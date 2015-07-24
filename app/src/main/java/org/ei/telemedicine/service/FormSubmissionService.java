package org.ei.telemedicine.service;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.doctor.DoctorData;
import org.ei.telemedicine.doctor.DoctorFormDataConstants;
import org.ei.telemedicine.doctor.PendingConsultantBaseAdapter;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.repository.AllDoctorRepository;
import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.repository.FormDataRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.ei.telemedicine.AllConstants.*;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.age;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anc_entityId;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anc_number;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anc_visit_date;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anc_visit_number;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.ancvisit;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anmId;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.blood_glucose;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.bp_dia;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.bp_sys;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_age;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_dob;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_entityId;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_immediateReferral;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_immediateReferral_reason;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_info;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_name;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_no_of_osrs;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_referral;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_report_child_disease;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_report_child_disease_date;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_report_child_disease_other;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_report_child_disease_place;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_signs;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_signs_other;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.child_submission_date;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.documentId;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.edd;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.entityId;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.fetal_data;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.husband_name;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.id_no;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.kopfeel_heat_or_chills;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.lmp;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pncVisit;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_abdominal_problems;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_breast_problems;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_difficulties;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_entityId;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_number;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_urinating_problems;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_vaginal_problems;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_visit_date;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.pnc_visit_place;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.poc_pending;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.risk_symptoms;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.temp_data;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.visit_type;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.weight_data;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.wife_name;
import static org.ei.telemedicine.domain.SyncStatus.SYNCED;
import static org.ei.telemedicine.util.EasyMap.create;
import static org.ei.telemedicine.util.Log.logError;

public class FormSubmissionService {
    private ZiggyService ziggyService;
    private FormDataRepository formDataRepository;
    private AllSettings allSettings;
    private String TAG = "FormSubmissionService";
    List<DoctorData> doctorDatas;
    Context context;
    AllDoctorRepository allDoctorRepository;

    public FormSubmissionService(ZiggyService ziggyService, FormDataRepository formDataRepository, AllSettings allSettings) {
        this.ziggyService = ziggyService;
        this.formDataRepository = formDataRepository;
        this.allSettings = allSettings;
    }

    public void processSubmissions(List<FormSubmission> formSubmissions) {
        for (FormSubmission submission : formSubmissions) {
            logError("Entity Id " + submission.entityId() + "\n Form Name" + submission.formName() + "\n Instance= " + submission.instance());
            if (formDataRepository.submissionExists(submission.instanceId())) {
                formDataRepository.removeForm(submission.instanceId());
            }
            try {
                logError("Params== " + getParams(submission));
                ziggyService.saveForm(getParams(submission), submission.instance());
            } catch (Exception e) {
                logError(format("Form submission processing failed, with instanceId: {0}. Exception: {1}, StackTrace: {2}",
                        submission.instanceId(), e.getMessage(), ExceptionUtils.getStackTrace(e)));
            }
            formDataRepository.updateServerVersion(submission.instanceId(), submission.serverVersion());
            allSettings.savePreviousFormSyncIndex(submission.serverVersion());
        }
    }

    private String getParams(FormSubmission submission) {
        return new Gson().toJson(
                create(INSTANCE_ID_PARAM, submission.instanceId())
                        .put(ENTITY_ID_PARAM, submission.entityId())
                        .put(FORM_NAME_PARAM, submission.formName())
                        .put(VERSION_PARAM, submission.version())
                        .put(SYNC_STATUS, SYNCED.value())
                        .map());
    }

    public void processDoctorRecords(String data) {
        context = Context.getInstance();
        allDoctorRepository = context.allDoctorRepository();
        JSONObject formData = new JSONObject();

        try {
            if (data != null) {
                Log.e(TAG, "Data " + data);
                JSONArray dataArray = new JSONArray(data);
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject datajsonObject = dataArray.getJSONObject(i);
                    JSONArray visitsArray = datajsonObject.getJSONArray("riskinfo");

                    for (int j = 0; j < visitsArray.length(); j++) {

                        JSONObject riskJson = visitsArray.getJSONObject(j);
                        formData.put(wife_name, getDataFromJson("wifeName", datajsonObject));
                        formData.put(husband_name, getDataFromJson("husbandName", datajsonObject));
                        formData.put(DoctorFormDataConstants.village_name, getDataFromJson("village", datajsonObject));
                        formData.put(age, getDataFromJson("wifeAge", datajsonObject));

                        formData.put(poc_pending, "");
                        formData.put(entityId, getDataFromJson("entityidec", datajsonObject));
                        formData.put(anmId, getDataFromJson("anmId", datajsonObject));
                        formData.put(visit_type, getDataFromJson("visit_type", riskJson));
                        formData.put(documentId, getDataFromJson("id", riskJson));
                        String visit_typ2e = getDataFromJson("visit_type", riskJson);
                        if (getDataFromJson(visit_type, riskJson).equals(ancvisit)) {
                            formData.put(id_no, getDataFromJson("ancNumber", riskJson));
//                                            formData.put(status, "LMP: " + getData("lmp", riskJson) + "EDD: " + getData("edd", riskJson));

                            formData.put(lmp, getDataFromJson("lmp", riskJson));
                            formData.put(edd, getDataFromJson("edd", riskJson));

                            formData.put(anc_number, getDataFromJson("ancNumber", riskJson));
                            formData.put(anc_visit_number, getDataFromJson("ancVisitNumber", riskJson));
                            formData.put(anc_visit_date, getDataFromJson("ancVisitDate", riskJson));
                            formData.put(anc_entityId, getDataFromJson("entityid", riskJson));

                            formData.put(risk_symptoms, getDataFromJson("riskObservedDuringANC", riskJson));
                            formData.put(bp_sys, getDataFromJson("bpSystolic", riskJson));
                            formData.put(bp_dia, getDataFromJson("bpDiastolic", riskJson));
                            formData.put(temp_data, getDataFromJson("temperature", riskJson));
                            formData.put(weight_data, getDataFromJson("weight", riskJson));
                            formData.put(blood_glucose, getDataFromJson("bloodGlucoseData", riskJson));
                            formData.put(fetal_data, getDataFromJson("fetalData", riskJson));

                        } else if (getDataFromJson(visit_type, riskJson).equals(pncVisit)) {
//                                            formData.put(status, "Place: " + getData("pncVisitPlace", riskJson) + "Date: " + getData("pncVisitDate", riskJson));
                            formData.put(id_no, getDataFromJson("pncNumber", riskJson));
                            formData.put(pnc_number, getDataFromJson("pncNumber", riskJson));
                            formData.put(pnc_visit_date, getDataFromJson("pncVisitDate", riskJson));
                            formData.put(pnc_visit_place, getDataFromJson("pncVisitPlace", riskJson));
                            formData.put(pnc_entityId, getDataFromJson("entityid", riskJson));

                            formData.put(pnc_difficulties, getDataFromJson("difficulties1", riskJson));
                            formData.put(pnc_vaginal_problems, getDataFromJson("vaginalProblems", riskJson));
                            formData.put(pnc_abdominal_problems, getDataFromJson("abdominalProblems", riskJson));
                            formData.put(pnc_breast_problems, getDataFromJson("breastProblems", riskJson));
                            formData.put(pnc_urinating_problems, getDataFromJson("difficulties2", riskJson));
                            formData.put(kopfeel_heat_or_chills, getDataFromJson("hasFeverSymptoms", riskJson));

                            formData.put(bp_sys, getDataFromJson("bpSystolic", riskJson));
                            formData.put(bp_dia, getDataFromJson("bpDiastolic", riskJson));
                            formData.put(temp_data, getDataFromJson("temperature", riskJson));
                            formData.put(weight_data, getDataFromJson("weight", riskJson));
                            formData.put(blood_glucose, getDataFromJson("bloodGlucoseData", riskJson));
                            formData.put(fetal_data, getDataFromJson("fetalData", riskJson));
                        } else {

                            formData.put(child_report_child_disease_place, getDataFromJson("reportChildDiseasePlace", riskJson));
                            formData.put(child_report_child_disease_date, getDataFromJson("reportChildDiseaseDate", riskJson));

                            formData.put(child_name, getDataFromJson("name", riskJson));
                            formData.put(child_info, getDataFromJson("childInfo", riskJson));
                            formData.put(child_dob, getDataFromJson("dateOfBirth", riskJson));
                            formData.put(child_age, getDataFromJson("age", riskJson));
                            formData.put(child_entityId, getDataFromJson("entityid", riskJson));
                            formData.put(child_immediateReferral, getDataFromJson("immediateReferral", riskJson));
                            formData.put(child_immediateReferral_reason, getDataFromJson("immediateReferralReason", riskJson));
                            formData.put(child_no_of_osrs, getDataFromJson("numberOfORSGiven", riskJson));
                            formData.put(child_submission_date, getDataFromJson("submissionDate", riskJson));
                            formData.put(child_referral, getDataFromJson("childReferral", riskJson));
                            formData.put(child_report_child_disease_date, getDataFromJson("reportChildDiseaseDate", riskJson));
                            formData.put(child_report_child_disease_other, getDataFromJson("reportChildDiseaseOther", riskJson));
                            formData.put(child_report_child_disease, getDataFromJson("reportChildDisease", riskJson));
                            formData.put(child_signs_other, getDataFromJson("childSignsOther", riskJson));
                            formData.put(child_signs, getDataFromJson("childSigns", riskJson));
                        }
                        DoctorData doctorData = new DoctorData();
//                                        doctorData.setVisitType(getData("visit_type", datajsonObject));
                        doctorData.setFormInformation(formData.toString());
                        doctorData.setAnmId(getDataFromJson("anmId", datajsonObject));
                        doctorData.setCaseId(getDataFromJson("id", riskJson));
                        doctorData.setSyncStatus("pending");
                        String vl = getDataFromJson("village", datajsonObject);
                        doctorData.setVillageName(vl);
                        doctorData.setVisitType(getDataFromJson(visit_type, riskJson));
                        Log.e(TAG, "record" + doctorData.getFormInformation());
                        allDoctorRepository.addData(doctorData);
                        Log.e(TAG, "record" + "insert");
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private String getDataFromJson(String key, JSONObject formData) {
        if (formData != null) try {
            return (formData != null && formData.has(key) && formData.getString(key) != null) ? formData.getString(key) : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
