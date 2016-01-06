package org.ei.opensrp.vaccinator.repository;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.ei.opensrp.repository.DrishtiRepository;
import org.ei.opensrp.vaccinator.domain.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.sqlcipher.DatabaseUtils.longForQuery;

/**
 * Created by muhammad.ahmed@ihsinformatics.com on 16-Nov-15.
 */
public class FieldRepository extends DrishtiRepository {

    private static final String FIELD_SQL = "CREATE TABLE field(id VARCHAR PRIMARY KEY, reporttype VARCHAR, centername VARCHAR, wasteddatails VARCHAR, receiveddetails VARCHAR, balancedetails VARCHAR,date VARCHAR)";
    public static final String FIELD_TABLE_NAME = "field";
    private static final String ID_COLUMN = "id";
    private static final String TYPE_COLUMN = "reporttype";
    private static final String CENTER_NAME_COLUMN = "centername";

    private static final String WASTED_DETAILS_COLUMN = "wasteddetails";
    private static final String RECEIVED_DETAILS_COLUMN = "receiveddetails";
    private static final String BALANCED_DETAILS_COLUMN = "balanceddetails";
    private static final String DATE_COLUMN = "date";

    public static final String[] FIELD_TABLE_COLUMNS={ID_COLUMN,TYPE_COLUMN,CENTER_NAME_COLUMN
            ,WASTED_DETAILS_COLUMN,RECEIVED_DETAILS_COLUMN,
            BALANCED_DETAILS_COLUMN,DATE_COLUMN
    };

    @Override
    protected void onCreate(SQLiteDatabase database) {
      database.execSQL(FIELD_SQL);
    }


    public void add(Field field) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.insert(FIELD_TABLE_NAME, null, createValuesFor(field));
    }


    private ContentValues createValuesFor(Field field) {
        ContentValues values = new ContentValues();
       // values.put(ID_COLUMN, field.getCaseId());
        values.put(TYPE_COLUMN,field.getReportType() );
        values.put(CENTER_NAME_COLUMN, field.getCenterName());
        values.put(WASTED_DETAILS_COLUMN, new Gson().toJson(field.getWastedDetails()));
        values.put(RECEIVED_DETAILS_COLUMN, new Gson().toJson(field.getReceivedDetails()));
        values.put(BALANCED_DETAILS_COLUMN, new Gson().toJson(field.getBalancedDetails()));
        values.put(DATE_COLUMN, field.getDate());
        return values;
    }

    public void updateField(Field field) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.update(FIELD_TABLE_NAME, createValuesFor(field), ID_COLUMN + " = ?", new String[]{field.getCaseId()});
    }

    public List<Field> allMonthlyData(){
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        //database.
                Cursor cursor = database.query(FIELD_TABLE_NAME, FIELD_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{"monthly"}, null, null, null, null);
        return readAll(cursor);
    }


    public List<Field> allDailyData(){
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        //database.
        Cursor cursor = database.query(FIELD_TABLE_NAME, FIELD_TABLE_COLUMNS, TYPE_COLUMN + " = ?", new String[]{"daily"}, null, null, null, null);
        return readAll(cursor);
    }

    public long count() {
        return longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(*) FROM " + FIELD_TABLE_NAME, new String[0]);
    }

    public long countDaysData() {
        return longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + FIELD_TABLE_NAME +"WHERE "+TYPE_COLUMN+"'=daily",new String[0] );
    }


    public long countMonthsData() {
        return longForQuery(masterRepository.getReadableDatabase(), "SELECT COUNT(1) FROM " + FIELD_TABLE_NAME +"WHERE "+TYPE_COLUMN+"'=monthly" ,new String[0] );
    }


    private List<Field> readAll(Cursor cursor) {
        cursor.moveToFirst();
        List<Field> children = new ArrayList<Field>();
        while (!cursor.isAfterLast()) {
            children.add(new Field(
                            cursor.getString(cursor.getColumnIndex(ID_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(TYPE_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(CENTER_NAME_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(DATE_COLUMN)),
                            new Gson().<Map<String, String>>fromJson(cursor.getString(cursor.getColumnIndex(WASTED_DETAILS_COLUMN)),
                                    new TypeToken<Map<String, String>>() {
                                    }.getType()),
                            new Gson().<Map<String, String>>fromJson(cursor.getString(cursor.getColumnIndex(RECEIVED_DETAILS_COLUMN)),
                                    new TypeToken<Map<String, String>>() {
                                            }.getType()),
                            new Gson().<Map<String, String>>fromJson(cursor.getString(cursor.getColumnIndex(BALANCED_DETAILS_COLUMN)),
                                    new TypeToken<Map<String, String>>() {}.getType())
            ));


            cursor.moveToNext();
        }
        cursor.close();
        return children;
    }

}
