package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;

import java.util.*;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;

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

    public String saveEntity(String entityType, String fields) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Map<String, String> fieldsMap = new Gson().fromJson(fields, new TypeToken<Map<String, String>>() {
        }.getType());
        Map<String, String> details;
        String entityId = fieldsMap.get(ID_PARAM);
        if (isBlank(entityId)) {
            entityId = UUID.randomUUID().toString();
            fieldsMap.put(ID_PARAM, entityId);
            details = new HashMap<String, String>();
        } else {
            details = getDetailsForEntity(entityType, database, entityId);
        }
        ContentValues contentValues = getContentValues(fieldsMap, details, entityType);
        database.replace(entityType, null, contentValues);
        return entityId;
    }

    private ContentValues getContentValues(Map<String, String> fieldsMap, Map<String, String> details, String entityType) {
        List<String> columns = asList(TABLE_COLUMN_MAP.get(entityType));
        ContentValues contentValues = new ContentValues();
        for (String fieldName : fieldsMap.keySet()) {
            if (columns.contains(fieldName)) {
                contentValues.put(fieldName, fieldsMap.get(fieldName));
            } else {
                details.put(fieldName, fieldsMap.get(fieldName));
            }
        }
        contentValues.put(DETAILS_COLUMN_NAME, new Gson().toJson(details));
        return contentValues;
    }

    private Map<String, String> getDetailsForEntity(String entityType, SQLiteDatabase database, String entityId) {
        Map<String, String> details;
        Cursor cursor = database.query(entityType,
                new String[]{DETAILS_COLUMN_NAME}, ID_COLUMN + " =?", new String[]{entityId}, null, null, null);
        if (cursor.isAfterLast()) {
            details = new HashMap<String, String>();
        } else {
            cursor.moveToFirst();
            details = new Gson().fromJson(cursor.getString(0), new TypeToken<Map<String, String>>() {
            }.getType());
        }
        cursor.close();
        return details;
    }
}
