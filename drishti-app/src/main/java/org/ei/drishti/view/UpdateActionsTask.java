package org.ei.drishti.view;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.event.Event;
import org.ei.drishti.service.ActionService;

public class UpdateActionsTask {
    private final LockingBackgroundTask task;
    private ActionService actionService;
    private Context context;

    public UpdateActionsTask(Context context, ActionService actionService, ProgressBar progressBar) {
        this.actionService = actionService;
        this.context = context;
        task = new LockingBackgroundTask(progressBar);
    }

    public void updateFromServer(final AfterFetchListener afterFetchListener) {
        task.doActionInBackground(new BackgroundAction<FetchStatus>() {
            public FetchStatus actionToDoInBackgroundThread() {
                return actionService.fetchNewActions();
            }

            public void postExecuteInUIThread(FetchStatus result) {
                if (result != null && context != null) {
                    Toast.makeText(context, result.displayValue(), Toast.LENGTH_SHORT).show();
                }
                afterFetchListener.afterFetch(result);
                Event.ON_DATA_FETCHED.notifyListeners(result);
            }
        });
    }
}
