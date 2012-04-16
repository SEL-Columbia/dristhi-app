package org.ei.drishti.view;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import org.ei.drishti.adapter.AlertFilterCriterion;
import org.ei.drishti.controller.AlertController;

import java.util.concurrent.locks.ReentrantLock;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.util.Log.logVerbose;

public class UpdateAlertsTask {
    private AlertController alertController;
    private ProgressBar progressBar;
    private static final ReentrantLock lock = new ReentrantLock();
    private String visitCodePrefix = AlertFilterCriterion.All.visitCodePrefix();

    public UpdateAlertsTask(AlertController alertController, ProgressBar progressBar) {
        this.alertController = alertController;
        this.progressBar = progressBar;
    }

    public void updateDisplay() {
        updateFromServer(false);
    }

    private void refreshView() {
        alertController.refreshAlertsOnView(visitCodePrefix);
    }

    public void updateFromServer(final boolean shouldUpdate) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (shouldUpdate) {
                    if (!lock.tryLock()) {
                        logVerbose("Going away. Something else is holding the lock.");
                        return null;
                    }

                    try {
                        alertController.fetchNewAlerts();
                    } finally {
                        lock.unlock();
                    }
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            protected void onPostExecute(Void result) {
                refreshView();
                progressBar.setVisibility(INVISIBLE);
            }
        }.execute(null);
    }

    public void changeAlertFilterCriterion(String visitCodePrefix) {
        this.visitCodePrefix = visitCodePrefix;
    }
}
