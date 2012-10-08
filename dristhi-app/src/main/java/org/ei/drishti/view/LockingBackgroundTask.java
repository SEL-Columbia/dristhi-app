package org.ei.drishti.view;

import android.os.AsyncTask;

import java.util.concurrent.locks.ReentrantLock;

import static org.ei.drishti.util.Log.logVerbose;

public class LockingBackgroundTask {
    private ProgressIndicator indicator;
    private static final ReentrantLock lock = new ReentrantLock();

    public LockingBackgroundTask(ProgressIndicator progressIndicator) {
        this.indicator = progressIndicator;
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
                indicator.setVisibile();
            }

            @Override
            protected void onPostExecute(T result) {
                backgroundAction.postExecuteInUIThread(result);
                indicator.setInvisible();
            }
        }.execute(null);
    }
}
