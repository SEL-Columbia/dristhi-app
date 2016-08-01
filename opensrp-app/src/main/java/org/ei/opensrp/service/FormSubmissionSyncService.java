package org.ei.opensrp.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.opensrp.Context;
import org.ei.opensrp.DristhiConfiguration;
import org.ei.opensrp.domain.FetchStatus;
import org.ei.opensrp.domain.Response;
import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.drishti.dto.form.FormSubmissionDTO;
import org.ei.opensrp.repository.AllSettings;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.repository.FormDataRepository;
import org.ei.opensrp.repository.ImageRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.ei.opensrp.convertor.FormSubmissionConvertor.toDomain;
import static org.ei.opensrp.domain.FetchStatus.*;
import static org.ei.opensrp.util.Log.logError;
import static org.ei.opensrp.util.Log.logInfo;

public class FormSubmissionSyncService {
    private static final String TAG = FormSubmissionSyncService.class.getCanonicalName();
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

    public FetchStatus sync() {
        FetchStatus dataStatus = nothingFetched;
        try {
            pushToServer();
            new ImageUploadSyncService((ImageRepository) Context.imageRepository());

            dataStatus = pullFromServer();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return dataStatus;
    }

    public FetchStatus sync(Map<String, String> syncParams) {
        FetchStatus dataStatus = nothingFetched;
        try {
            pushToServer();
            new ImageUploadSyncService((ImageRepository) Context.imageRepository());

            dataStatus = pullFromServer(syncParams);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return dataStatus;
    }

    public void pushToServer() {
        List<FormSubmission> pendingFormSubmissions = formDataRepository.getPendingFormSubmissions();
        if (pendingFormSubmissions.isEmpty()) {
            return;
        }
        String jsonPayload = mapToFormSubmissionDTO(pendingFormSubmissions);
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

    public FetchStatus pullFromServer() throws JSONException, UnsupportedEncodingException {


        while (true) {// why this?
            String uri = buildPullFromServerUrl(null);
            Response<String> response = httpAgent.fetch(uri);
            return processPullFromServerResponse(response);
        }
    }

    public FetchStatus pullFromServer(Map<String, String> syncParams) throws JSONException, UnsupportedEncodingException {

        while (true) {
            String uri = buildPullFromServerUrl(syncParams);
            Response<String> response = httpAgent.fetch(uri);
            return processPullFromServerResponse(response);
        }
    }

    private FetchStatus processPullFromServerResponse(Response<String> response) {
        FetchStatus dataStatus = nothingFetched;
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
            formSubmissionService.processSubmissions(toDomain(formSubmissions));
            dataStatus = fetched;
        }
        return dataStatus;
    }

    /**
     * This method helps pass params dynamically when syncing to the server
     * the map key will be the param name and value which is a list will be the param value
     *
     * @param syncParams
     * @return
     */
    private String buildPullFromServerUrl(Map<String, String> syncParams) throws JSONException, UnsupportedEncodingException {
        String anmId = allSharedPreferences.fetchRegisteredANM();
        int downloadBatchSize = configuration.syncDownloadBatchSize();
        String baseURL = configuration.dristhiBaseURL();
        String uri = format("{0}/{1}?anm-id={2}&timestamp={3}&batch-size={4}",
                baseURL,
                FORM_SUBMISSIONS_PATH,
                anmId,
                allSettings.fetchPreviousFormSyncIndex(),
                downloadBatchSize);
        if (syncParams != null && !syncParams.isEmpty()) {
            String params = "";
            for (Map.Entry<String, String> entry : syncParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                params += params + "&" + key + "=" + value;
            }
            uri = uri + params;

        }

        return uri;
    }

    private String mapToFormSubmissionDTO(List<FormSubmission> pendingFormSubmissions) {
        List<org.ei.drishti.dto.form.FormSubmissionDTO> formSubmissions = new ArrayList<org.ei.drishti.dto.form.FormSubmissionDTO>();
        for (FormSubmission pendingFormSubmission : pendingFormSubmissions) {
            formSubmissions.add(new org.ei.drishti.dto.form.FormSubmissionDTO(allSharedPreferences.fetchRegisteredANM(), pendingFormSubmission.instanceId(),
                    pendingFormSubmission.entityId(), pendingFormSubmission.formName(), pendingFormSubmission.instance(), pendingFormSubmission.version(),
                    pendingFormSubmission.formDataDefinitionVersion()));
        }
        return new Gson().toJson(formSubmissions);
    }
}