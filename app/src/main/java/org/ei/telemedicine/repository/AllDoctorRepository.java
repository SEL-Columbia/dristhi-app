package org.ei.telemedicine.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ei.telemedicine.doctor.DoctorData;
//import org.ei.telemedicine.doctor.DoctorDomain;
import org.ei.telemedicine.domain.Mother;
import org.json.JSONException;

public class AllDoctorRepository {
    private DoctorRepository doctorRepository;
    private String TAG = "AllDoctorRepository";

    public AllDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Mother> all() {
        return doctorRepository.allmotherData();
    }

    public int getChildCount() {
        return 0;
    }

    public void addData(DoctorData doctorData) {
        Log.e(TAG, doctorData.getFormInformation());
        doctorRepository.add(doctorData);
    }

    public int getCount(String type) {
        int countByType = doctorRepository.getCountByType(type);
        Log.e(TAG, type + " Count= " + countByType);
        return countByType;
    }
    public ArrayList<String> getVillages(){
        return doctorRepository.allVillages();
    }

    public List<DoctorData> getAllConsultants() {
        try {
            return doctorRepository.allConsultantsData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearDataNoPoc() {
        doctorRepository.deleteAll();
    }

    public String getPocInfoCaseId(String caseId) {
        String data = doctorRepository.getPocInfo(caseId);
        return data;
    }

    public void updatePocInLocal(String caseId, String pocInfo, String pocPendingInfo) {
        doctorRepository.updateDetails(caseId, pocInfo, pocPendingInfo);
    }

    public void deleteUseCaseId(String caseId) {
        doctorRepository.deleteUseCaseId(caseId);
    }
}
