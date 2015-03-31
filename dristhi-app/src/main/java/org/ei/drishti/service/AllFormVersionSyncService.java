package org.ei.drishti.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.drishti.Context;
import org.ei.drishti.domain.DownloadStatus;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.domain.FormDefinitionVersion;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.SyncStatus;
import org.ei.drishti.repository.FormsVersionRepository;
import org.ei.drishti.util.ZipUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import static org.ei.drishti.domain.DownloadStatus.*;

import static org.ei.drishti.util.Log.logError;

/**
 * Created by Dimas Ciputra on 3/23/15.
 */
public class AllFormVersionSyncService {

    private AllFormVersionService allFormVersionService;
    private final HTTPAgent httpAgent;
    private final Context context;
    private final FormsVersionRepository formsVersionRepository;

    public AllFormVersionSyncService(AllFormVersionService allFormVersionService, HTTPAgent httpAgent,
                                     Context context, FormsVersionRepository formsVersionRepository) {
        this.allFormVersionService = allFormVersionService;
        this.formsVersionRepository = formsVersionRepository;
        this.httpAgent = httpAgent;
        this.context = context;
    }

    public FetchStatus pullFormDefinitionFromServer() {
        FetchStatus status = FetchStatus.nothingFetched;
        String baseUrl = context.baseURLTest();
        String uri = baseUrl + "/forms";

        Response<String> response = httpAgent.fetch(uri);
        if(response.isFailure()) {
            logError("Form definition pull error");
            status = FetchStatus.fetchedFailed;
            return status;
        }

        List<FormDefinitionVersion> formDefinition = new Gson().fromJson(response.payload(),
                new TypeToken<List<FormDefinitionVersion>>(){}.getType());

        if(formDefinition.size() > 0) {
            allFormVersionService.processForms(formDefinition);
            status = FetchStatus.fetched;
        } else {
            return status;
        }
        return status;
    }

    public DownloadStatus downloadAllPendingFormFromServer() {
        DownloadStatus status = nothingDownloaded;
        List<FormDefinitionVersion> list =  formsVersionRepository.getAllFormWithSyncStatus(SyncStatus.PENDING);

        if(list.isEmpty()) {
            return status;
        } else {
            status = allFormVersionService.processDownloadPendingForms(list);
        }
        return status;
    }

    public void unzipAllDownloadedFormFile() {
        File dir = new File(FormPathService.sdcardPathDownload);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().matches(".*\\.zip");
            }
        };
        File[] zipFiles = dir.listFiles(filter);
        for(File f : zipFiles) {
            ZipUtil zipUtil = new ZipUtil(f.getAbsolutePath(), FormPathService.sdcardPath);
            zipUtil.unzip();
        }
    }

}