package org.ei.drishti.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Repository extends SQLiteOpenHelper {
    private static final String SETTINGS_SQL = "CREATE TABLE settings(key VARCHAR PRIMARY KEY, value VARCHAR)";
    private static final String ALERTS_SQL = "CREATE TABLE alerts(caseID VARCHAR, thaayiCardNumber VARCHAR, visitCode VARCHAR, benificiaryName VARCHAR, lateness INTEGER)";

    public Repository(Context context) {
        super(context, "drishti.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SETTINGS_SQL);
        database.execSQL(ALERTS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }

    public void updateSetting(String key, String value) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        database.replace("settings", null, values);
    }

    public String querySetting(String key, String defaultValue) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("settings", new String[]{"value"}, "key = ?", new String[]{key}, null, null, null, "1");
        cursor.moveToFirst();
        if (cursor.isAfterLast()) {
            cursor.close();
            return defaultValue;
        }

        String value = cursor.getString(0);
        cursor.close();
        return value;
    }
}
