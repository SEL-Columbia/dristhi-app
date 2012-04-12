package org.ei.drishti.view;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import org.ei.drishti.controller.AlertController;

import java.util.concurrent.locks.ReentrantLock;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
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
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (!lock.tryLock()) {
                    logVerbose("Going away. Something else is holding the lock.");
                    return null;
                }

                try {
                    alertController.fetchNewAlerts();
                } finally {
                    lock.unlock();
                }

                return null;
            }

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            protected void onPostExecute(Void result) {
                progressBar.setVisibility(INVISIBLE);
                updateDisplay();
            }
        }.execute(null);
    }
}
