package org.ei.opensrp.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import net.sqlcipher.database.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by koros on 4/19/16.
 */
public class DetailsRepository extends DrishtiRepository {

    private static final String SQL = "CREATE TABLE ec_details(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, base_entity_id VARCHAR, key VARCHAR, value VARCHAR, event_date datetime)";
    private static final String TABLE_NAME = "ec_details";

    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL);
    }

    public void add(String baseEntityId, String key, String value, Long timestamp) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        ContentValues values = new ContentValues();
        Long _id = getIdForDetailsIfExists(baseEntityId, key, value);
        if (_id != null){
            values.put("_id", _id);
        }
        values.put("base_entity_id", baseEntityId);
        values.put("key", key);
        values.put("value", value);
        values.put("event_date", timestamp);
        Long id = database.insertWithOnConflict(TABLE_NAME, BaseColumns._ID, values, android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE);
    }

    private Long getIdForDetailsIfExists(String baseEntityId, String key, String value) {
        Cursor mCursor = null;
        try {
            SQLiteDatabase db = masterRepository.getWritableDatabase();
            mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where base_entity_id = '" + baseEntityId + "' AND key ='" + key + "' AND value='" + value + "' LIMIT 1", null);
            if (mCursor != null && mCursor.moveToFirst()){
                return mCursor.getLong(mCursor.getColumnIndex("_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mCursor != null) mCursor.close();
        }
        return null;
    }

    public Map<String, String> getAllDetailsForClient(String base_entity_id) {
        Cursor mCursor = null;
        Map<String, String> clientDetails = new HashMap<String, String>();
        try {
            SQLiteDatabase db = masterRepository.getReadableDatabase();
            mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ", null);
            if (mCursor != null && mCursor.moveToFirst()){
                do {
                    String key = mCursor.getString(mCursor.getColumnIndex("key"));
                    String value = mCursor.getString(mCursor.getColumnIndex("value"));
                    clientDetails.put(key, value);
                }while (mCursor.moveToNext());
            }
            return clientDetails;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mCursor != null) mCursor.close();
        }
        return clientDetails;
    }


}
