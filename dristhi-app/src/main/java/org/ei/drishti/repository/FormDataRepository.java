package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormDataRepository extends DrishtiRepository {
    private static final String FORM_SUBMISSION_SQL = "CREATE TABLE form_submission(id VARCHAR PRIMARY KEY, formName VARCHAR, data VARCHAR)";
    public static final String ID_COLUMN = "id";
    private static final String FORM_NAME_COLUMN = "formName";
    private static final String DATA_COLUMN = "data";
    private static final String FORM_SUBMISSION_TABLE_NAME = "form_submission";
    public static final String[] FORM_SUBMISSION_TABLE_COLUMNS = new String[]{ID_COLUMN, FORM_NAME_COLUMN, DATA_COLUMN};
    private static final String DETAILS_COLUMN_NAME = "details";
    private static final String ID_PARAM = "id";
    private static final String FORM_NAME_PARAM = "formName";

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(FORM_SUBMISSION_SQL);
    }

    public String queryUniqueResult(String sql) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{});

        cursor.moveToFirst();
        Map<String, String> result = readARow(cursor);
        cursor.close();

        return new Gson().toJson(result);
    }

    public String queryList(String sql) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{});
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            results.add(readARow(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return new Gson().toJson(results);
    }

    public void saveFormSubmission(String paramsJSON, String data) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Map<String, String> params = new Gson().fromJson(paramsJSON, new TypeToken<Map<String, String>>() {
        }.getType());
        database.insert(FORM_SUBMISSION_TABLE_NAME, null, createValuesForFormSubmission(params, data));
    }

    private ContentValues createValuesForFormSubmission(Map<String, String> params, String data) {
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, params.get(ID_PARAM));
        values.put(FORM_NAME_COLUMN, params.get(FORM_NAME_PARAM));
        values.put(DATA_COLUMN, data);
        return values;
    }

    public String fetchFromSubmission(String id) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(FORM_SUBMISSION_TABLE_NAME, FORM_SUBMISSION_TABLE_COLUMNS, ID_COLUMN + " = ?", new String[]{id}, null, null, null);
        return readFormSubmission(cursor);
    }

    private String readFormSubmission(Cursor cursor) {
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(DATA_COLUMN));
    }

    private Map<String, String> readARow(Cursor cursor) {
        Map<String, String> columnValues = new HashMap<String, String>();
        String[] columns = cursor.getColumnNames();
        for (int index = 0; index < columns.length; index++) {
            if (DETAILS_COLUMN_NAME.equalsIgnoreCase(columns[index])) {
                Map<String, String> details = new Gson().fromJson(cursor.getString(index), new TypeToken<Map<String, String>>() {
                }.getType());
                columnValues.putAll(details);
            } else {
                columnValues.put(columns[index], cursor.getString(index));
            }
        }
        return columnValues;
    }
}
