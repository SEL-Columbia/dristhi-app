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
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.*;
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

    public void processSubmissions(List<FormSubmission> formSubmissions, String villageName) {
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
            formDataRepository.updateServerVersion(submission.instanceId(), submission.serverVersion(), villageName);
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
                        formData.put(phoneNumber, getDataFromJson("phoneNumber", datajsonObject));
                        formData.put(entityId, getDataFromJson("entityidec", datajsonObject));

                        formData.put(poc_pending, getDataFromJson("pending", datajsonObject));
                        formData.put(anmId, getDataFromJson("anmId", datajsonObject));
                        formData.put(visit_type, getDataFromJson("visit_type", riskJson));
                        formData.put(documentId, getDataFromJson("id", riskJson));
                        formData.put(isHighRisk, getDataFromJson("isHighRisk", riskJson));
                        formData.put(anmPoc, getDataFromJson("anmPoc", riskJson));
                        String visit_typ2e = getDataFromJson("visit_type", riskJson);
                        if (getDataFromJson(visit_type, riskJson).equals(ancvisit)) {
                            formData.put(id_no, getDataFromJson("ancNumber", riskJson));
//                                            formData.put(status, "LMP: " + getData("lmp", riskJson) + "EDD: " + getData("edd", riskJson));

                            formData.put(lmp, getDataFromJson("lmp", riskJson));
                            formData.put(edd, getDataFromJson("edd", riskJson));

                            formData.put(anc_number, getDataFromJson("ancNumber", riskJson));
                            formData.put(anc_visit_number, getDataFromJson("ancVisitNumber", riskJson));
                            formData.put(anc_visit_date, getDataFromJson("ancVisitDate", riskJson));
//                            formData.put(anc_entityId, getDataFromJson("entityid", riskJson));
                            formData.put(visitId, getDataFromJson("entityid", riskJson));
                            formData.put(risk_symptoms, getDataFromJson("riskObservedDuringANC", riskJson));
                            formData.put(bp_sys, getDataFromJson("bpSystolic", riskJson));
                            formData.put(bp_dia, getDataFromJson("bpDiastolic", riskJson));
                            formData.put(DoctorFormDataConstants.bp_pulse, getDataFromJson("pulseRate", riskJson));

                            formData.put(temp_data, getDataFromJson("temperature", riskJson));
                            formData.put(weight_data, getDataFromJson("weight", riskJson));
                            formData.put(blood_glucose, getDataFromJson("bloodGlucoseData", riskJson));
                            formData.put(fetal_data, getDataFromJson("fetalData", riskJson));
                            formData.put(stethoscope_data, getDataFromJson("pstechoscopeData", riskJson));
                        } else if (getDataFromJson(visit_type, riskJson).equals(pncVisit)) {
//                                            formData.put(status, "Place: " + getData("pncVisitPlace", riskJson) + "Date: " + getData("pncVisitDate", riskJson));
                            formData.put(id_no, getDataFromJson("pncNumber", riskJson));
                            formData.put(pnc_number, getDataFromJson("pncNumber", riskJson));
                            formData.put(pnc_visit_date, getDataFromJson("pncVisitDate", riskJson));
                            formData.put(pnc_visit_place, getDataFromJson("pncVisitPlace", riskJson));
                            formData.put(visitId, getDataFromJson("entityid", riskJson));
//                            formData.put(pnc_entityId, getDataFromJson("entityid", riskJson));

                            formData.put(pnc_difficulties, getDataFromJson("difficulties1", riskJson));
                            formData.put(pnc_vaginal_problems, getDataFromJson("vaginalProblems", riskJson));
                            formData.put(pnc_abdominal_problems, getDataFromJson("abdominalProblems", riskJson));
                            formData.put(pnc_breast_problems, getDataFromJson("breastProblems", riskJson));
                            formData.put(pnc_urinating_problems, getDataFromJson("difficulties2", riskJson));
                            formData.put(kopfeel_heat_or_chills, getDataFromJson("hasFeverSymptoms", riskJson));

                            formData.put(bp_sys, getDataFromJson("bpSystolic", riskJson));
                            formData.put(bp_dia, getDataFromJson("bpDiastolic", riskJson));
                            formData.put(DoctorFormDataConstants.bp_pulse, getDataFromJson("pulseRate", riskJson));

                            formData.put(temp_data, getDataFromJson("temperature", riskJson));
                            formData.put(weight_data, getDataFromJson("weight", riskJson));
                            formData.put(blood_glucose, getDataFromJson("bloodGlucoseData", riskJson));
                            formData.put(hb_level, getDataFromJson("hbLevel", riskJson));
                            formData.put(stethoscope_data, getDataFromJson("pstechoscopeData", riskJson));
                        } else {
                            formData.put(id_no, getDataFromJson("childNumber", riskJson));
                            formData.put(child_report_child_disease_place, getDataFromJson("reportChildDiseasePlace", riskJson));
                            formData.put(child_report_child_disease_date, getDataFromJson("reportChildDiseaseDate", riskJson));

                            formData.put(child_name, getDataFromJson("name", riskJson));
                            formData.put(child_info, getDataFromJson("childInfo", riskJson));
                            formData.put(child_dob, getDataFromJson("dateOfBirth", riskJson));
                            formData.put(child_age, getDataFromJson("age", riskJson));
                            formData.put(child_gender, getDataFromJson("gender", riskJson));
//                            formData.put(child_entityId, getDataFromJson("entityid", riskJson));
                            formData.put(visitId, getDataFromJson("entityid", riskJson));
                            formData.put(temp_data, getDataFromJson("childTemperature", riskJson));
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

                            formData.put(child_sick_visit_date, getDataFromJson("sickVisitDate", riskJson));
                            formData.put(child_days_of_cough, getDataFromJson("numberOfDaysCough", riskJson));
                            formData.put(child_breaths_per_minute, getDataFromJson("breathsPerMinute", riskJson));
                            formData.put(child_days_of_diarrhea, getDataFromJson("daysOfDiarrhea", riskJson));
                            formData.put(child_blood_in_stool, getDataFromJson("bloodInStool", riskJson));
                            formData.put(child_vommit_every_thing, getDataFromJson("vommitEveryThing", riskJson));
                            formData.put(child_number_of_days_fever, getDataFromJson("daysOfFever", riskJson));


                        }
                        DoctorData doctorData = new DoctorData();
//                                        doctorData.setVisitType(getData("visit_type", datajsonObject));

                        doctorData.setFormInformation(formData.toString());
                        doctorData.setAnmId(getDataFromJson("anmId", datajsonObject));
                        doctorData.setCaseId(getDataFromJson("entityid", riskJson));
                        doctorData.setSyncStatus("pending");
                        String village = getDataFromJson("village", datajsonObject);
                        doctorData.setVillageName(village);
                        doctorData.setVisitType(getDataFromJson(visit_type, riskJson));
                        Log.e(TAG, "record" + doctorData.getFormInformation());
                        allDoctorRepository.addData(doctorData);
//                        String id = getData("id", riskJson);
//                        if (!tempList.contains(getData("id", riskJson))) {
//                            tempList.add(getData("id", riskJson));
//                            doctorDataArrayList.add(doctorData);
//                        }
//
//                        syncAdapter = new PendingConsultantBaseAdapter(NativeDoctorActivity.this, doctorDataArrayList);
//                        syncAdapter.notifyDataSetChanged();
//                        lv_pending_consultants.setAdapter(syncAdapter);
//                        anc_count = 0;
//                        pnc_count = 0;
//                        child_count = 0;
//                        if (doctorDataArrayList.size() != 0) {
//                            for (DoctorData doctorDataInfo : doctorDataArrayList) {
//                                if (doctorDataInfo.getVisitType().equalsIgnoreCase("ANC"))
//                                    anc_count = anc_count + 1;
//                                else if (doctorDataInfo.getVisitType().equalsIgnoreCase("PNC"))
//                                    pnc_count = pnc_count + 1;
//                                else
//                                    child_count = child_count + 1;
//                            }
//                            tv_anc_count.setText(anc_count + "");
//                            tv_pnc_count.setText(pnc_count + "");
//                            tv_child_count.setText(child_count + "");
//                        }
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
