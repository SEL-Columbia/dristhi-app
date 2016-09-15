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
    private static final String BASE_ENTITY_ID_INDEX_SQL =  "CREATE INDEX ec_details_base_entity_id_index ON ec_details(base_entity_id COLLATE NOCASE);";
    private static final String KEY_INDEX_SQL =  "CREATE INDEX ec_details_key_index ON ec_details(key);";


    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL);
        database.execSQL(BASE_ENTITY_ID_INDEX_SQL);
        database.execSQL(KEY_INDEX_SQL);
    }

    public void add(String baseEntityId, String key, String value, Long timestamp) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        ContentValues values = new ContentValues();
        Long _id = getIdForDetailsIfExists(baseEntityId, key, value);
        if (_id != null){

            if(_id.longValue() == -1l){ // Value has not changed, no need to update
                return;
            }

            values.put("_id", _id);

        }
        values.put("base_entity_id", baseEntityId);
        values.put("key", key);
        values.put("value", value);
        values.put("event_date", timestamp);
        //TODO if the value is like this: 1066AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA, convert to human readable
        Long id = database.insertWithOnConflict(TABLE_NAME, BaseColumns._ID, values, android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE);
    }

    private Long getIdForDetailsIfExists(String baseEntityId, String key, String value) {
        Cursor mCursor = null;
        try {
            SQLiteDatabase db = masterRepository.getWritableDatabase();
            mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where base_entity_id = '" + baseEntityId + "' AND key ='" + key + "' LIMIT 1", null);
            if (mCursor != null && mCursor.moveToFirst()){
                if(value != null){
                    String currentValue = mCursor.getString(mCursor.getColumnIndex("value"));
                    if(value.equals(currentValue)) { // Value has not changed, no need to update
                        return -1l;
                    }
                }
                return mCursor.getLong(mCursor.getColumnIndex("_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (mCursor != null) mCursor.close();
        }
        return null;
    }

    public Map<String, String> getAllDetailsForClient(String baseEntityId) {
        Cursor cursor = null;
        Map<String, String> clientDetails = new HashMap<String, String>();
        try {
            SQLiteDatabase db = masterRepository.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where base_entity_id='"+baseEntityId+"'", null);
            if (cursor != null && cursor.moveToFirst()){
                do {
                    String key = cursor.getString(cursor.getColumnIndex("key"));
                    String value = cursor.getString(cursor.getColumnIndex("value"));
                    clientDetails.put(key, value);
                }while (cursor.moveToNext());
            }
            return clientDetails;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) cursor.close();
        }
        return clientDetails;
    }



}
