package org.ei.drishti.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.protocol.HTTP;
import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.DownloadStatus;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.domain.FetchVersionStatus;
import org.ei.drishti.domain.FormDefinitionVersion;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.SyncStatus;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.FormsVersionRepository;
import org.ei.drishti.util.DownloadForm;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.ei.drishti.AllConstants.FORM_DROPBOX;
import static org.ei.drishti.domain.FetchStatus.fetchedFailed;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;
import static org.ei.drishti.domain.ResponseStatus.failure;
import static org.ei.drishti.repository.FormsVersionRepository.*;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.domain.SyncStatus.*;
import static org.ei.drishti.util.Log.logError;

/**
 * Created by Dimas Ciputra on 3/22/15.
 */
public class AllFormVersionService {

    private FormsVersionRepository formsVersionRepository;
    private HTTPAgent agent = null;
    private final int MAX_DOWNLOAD_SIZE = 5;

    public AllFormVersionService(FormsVersionRepository formsVersionRepository, HTTPAgent agent) {
        this.formsVersionRepository = formsVersionRepository;
        this.agent = agent;
    }

    public void processForms(List<FormDefinitionVersion> forms) {
        for(FormDefinitionVersion form : forms) {
            if(!formsVersionRepository.formExists(form.getFormName())) {
                /* Form not exist yet, add it to repository */
                formsVersionRepository.addFormVersion(getParams(form));
            } else {
                String currentVersion = formsVersionRepository.getVersion(form.getFormName());
                /* Form is exist, update it */
                if(Integer.parseInt(currentVersion) < Integer.parseInt(form.getVersion())) {
                    formsVersionRepository.updateServerVersion(form.getFormName(), form.getVersion());
                }
            }
        }
    }

    public DownloadStatus processDownloadAllFromServer() {
        String download = DownloadForm.DownloadFromURL(FORM_DROPBOX, "form.zip");
        if(download!=null) {
            return DownloadStatus.failedDownloaded;
        }

        List<FormDefinitionVersion> list = formsVersionRepository.getAllFormWithSyncStatus(SyncStatus.PENDING);
        for(FormDefinitionVersion l : list) {
            formsVersionRepository.updateSyncStatus(l.getFormName(), SyncStatus.SYNCED);
        }
        return DownloadStatus.downloaded;
    }

    public DownloadStatus processDownloadPendingForms(List<FormDefinitionVersion> pendingFormList) {
        if(pendingFormList.size() > MAX_DOWNLOAD_SIZE) {
            return processDownloadAllFromServer();
        }

        for(FormDefinitionVersion l : pendingFormList) {
            String downloadLink = pullLinkFromServer(l.getFormName());
            if(downloadLink == null) return DownloadStatus.failedDownloaded;
            String download = DownloadForm.DownloadFromURL(downloadLink, l.getFormName()+".zip");
            if(download!=null) {
                return DownloadStatus.failedDownloaded;
            } else {
                formsVersionRepository.updateSyncStatus(l.getFormName(), SyncStatus.SYNCED);
            }
        }
        return DownloadStatus.downloaded;
    }

    private String pullLinkFromServer(String formName) {
        String url = AllConstants.BASE_URL_TEST + formName;
        Response<String> response = agent.fetch(url);

        if(response.isFailure()) {
            logError("Form definition pull error");
            return null;
        }

        if(!response.payload().isEmpty()) {
            Map<String, String> jsonResponse = new Gson().fromJson(response.payload(), Map.class);
            return jsonResponse.get("link");
        }

        return null;
    }

    private Map<String, String> getParams(FormDefinitionVersion formDefinitionVersion) {
        return create(FORM_NAME_COLUMN, formDefinitionVersion.getFormName())
                    .put(VERSION_COLUMN, formDefinitionVersion.getVersion())
                    .put(SYNC_STATUS_COLUMN, PENDING.value())
                    .map();
    }
}
