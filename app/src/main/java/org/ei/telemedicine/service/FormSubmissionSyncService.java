package org.ei.telemedicine.service;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.DristhiConfiguration;
import org.ei.telemedicine.doctor.DoctorData;
import org.ei.telemedicine.doctor.DoctorFormDataConstants;
import org.ei.telemedicine.doctor.PendingConsultantBaseAdapter;
import org.ei.telemedicine.domain.ANM;
import org.ei.telemedicine.domain.FetchStatus;
import org.ei.telemedicine.domain.ProfileImage;
import org.ei.telemedicine.domain.Response;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.dto.form.FormSubmissionDTO;
import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.repository.AllSharedPreferences;
import org.ei.telemedicine.repository.FormDataRepository;
import org.ei.telemedicine.repository.ImageRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.ei.telemedicine.AllConstants.DOC_DATA_URL_PATH;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_VISIT;
import static org.ei.telemedicine.AllConstants.PSTETHOSCOPE_DATA;
import static org.ei.telemedicine.convertor.FormSubmissionConvertor.toDomain;
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
import static org.ei.telemedicine.domain.FetchStatus.fetched;
import static org.ei.telemedicine.domain.FetchStatus.fetchedFailed;
import static org.ei.telemedicine.domain.FetchStatus.nothingFetched;
import static org.ei.telemedicine.util.Log.logError;
import static org.ei.telemedicine.util.Log.logInfo;

public class FormSubmissionSyncService {
    public static final String FORM_SUBMISSIONS_PATH = "form-submissions";

    private final HTTPAgent httpAgent;
    private final FormDataRepository formDataRepository;
    private AllSettings allSettings;
    private AllSharedPreferences allSharedPreferences;
    private FormSubmissionService formSubmissionService;
    private DristhiConfiguration configuration;

    public FormSubmissionSyncService(FormSubmissionService formSubmissionService, HTTPAgent httpAgent,
                                     FormDataRepository formDataRepository, AllSettings allSettings,
                                     AllSharedPreferences allSharedPreferences, DristhiConfiguration configuration) {
        this.formSubmissionService = formSubmissionService;
        this.httpAgent = httpAgent;
        this.formDataRepository = formDataRepository;
        this.allSettings = allSettings;
        this.allSharedPreferences = allSharedPreferences;
        this.configuration = configuration;
    }

    public FetchStatus sync(String villageName) {
        pushToServer();
        new ImageUploadSyncService((ImageRepository) Context.getInstance().imageRepository());
        return pullFromServer(villageName);
    }


    public void pushToServer() {
        List<FormSubmission> pendingFormSubmissions = formDataRepository.getPendingFormSubmissions();
        String audiofilePath = "";
        if (pendingFormSubmissions.isEmpty()) {
            return;
        } else {
            for (int index = 0; index < pendingFormSubmissions.size(); index++) {
                FormSubmission formSubmission = pendingFormSubmissions.get(index);
                if (formSubmission.formName().equals(ANC_VISIT) || formSubmission.formName().equals(ANC_VISIT_EDIT) || formSubmission.formName().equals(PNC_VISIT)) {
                    try {
                        String entityId = formSubmission.entityId();
                        String formData = formSubmission.instance();
                        JSONObject formInstanceJsonData = new JSONObject(formData);
                        Log.e("JsonD", formInstanceJsonData + "");
                        JSONObject instanceData = formInstanceJsonData.getJSONObject("form");
                        JSONArray fieldsJsonArray = instanceData.getJSONArray("fields");
                        for (int i = fieldsJsonArray.length() - 1; i >= 0; i--) {
                            JSONObject jsonData = fieldsJsonArray.getJSONObject(i);
                            if (jsonData.getString("name").equals(PSTETHOSCOPE_DATA)) {
                                String fileLocation = jsonData.has("value") ? jsonData.getString("value") : "";
                                if (fileLocation.trim().length() != 0) {
                                    ProfileImage profileImage = new ProfileImage("", Context.getInstance().allSharedPreferences().fetchRegisteredANM(), entityId, "audio/x-wav", fileLocation, "");
                                    String response = Context.getInstance().getHttpAgent().httpImagePost(Context.getInstance().configuration().dristhiBaseURL() + "/multimedia-file", profileImage);
                                    if (response.trim().length() != 0) {
                                        jsonData.put("value", response);
                                        fieldsJsonArray.put(i, jsonData);
                                    }
                                }
                            }
                        }
                        instanceData.put("fields", fieldsJsonArray);
                        formInstanceJsonData.put("form", instanceData);
                        FormSubmission updateFormSubmission = new FormSubmission(formSubmission.instanceId(), formSubmission.entityId(), formSubmission.formName(), formInstanceJsonData.toString(), formSubmission.version(), formSubmission.syncStatus(), formSubmission.formDataDefinitionVersion());
                        pendingFormSubmissions.set(index, updateFormSubmission);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        String jsonPayload = mapToFormSubmissionDTO(pendingFormSubmissions);
        logError("Json Data " + jsonPayload);
        Response<String> response = httpAgent.post(
                format("{0}/{1}",
                        configuration.dristhiBaseURL(),
                        FORM_SUBMISSIONS_PATH),
                jsonPayload);
        if (response.isFailure()) {
            logError(format("Form submissions sync failed. Submissions:  {0}", pendingFormSubmissions));
            return;
        }
        formDataRepository.markFormSubmissionsAsSynced(pendingFormSubmissions);
        logInfo(format("Form submissions sync successfully. Submissions:  {0}", pendingFormSubmissions));
    }

    public FetchStatus pullFromServer(String villageName) {
        FetchStatus dataStatus = nothingFetched;
        String userId = allSharedPreferences.fetchRegisteredANM();

        if (allSharedPreferences.getUserRole().equals(AllConstants.DOCTOR_ROLE)) {
            //For Doctor Module
            String baseDocURL = configuration.dristhiDjangoBaseURL();

            String uri = format("{0}/{1}?docname={2}&pwd={3}",
                    baseDocURL,
                    DOC_DATA_URL_PATH,
                    userId,
                    allSharedPreferences.getPwd()
            );
            logError("url= " + uri);
            Response<String> response = httpAgent.fetch(uri);
            if (response.isFailure()) {
                logError(format("Fetching doctor records failed."));
                return fetchedFailed;
            }

            if (response == null) {
                return dataStatus;
            } else {
                formSubmissionService.processDoctorRecords(response.payload());
                dataStatus = fetched;
            }

        } else {
            int downloadBatchSize = configuration.syncDownloadBatchSize();
            String baseURL = configuration.dristhiBaseURL();
            Log.e("Base Url", baseURL);
//            ArrayList<String> villagesList = new ArrayList<String>();
//            villagesList.add("Gazi Pur");
//            villagesList.add("Kotla");
//            villagesList.add("Dalu Pura");
//            for (String villageName : villagesList) {
            while (true) {
                String uri = format("{0}/{1}?anm-id={2}&timestamp={3}&batch-size={4}&village={5}",
                        baseURL,
                        FORM_SUBMISSIONS_PATH,
                        userId,
                        formDataRepository.getLastFormIndex(villageName),
                        downloadBatchSize, villageName.replace(" ", "%20"));
                logError("url= " + uri);
                Response<String> response = httpAgent.fetch(uri);
                if (response.isFailure()) {
                    logError(format("Form submissions pull failed."));
                    return fetchedFailed;
                }
                List<FormSubmissionDTO> formSubmissions = new Gson().fromJson(response.payload(),
                        new TypeToken<List<FormSubmissionDTO>>() {
                        }.getType());
                if (formSubmissions.isEmpty()) {
                    return dataStatus;
                } else {
                    List<FormSubmission> submissions = toDomain(formSubmissions);
                    formSubmissionService.processSubmissions(submissions, villageName);
                    dataStatus = fetched;
                }
            }
//            }
        }
        return dataStatus;
    }

    private String mapToFormSubmissionDTO(List<FormSubmission> pendingFormSubmissions) {
        List<org.ei.telemedicine.dto.form.FormSubmissionDTO> formSubmissions = new ArrayList<org.ei.telemedicine.dto.form.FormSubmissionDTO>();
        for (FormSubmission pendingFormSubmission : pendingFormSubmissions) {
            formSubmissions.add(new org.ei.telemedicine.dto.form.FormSubmissionDTO(allSharedPreferences.fetchRegisteredANM(), pendingFormSubmission.instanceId(),
                    pendingFormSubmission.entityId(), pendingFormSubmission.formName(), pendingFormSubmission.instance(), pendingFormSubmission.version(),
                    pendingFormSubmission.formDataDefinitionVersion()));
        }
        return new Gson().toJson(formSubmissions);
    }


}
