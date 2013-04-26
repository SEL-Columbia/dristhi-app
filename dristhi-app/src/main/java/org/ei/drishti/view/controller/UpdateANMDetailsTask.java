package org.ei.drishti.view.controller;

import android.os.AsyncTask;
import org.ei.drishti.domain.ANM;
import org.ei.drishti.service.ANMService;

import java.util.concurrent.locks.ReentrantLock;

import static org.ei.drishti.util.Log.logWarn;

public class UpdateANMDetailsTask {
    private final ANMService anmService;
    private static final ReentrantLock lock = new ReentrantLock();

    public UpdateANMDetailsTask(ANMService anmService) {
        this.anmService = anmService;
    }

    public void fetch(final AfterANMDetailsFetchListener afterFetchListener) {
        new AsyncTask<Void, Void, ANM>() {
            @Override
            protected ANM doInBackground(Void... params) {
                if (!lock.tryLock()) {
                    logWarn("Update ANM details is in progress, so going away.");
                    cancel(true);
                    return null;
                }
                try {
                    return anmService.fetchDetails();
                } finally {
                    lock.unlock();
                }
            }

            @Override
            protected void onPostExecute(ANM anm) {
                afterFetchListener.afterFetch(anm);
            }
        }.execute(null);

    }
}
