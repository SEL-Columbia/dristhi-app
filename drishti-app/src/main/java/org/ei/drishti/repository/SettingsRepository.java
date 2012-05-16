package org.ei.drishti.repository;

import android.content.ContentValues;
import android.database.Cursor;
import info.guardianproject.database.sqlcipher.SQLiteDatabase;

public class SettingsRepository implements DrishtiRepository {
    static final String SETTINGS_SQL = "CREATE TABLE settings(key VARCHAR PRIMARY KEY, value VARCHAR)";
    public static final String SETTINGS_TABLE_NAME = "settings";
    public static final String SETTINGS_KEY_COLUMN = "key";
    public static final String SETTINGS_VALUE_COLUMN = "value";
    private Repository masterRepository;

    public String querySetting(String key, String defaultValue) {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
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
        SQLiteDatabase database = masterRepository.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SETTINGS_KEY_COLUMN, key);
        values.put(SETTINGS_VALUE_COLUMN, value);

        database.replace(SETTINGS_TABLE_NAME, null, values);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SETTINGS_SQL);
    }

    public void updateMasterRepository(Repository repository) {
        this.masterRepository = repository;
    }
}
