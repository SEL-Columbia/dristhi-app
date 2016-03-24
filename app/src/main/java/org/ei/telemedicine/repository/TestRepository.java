package org.ei.telemedicine.repository;

import static net.sqlcipher.DatabaseUtils.longForQuery;
import static org.apache.commons.lang3.StringUtils.repeat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sqlcipher.database.SQLiteDatabase;

import org.ei.telemedicine.doctor.DoctorData;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.TestDomain;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestRepository extends DrishtiRepository {
    static final String DOCTORS_INFO_SQL = "CREATE TABLE consultants(SNo INTEGER PRIMARY KEY AUTOINCREMENT,AnmId VARCHAR, FormInformation BLOB,FormTime VARCHAR,POCInformation BLOB,POCTime VARCHAR,PocStatus VARCHAR,SyncStatus VARCHAR)";

    public static final String DOCTORS_INFO_TABLE_NAME = "doctor";

    public static final String SNO_COLUMN = "SNo";
    public static final String ANMID_COLUMN = "AnmId";
    public static final String CASE_ID_COLUMN = "CaseId";
    public static final String FORMINFORMATION_COLUMN = "FormInformation";
    public static final String FORMTIME_COLUMN = "FormTime";
    public static final String POCINFORMATION_COL = "POCInformation";
    public static final String POCSTATUS_COL = "PocStatus";
    public static final String POCTIME_COL = "PocTime";
    public static final String SYNCSTATUS_COL = "SyncStatus";
    public static final String VISIT_TYPE_COL = "VisitType";

    public static final String[] DOCTOR_TABLE_COLUMNS = new String[]{SNO_COLUMN, ANMID_COLUMN, CASE_ID_COLUMN, FORMINFORMATION_COLUMN, FORMTIME_COLUMN, POCINFORMATION_COL, POCSTATUS_COL, POCTIME_COL, SYNCSTATUS_COL, VISIT_TYPE_COL};


    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(DOCTORS_INFO_SQL);
    }
//
//    public List<DoctorData> allConsultantsData() {
//        SQLiteDatabase database = masterRepository.getReadableDatabase();
//        Cursor cursor = database.query(DOCTORS_INFO_TABLE_NAME, DOCTOR_TABLE_COLUMNS, POCINFORMATION_COL + " = null ", null, null, null, null, null);
//        return readAllConstultansData(cursor);
//    }

//    private List<DoctorData> readAllConstultansData(Cursor cursor) {
//        cursor.moveToFirst();
//        List<DoctorData> doctorDatas = new ArrayList<DoctorData>();
//        while (!cursor.isAfterLast()) {
//            DoctorData doctorData = new DoctorData();
//
//            doctorData.setCaseId(cursor.getString(cursor.getColumnIndex(CASE_ID_COLUMN)));
//            doctorData.setAnmId(cursor.getString(cursor.getColumnIndex(ANMID_COLUMN)));
//            doctorData.setFormInformation(cursor.getString(cursor.getColumnIndex(FORMINFORMATION_COLUMN)));
//            doctorData.setFormTime(cursor.getString(cursor.getColumnIndex(FORMTIME_COLUMN)));
//            doctorData.setPOCInformation(cursor.getString(cursor.getColumnIndex(POCINFORMATION_COL)));
//            doctorData.setPocStatus(cursor.getString(cursor.getColumnIndex(POCSTATUS_COL)));
//            doctorData.setPocTime(cursor.getString(cursor.getColumnIndex(POCTIME_COL)));
//            doctorData.setSyncStatus(cursor.getString(cursor.getColumnIndex(SYNCSTATUS_COL)));
//            doctorData.setVisitType(cursor.getString(cursor.getColumnIndex(VISIT_TYPE_COL)));
//
//            doctorDatas.add(doctorData);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return doctorDatas;
//    }
//
//    public void add(DoctorData doctorData) {
//        SQLiteDatabase database = masterRepository.getWritableDatabase();
//        database.insert(DOCTORS_INFO_TABLE_NAME, null, createValuesFor(doctorData));
//    }
//
//
//    private ContentValues createValuesFor(DoctorData doctorData) {
//        ContentValues values = new ContentValues();
//        values.put(CASE_ID_COLUMN, doctorData.getCaseId());
//        values.put(ANMID_COLUMN, doctorData.getAnmId());
//        values.put(FORMINFORMATION_COLUMN, doctorData.getFormInformation());
//        values.put(POCINFORMATION_COL, doctorData.getPOCInformation());
//        values.put(FORMTIME_COLUMN, doctorData.getFormTime());
//        values.put(POCSTATUS_COL, doctorData.getPocStatus());
//        values.put(POCTIME_COL, doctorData.getPocTime());
//        values.put(SYNCSTATUS_COL, doctorData.getSyncStatus());
//        values.put(VISIT_TYPE_COL, doctorData.getVisitType());
//        return values;
//    }
//
//    public void updateDetails(String caseId, String pocInformation) {
//        SQLiteDatabase database = masterRepository.getWritableDatabase();
//        ContentValues valuesToUpdate = new ContentValues();
//        valuesToUpdate.put(POCINFORMATION_COL, pocInformation);
//        database.update(DOCTORS_INFO_TABLE_NAME, valuesToUpdate, CASE_ID_COLUMN + " = ?", new String[]{caseId});
//    }
//
//    public void deleteAll() {
////        SQLiteDatabase database = masterRepository.getWritableDatabase();
////        database.execSQL("TRUNCATE table" + DOCTORS_INFO_TABLE_NAME);
//    }
//
//
//    public int getCountByType(String type) {
//        SQLiteDatabase database = masterRepository.getWritableDatabase();
////        Cursor cursor = database.rawQuery("select count(*) from mother where type=?", new String[]{type});
//        Cursor cursor = database.rawQuery("select * from mother where isClosed=?", new String[]{"false"});
//        return cursor.getCount();
//    }
//
//    public Cursor allmotherData() {
//        SQLiteDatabase database = masterRepository.getWritableDatabase();
//        Cursor cursor = null;
//        if (database != null) {
//            cursor = database.rawQuery("select * from mother where isClosed=?", new String[]{"false"});
//        } else {
//
//        }
//        return cursor;
//    }

}
