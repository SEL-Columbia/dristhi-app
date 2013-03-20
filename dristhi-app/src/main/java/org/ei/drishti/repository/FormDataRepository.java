package org.ei.drishti.repository;

import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormDataRepository extends DrishtiRepository {
    private static final String DETAILS_COLUMN_NAME = "details";

    @Override
    protected void onCreate(SQLiteDatabase database) {
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
