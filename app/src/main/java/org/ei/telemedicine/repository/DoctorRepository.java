package org.ei.telemedicine.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sqlcipher.database.SQLiteDatabase;

import org.ei.telemedicine.doctor.DoctorData;
import org.ei.telemedicine.domain.EligibleCouple;
import org.ei.telemedicine.domain.Mother;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * Created by naveen on 5/21/15.
 * New Consultants Table
 */
public class DoctorRepository extends DrishtiRepository {
    static final String DOCTORS_INFO_SQL = "CREATE TABLE doctors(SNo INTEGER PRIMARY KEY AUTOINCREMENT,AnmId VARCHAR,CaseId VARCHAR, FormInformation VARCHAR,FormTime VARCHAR,POCInformation VARCHAR,POCTime VARCHAR,PocStatus VARCHAR,SyncStatus VARCHAR,VisitType VARCHAR,Village VARCHAR)";

    public static final String DOCTORS_INFO_TABLE_NAME = "doctors";

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
    public static final String VILLAGE_NAME_COL = "Village";

    public static final String[] DOCTOR_TABLE_COLUMNS = new String[]{SNO_COLUMN, ANMID_COLUMN, CASE_ID_COLUMN, FORMINFORMATION_COLUMN, FORMTIME_COLUMN, POCINFORMATION_COL, POCSTATUS_COL, POCTIME_COL, SYNCSTATUS_COL, VISIT_TYPE_COL, VILLAGE_NAME_COL};
    private String TAG = "DoctorRepository";


    @Override
    protected void onCreate(SQLiteDatabase database) {
        database.execSQL(DOCTORS_INFO_SQL);
    }

    public List<DoctorData> allConsultantsData() throws JSONException {
        SQLiteDatabase database = masterRepository.getReadableDatabase();
        Cursor cursor = database.query(DOCTORS_INFO_TABLE_NAME, DOCTOR_TABLE_COLUMNS, null, null, null, null, null, null);
        Log.e(TAG, "Cursor Size" + cursor.getCount());
        return readAllConstultansData(cursor);
    }

    private List<DoctorData> readAllConstultansData(Cursor cursor) throws JSONException {
        cursor.moveToFirst();
        List<DoctorData> doctorDatas = new ArrayList<DoctorData>();
        while (!cursor.isAfterLast()) {
            DoctorData doctorData = new DoctorData();

            doctorData.setCaseId(cursor.getString(cursor.getColumnIndex(CASE_ID_COLUMN)));
            doctorData.setAnmId(cursor.getString(cursor.getColumnIndex(ANMID_COLUMN)));
            String form = cursor.getString(cursor.getColumnIndex(FORMINFORMATION_COLUMN));
            doctorData.setFormInformation(form);
            doctorData.setFormTime(cursor.getString(cursor.getColumnIndex(FORMTIME_COLUMN)));
//            doctorData.setPOCInformation(cursor.getBlob(cursor.getColumnIndex(POCINFORMATION_COL)) + "");
//            doctorData.setPocStatus(cursor.getString(cursor.getColumnIndex(POCSTATUS_COL)));
//            doctorData.setPocTime(cursor.getString(cursor.getColumnIndex(POCTIME_COL)));
//            doctorData.setSyncStatus(cursor.getString(cursor.getColumnIndex(SYNCSTATUS_COL)));
            String string = cursor.getString(cursor.getColumnIndex(VISIT_TYPE_COL));
            doctorData.setVisitType(string);

            doctorDatas.add(doctorData);
            cursor.moveToNext();
        }
        cursor.close();
        return doctorDatas;
    }

    public void add(DoctorData doctorData) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Log.e(TAG, "Adding records" + doctorData.getFormInformation());
        if (!isExistCaseId(doctorData.getCaseId()))
            database.insert(DOCTORS_INFO_TABLE_NAME, null, createValuesFor(doctorData));
        else
            Log.e(TAG, "Already exist");
    }


    private ContentValues createValuesFor(DoctorData doctorData) {
        ContentValues values = new ContentValues();
        values.put(CASE_ID_COLUMN, doctorData.getCaseId());
        values.put(ANMID_COLUMN, doctorData.getAnmId());
        values.put(FORMINFORMATION_COLUMN, doctorData.getFormInformation().toString());
        values.put(POCINFORMATION_COL, doctorData.getPOCInformation());
        values.put(FORMTIME_COLUMN, doctorData.getFormTime());
        values.put(POCSTATUS_COL, doctorData.getPocStatus());
        values.put(POCTIME_COL, doctorData.getPocTime());
        values.put(SYNCSTATUS_COL, doctorData.getSyncStatus());
        values.put(VISIT_TYPE_COL, doctorData.getVisitType());
        values.put(VILLAGE_NAME_COL, doctorData.getVillageName());
        return values;
    }

    public void updateDetails(String caseId, String pocInformation, String pocPendingInfo) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String formInfo = getFormInfo(caseId);
        if (formInfo != null) {
            try {
                JSONObject formInfoJson = new JSONObject(formInfo);
                formInfoJson.put("pocPending", pocPendingInfo);
                ContentValues valuesToUpdate = new ContentValues();
                valuesToUpdate.put(POCINFORMATION_COL, pocInformation);
                valuesToUpdate.put(FORMINFORMATION_COLUMN, formInfoJson.toString());
                database.update(DOCTORS_INFO_TABLE_NAME, valuesToUpdate, CASE_ID_COLUMN + " = ?", new String[]{caseId});
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param caseId
     * @return formInformation for this caseId
     */
    private String getFormInfo(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + DOCTORS_INFO_TABLE_NAME + " where " + CASE_ID_COLUMN + " ='" + caseId + "'", null);
        String formInfo = null;
//        Cursor cursor = database.query(DOCTORS_INFO_TABLE_NAME, DOCTOR_TABLE_COLUMNS, CASE_ID_COLUMN, new String[]{caseId}, null, null, null, null);
        while (cursor.moveToNext()) {
            formInfo = cursor.getString(cursor.getColumnIndex(FORMINFORMATION_COLUMN));
        }
        cursor.close();
        return formInfo;
    }

    public String getPocInfo(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + DOCTORS_INFO_TABLE_NAME + " where " + CASE_ID_COLUMN + " ='" + caseId + "'", null);
        Log.e(TAG, "Poc Cursor Size" + cursor.getCount() + "");
        String pocInfo = null;
        while (cursor.moveToNext()) {
            Log.e(TAG, "PocCursor Data" + cursor.getString(cursor.getColumnIndex(POCINFORMATION_COL)) + "");
            pocInfo = cursor.getString(cursor.getColumnIndex(POCINFORMATION_COL));
        }
        cursor.close();
        return pocInfo;
    }

    public void deleteAll() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        database.execSQL("DELETE FROM  " + DOCTORS_INFO_TABLE_NAME + " where POCInformation is null");
    }

    public void deleteUseCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String deleteQuery = "DELETE FROM  " + DOCTORS_INFO_TABLE_NAME + " where CaseId ='" + caseId + "'";
        database.execSQL(deleteQuery);
    }

    public ArrayList<String> allVillages() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();

//        String villagesQuery = "select DISTINCT(Village) from " + DOCTORS_INFO_TABLE_NAME;
//        Cursor cursor = database.rawQuery(villagesQuery, null);
        Cursor cursor = database.query(true, DOCTORS_INFO_TABLE_NAME, new String[]{VILLAGE_NAME_COL}, null, null, null, null, null, null);
        ArrayList<String> villagesList = new ArrayList<String>();
        while (cursor.moveToNext()) {
            Log.e("Villages", cursor.getString(cursor.getColumnIndex(VILLAGE_NAME_COL)));
            villagesList.add(cursor.getString(cursor.getColumnIndex(VILLAGE_NAME_COL)));
        }
        cursor.close();
        return villagesList;
    }


    public int getCountByType(String type) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + DOCTORS_INFO_TABLE_NAME + " where " + VISIT_TYPE_COL + "='" + type + "'", null);
        //        Cursor cursor = database.rawQuery("select * from mother where isClosed=?", new String[]{"false"});
        int count = cursor.getCount();
        Log.e(TAG, count + "");
        cursor.close();
        return count;
    }

    public boolean isExistCaseId(String caseId) {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        String selectqry = "select * from " + DOCTORS_INFO_TABLE_NAME + " where " + CASE_ID_COLUMN + "= '" + caseId + "'";
        Cursor cursor = database.rawQuery(selectqry, null);
        Log.e(TAG, cursor.getCount() + "");
        boolean isCaseIdExist = cursor.getCount() != 0;
        cursor.close();
        return isCaseIdExist;
    }

    public List<Mother> allmotherData() {
        SQLiteDatabase database = masterRepository.getWritableDatabase();
        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery("select * from mother where isClosed=?", new String[]{"false"});
        }
        return readAllMothers(cursor);
    }

    private List<Mother> readAllMothers(Cursor cursor) {
        cursor.moveToFirst();
        List<Mother> mothers = new ArrayList<Mother>();
        while (!cursor.isAfterLast()) {
            Map<String, String> details = new Gson().fromJson(cursor.getString(5), new TypeToken<Map<String, String>>() {
            }.getType());

            mothers.add(new Mother(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4))
                    .withDetails(details)
                    .setIsClosed(Boolean.valueOf(cursor.getString(6)))
                    .withType(cursor.getString(cursor.getColumnIndex("type"))));
            cursor.moveToNext();
        }
        cursor.close();
        return mothers;
    }

    public int getChildCount() {
        return 0;
    }
}
