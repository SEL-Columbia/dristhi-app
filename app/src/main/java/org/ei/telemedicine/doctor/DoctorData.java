package org.ei.telemedicine.doctor;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by naveen on 5/20/15.
 */
public class DoctorData {


    private String anmId;
    private String FormInformation;
    private String FormTime;
    private String POCInformation;
    private String PocStatus;
    private String PocTime;
    private String SyncStatus;
    private String VisitType;
    private String villageName;

    public String getVisitType() {
        return VisitType;
    }

    public void setVisitType(String visitType) {
        VisitType = visitType;
    }

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String CaseId;

    public String getAnmId() {
        return anmId;
    }

    public void setAnmId(String anmId) {
        this.anmId = anmId;
    }

    public String getFormInformation() {
        return FormInformation;
    }

    public void setFormInformation(String formInformation) {
        FormInformation = formInformation;
    }

    public String getFormTime() {
        return FormTime;
    }

    public void setFormTime(String formTime) {
        FormTime = formTime;
    }

    public String getPOCInformation() {
        return POCInformation;
    }

    public void setPOCInformation(String POCInformation) {
        this.POCInformation = POCInformation;
    }

    public String getPocStatus() {
        return PocStatus;
    }

    public void setPocStatus(String pocStatus) {
        PocStatus = pocStatus;
    }

    public String getPocTime() {
        return PocTime;
    }

    public void setPocTime(String pocTime) {
        PocTime = pocTime;
    }

    public String getSyncStatus() {
        return SyncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        SyncStatus = syncStatus;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public static Comparator<DoctorData> womanNameComparator = new Comparator<DoctorData>() {

        public int compare(DoctorData s1, DoctorData s2) {
            JSONObject compareformInfoJson = null;
            try {
                compareformInfoJson = new JSONObject(s1.getFormInformation());
                JSONObject currentformInfoJson = new JSONObject(s2.getFormInformation());
                String compareWomanName = compareformInfoJson.has(DoctorFormDataConstants.wife_name) ? compareformInfoJson.getString((DoctorFormDataConstants.wife_name)).toUpperCase() : "";
                String currentWomanName = currentformInfoJson.has(DoctorFormDataConstants.wife_name) ? currentformInfoJson.getString((DoctorFormDataConstants.wife_name)).toUpperCase() : "";
                return compareWomanName.compareTo(currentWomanName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return 0;
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };


}
