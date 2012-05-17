package org.ei.drishti.view;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.concurrent.locks.ReentrantLock;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.ei.drishti.util.Log.logVerbose;

public class LockingBackgroundTask {
    private ProgressBar progressBar;
    private static final ReentrantLock lock = new ReentrantLock();

    public LockingBackgroundTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public <T> void doActionInBackground(final BackgroundAction<T> backgroundAction) {
        new AsyncTask<Void, Void, T>() {
            @Override
            protected T doInBackground(Void... params) {
                if (!lock.tryLock()) {
                    logVerbose("Going away. Something else is holding the lock.");
                    return null;
                }
                try {
                    return backgroundAction.actionToDoInBackgroundThread();
                } finally {
                    lock.unlock();
                }
            }

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            protected void onPostExecute(T result) {
                backgroundAction.postExecuteInUIThread(result);
                progressBar.setVisibility(INVISIBLE);
            }
        }.execute(null);
    }
}