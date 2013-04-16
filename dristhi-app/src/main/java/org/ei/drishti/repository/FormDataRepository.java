package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;
import org.ei.drishti.AllConstants;
import org.ei.drishti.util.Log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;

public class FormDataRepository extends DrishtiRepository {
    private static final String FORM_SUBMISSION_SQL = "CREATE TABLE form_submission(instanceId VARCHAR PRIMARY KEY, formName VARCHAR, data VARCHAR)";
    public static final String INSTANCE_ID_COLUMN = "instanceId";
    public static final String ID_COLUMN = "id";
    private static final String FORM_NAME_COLUMN = "formName";
    private static final String DATA_COLUMN = "data";
    private static final String FORM_SUBMISSION_TABLE_NAME = "form_submission";
    public static final String[] FORM_SUBMISSION_TABLE_COLUMNS = new String[]{INSTANCE_ID_COLUMN, FORM_NAME_COLUMN, DATA_COLUMN};
    private static final String DETAILS_COLUMN_NAME = "details";
    private static final String ID_PARAM = "id";
    private static final String FORM_NAME_PARAM = "formName";
    private Map<String, String[]> TABLE_COLUMN_MAP;

    public FormDataRepository() {
        TABLE_COLUMN_MAP = new HashMap<String, String[]>();
        TABLE_COLUMN_MAP.put(EligibleCoupleRepository.EC_TABLE_NAME, EligibleCoupleRepository.EC_TABLE_COLUMNS);
        TABLE_COLUMN_MAP.put(MotherRepository.MOTHER_TABLE_NAME, MotherRepository.MOTHER_TABLE_COLUMNS);
        TABLE_COLUMN_MAP.put(ChildRepository.CHILD_TABLE_NAME, ChildRepository.CHILD_TABLE_COLUMNS);
    }

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
        values.put(INSTANCE_ID_COLUMN, params.get(AllConstants.ID_PARAM));
        values.put(FORM_NAME_COLUMN, params.get(FORM_NAME_PARAM));
        values.put(DATA_COLUMN, data);
        return values;
    }

    public String fetchFromSubmission(String id) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(FORM_SUBMISSION_TABLE_NAME, FORM_SUBMISSION_TABLE_COLUMNS, INSTANCE_ID_COLUMN + " = ?", new String[]{id}, null, null, null);
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

    public String saveEntity(String entityType, String fields) {
        Log.logError(MessageFormat.format("entityType: {0}, entityFields: {1}", entityType, fields));
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Map<String, String> updatedFieldsMap = new Gson().fromJson(fields, new TypeToken<Map<String, String>>() {
        }.getType());

        String entityId = updatedFieldsMap.get(ID_PARAM);
        Map<String, String> entityMap = loadEntityMap(entityType, database, entityId);

        ContentValues contentValues = getContentValues(updatedFieldsMap, entityType, entityMap);
        database.replace(entityType, null, contentValues);
        return entityId;
    }

    private ContentValues getContentValues(Map<String, String> updatedFieldsMap, String entityType, Map<String, String> entityMap) {
        List<String> columns = asList(TABLE_COLUMN_MAP.get(entityType));
        ContentValues contentValues = initializeContentValuesBasedExistingValues(entityMap);
        Map<String, String> details = initializeDetailsBasedOnExistingValues(entityMap);

        for (String fieldName : updatedFieldsMap.keySet()) {
            if (columns.contains(fieldName)) {
                contentValues.put(fieldName, updatedFieldsMap.get(fieldName));
            } else {
                details.put(fieldName, updatedFieldsMap.get(fieldName));
            }
        }
        contentValues.put(DETAILS_COLUMN_NAME, new Gson().toJson(details));

        return contentValues;
    }

    private Map<String, String> initializeDetailsBasedOnExistingValues(Map<String, String> entityMap) {
        Map<String, String> details;
        String detailsJSON = entityMap.get(DETAILS_COLUMN_NAME);
        if (detailsJSON == null) {
            details = new HashMap<String, String>();
        } else {
            details = new Gson().fromJson(detailsJSON, new TypeToken<Map<String, String>>() {
            }.getType());
        }
        return details;
    }

    private ContentValues initializeContentValuesBasedExistingValues(Map<String, String> entityMap) {
        ContentValues contentValues = new ContentValues();
        for (String column : entityMap.keySet()) {
            contentValues.put(column, entityMap.get(column));
        }
        return contentValues;
    }

    private Map<String, String> loadEntityMap(String entityType, SQLiteDatabase database, String entityId) {
        Map<String, String> entityMap = new HashMap<String, String>();
        Cursor cursor = database.query(entityType,
                TABLE_COLUMN_MAP.get(entityType), ID_COLUMN + " =?", new String[]{entityId}, null, null, null);
        if (!cursor.isAfterLast()) {
            cursor.moveToFirst();
            for (String column : cursor.getColumnNames()) {
                entityMap.put(column, cursor.getString(cursor.getColumnIndex(column)));
            }
        }
        cursor.close();
        return entityMap;
    }

    public String generateIdFor(String entityType) {
        return randomUUID().toString();
    }
}
