package org.ei.drishti.repository;

import android.database.Cursor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class FormDataRepository extends DrishtiRepository {
    private static final String DETAILS_COLUMN_NAME = "details";

    @Override
    protected void onCreate(SQLiteDatabase database) {
    }

    public String rawQuery(String sql) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{});

        cursor.moveToFirst();
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
        cursor.close();
        return new Gson().toJson(columnValues);
    }
}
