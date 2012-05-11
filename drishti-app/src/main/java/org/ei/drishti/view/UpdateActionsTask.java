package org.ei.drishti.view;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.service.ActionService;

import java.util.concurrent.locks.ReentrantLock;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.util.Log.logVerbose;

public class UpdateActionsTask {
    private Context context;
    private ActionService actionService;
    private ProgressBar progressBar;
    private static final ReentrantLock lock = new ReentrantLock();

    public UpdateActionsTask(Context context, ActionService actionService, ProgressBar progressBar) {
        this.context = context;
        this.actionService = actionService;
        this.progressBar = progressBar;
    }

    public void updateFromServer(final AfterFetchListener afterFetchListener) {
        new AsyncTask<Void, Void, FetchStatus>() {
            @Override
            protected FetchStatus doInBackground(Void... params) {
                if (!lock.tryLock()) {
                    logVerbose("Going away. Something else is holding the lock.");
                    return null;
                }
                try {
                    return actionService.fetchNewActions();
                } finally {
                    lock.unlock();
                }
            }

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            protected void onPostExecute(FetchStatus result) {
                if (result != null && context != null) {
                    Toast.makeText(context, result.displayValue(), Toast.LENGTH_SHORT).show();
                }
                afterFetchListener.afterFetch(result);
                progressBar.setVisibility(INVISIBLE);
            }
        }.execute(null);
    }
}
