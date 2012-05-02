package org.ei.drishti.view;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.FetchStatus;

import java.util.concurrent.locks.ReentrantLock;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.Log.logVerbose;

public class UpdateAlertsTask {
    private Context context;
    private AlertController alertController;
    private ProgressBar progressBar;
    private static final ReentrantLock lock = new ReentrantLock();

    public UpdateAlertsTask(Context context, AlertController alertController, ProgressBar progressBar) {
        this.context = context;
        this.alertController = alertController;
        this.progressBar = progressBar;
    }

    public void updateDisplay() {
        alertController.refreshAlertsFromDB();
    }

    public void updateFromServer() {
        updateFromServer(new AfterChangeListener() {
            public void afterChangeHappened() {
            }
        });
    }

    public void updateFromServer(final AfterChangeListener afterChangeListener) {
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
                if (context!=null) {
                    Toast.makeText(context, result.displayValue(), Toast.LENGTH_SHORT).show();
                }
                afterChangeListener.afterChangeHappened();
                progressBar.setVisibility(INVISIBLE);
            }
        }.execute(null);
    }
}
