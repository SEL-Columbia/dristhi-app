package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sqlcipher.database.SQLiteDatabase;

import org.ei.drishti.domain.SyncStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.ei.drishti.util.EasyMap.create;

/**
 * Created by Dimas Ciputra on 3/21/15.
 */
public class FormsVersionRepository extends DrishtiRepository {

    private static final String FORM_VERSION_SQL = "CREATE TABLE all_forms_version(id INTEGER PRIMARY KEY," +
            "formName VARCHAR, version VARCHAR, syncStatus VARCHAR)";
    private static final String FORM_VERSION_TABLE_NAME = "all_forms_version";

    private static final String ID_COLUMN = "id";
    public static final String FORM_NAME_COLUMN = "formName";
    public static final String VERSION_COLUMN = "version";
    public static final String SYNC_STATUS_COLUMN = "syncStatus";

    public static final String[] FORM_VERSION_TABLE_COLUMNS = new String[]{ID_COLUMN, FORM_NAME_COLUMN,
            VERSION_COLUMN, SYNC_STATUS_COLUMN};

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(FORM_VERSION_SQL);
    }

    public Map<String, String> fetchVersionByFormName(String formName) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(FORM_VERSION_TABLE_NAME, FORM_VERSION_TABLE_COLUMNS, FORM_NAME_COLUMN + " = ?",
                new String[]{formName}, null, null, null);
        return readFormVersion(cursor).get(0);
    }

    public List<Map<String, String>> getAllFormWithSyncStatus(SyncStatus status) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(FORM_VERSION_TABLE_NAME, FORM_VERSION_TABLE_COLUMNS, SYNC_STATUS_COLUMN + " = ?",
                new String[]{status.value()}, null, null, null);
        return readFormVersion(cursor);
    }

    public void addFormVersion(Map<String, String> dataJSON) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(FORM_VERSION_TABLE_NAME, null, createValuesFormVersions(dataJSON));
    }

    public void updateServerVersion(String formName, String serverVersion) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VERSION_COLUMN, serverVersion);
        database.update(FORM_VERSION_TABLE_NAME, values, FORM_NAME_COLUMN + " = ?", new String[]{formName});
    }

    public void updateSyncStatus(String formName, SyncStatus status){
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SYNC_STATUS_COLUMN, status.value());
        database.update(FORM_VERSION_TABLE_NAME, values, FORM_NAME_COLUMN + " = ?", new String[]{formName});
    }

    public boolean formExists(String formName) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(FORM_VERSION_TABLE_NAME, new String[]{FORM_NAME_COLUMN}, FORM_NAME_COLUMN
                + " = ?", new String[]{formName}, null, null, null);
        boolean isThere = cursor.moveToFirst();
        cursor.close();
        return isThere;
    }

    public ContentValues createValuesFormVersions(Map<String, String> params) {
        ContentValues values = new ContentValues();
        values.put(FORM_NAME_COLUMN, params.get(FORM_NAME_COLUMN));
        values.put(VERSION_COLUMN, params.get(VERSION_COLUMN));
        values.put(SYNC_STATUS_COLUMN, params.get(SYNC_STATUS_COLUMN));
        return values;
    }

    private List<Map<String, String>> readFormVersion(Cursor cursor) {
        cursor.moveToFirst();
        List<Map<String, String>> submissions = new ArrayList<Map<String, String>>();
        while (!cursor.isAfterLast()) {
            submissions.add(
                    create(ID_COLUMN, cursor.getString(cursor.getColumnIndex(ID_COLUMN)))
                            .put(FORM_NAME_COLUMN, cursor.getString(cursor.getColumnIndex(FORM_NAME_COLUMN)))
                            .put(VERSION_COLUMN, cursor.getString(cursor.getColumnIndex(VERSION_COLUMN)))
                            .put(SYNC_STATUS_COLUMN, cursor.getString(cursor.getColumnIndex(SYNC_STATUS_COLUMN)))
                            .map());
            cursor.moveToNext();
        }
        cursor.close();
        return submissions;
    }

}