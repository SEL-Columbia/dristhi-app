package org.ei.drishti.view;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.FetchStatus;

import java.util.concurrent.locks.ReentrantLock;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.Log.logVerbose;

public class UpdateAlertsTask {
    private AlertController alertController;
    private ProgressBar progressBar;
    private static final ReentrantLock lock = new ReentrantLock();

    public UpdateAlertsTask(AlertController alertController, ProgressBar progressBar) {
        this.alertController = alertController;
        this.progressBar = progressBar;
    }

    public void updateDisplay() {
        alertController.refreshAlertsOnView();
    }

    public void updateFromServer() {
        new AsyncTask<Void, Void, FetchStatus>() {
            @Override
            protected FetchStatus doInBackground(Void... params) {
                if (!lock.tryLock()) {
                    logVerbose("Going away. Something else is holding the lock.");
                    return null;
                }

                try {
                    return alertController.fetchNewAlerts();
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
                if (fetched.equals(result)) {
                    updateDisplay();
                }
                progressBar.setVisibility(INVISIBLE);
            }
        }.execute(null);
    }
}
