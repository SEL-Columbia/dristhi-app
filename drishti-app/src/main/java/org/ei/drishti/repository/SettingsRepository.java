package org.ei.drishti.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsRepository extends SQLiteOpenHelper {
    static final String SETTINGS_SQL = "CREATE TABLE settings(key VARCHAR PRIMARY KEY, value VARCHAR)";
    public static final String SETTINGS_TABLE_NAME = "settings";
    public static final String SETTINGS_KEY_COLUMN = "key";
    public static final String SETTINGS_VALUE_COLUMN = "value";

    public SettingsRepository(Context context) {
        super(context, "drishti.db", null, 1);
    }

    public String querySetting(String key, String defaultValue) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(SETTINGS_TABLE_NAME, new String[]{SETTINGS_VALUE_COLUMN}, SETTINGS_KEY_COLUMN + " = ?", new String[]{key}, null, null, null, "1");
        cursor.moveToFirst();
        if (cursor.isAfterLast()) {
            cursor.close();
            return defaultValue;
        }

        String value = cursor.getString(0);
        cursor.close();
        return value;
    }

    public void updateSetting(String key, String value) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SETTINGS_KEY_COLUMN, key);
        values.put(SETTINGS_VALUE_COLUMN, value);

        database.replace(SETTINGS_TABLE_NAME, null, values);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SettingsRepository.SETTINGS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
    }
}
