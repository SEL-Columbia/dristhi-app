package org.ei.drishti.sync;

import android.content.Context;
import android.widget.Toast;

import org.ei.drishti.domain.DownloadStatus;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.service.ActionService;
import org.ei.drishti.service.AllFormVersionSyncService;
import org.ei.drishti.service.FormSubmissionSyncService;
import org.ei.drishti.view.BackgroundAction;
import org.ei.drishti.view.LockingBackgroundTask;
import org.ei.drishti.view.ProgressIndicator;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;
import static org.ei.drishti.util.Log.logInfo;

public class UpdateActionsTask {
    private final LockingBackgroundTask task;
    private ActionService actionService;
    private Context context;
    private FormSubmissionSyncService formSubmissionSyncService;
    private AllFormVersionSyncService allFormVersionSyncService;

    public UpdateActionsTask(Context context, ActionService actionService, FormSubmissionSyncService formSubmissionSyncService, ProgressIndicator progressIndicator,
                             AllFormVersionSyncService allFormVersionSyncService) {
        this.actionService = actionService;
        this.context = context;
        this.formSubmissionSyncService = formSubmissionSyncService;
        this.allFormVersionSyncService = allFormVersionSyncService;
        task = new LockingBackgroundTask(progressIndicator);
    }

    public void updateFromServer(final AfterFetchListener afterFetchListener) {
        if (org.ei.drishti.Context.getInstance().IsUserLoggedOut()) {
            logInfo("Not updating from server as user is not logged in.");
            return;
        }

        task.doActionInBackground(new BackgroundAction<FetchStatus>() {
            public FetchStatus actionToDoInBackgroundThread() {
                FetchStatus fetchStatusForForms = formSubmissionSyncService.sync();
                FetchStatus fetchStatusForActions = actionService.fetchNewActions();
                FetchStatus fetchVersionStatus = allFormVersionSyncService.pullFormDefinitionFromServer();
                DownloadStatus downloadStatus = allFormVersionSyncService.downloadAllPendingFormFromServer();

                if(downloadStatus == DownloadStatus.downloaded) {
                    // Unzip the downloaded form and delete zip
                    allFormVersionSyncService.unzipAllDownloadedFormFile();
                }

                if(fetchStatusForActions == fetched || fetchStatusForForms == fetched ||
                        fetchVersionStatus == fetched || downloadStatus == DownloadStatus.downloaded)
                    return fetched;

                return fetchStatusForForms;
            }

            public void postExecuteInUIThread(FetchStatus result) {
                if (result != null && context != null && result != nothingFetched) {
                    Toast.makeText(context, result.displayValue(), Toast.LENGTH_SHORT).show();
                }
                afterFetchListener.afterFetch(result);
            }
        });
    }
}