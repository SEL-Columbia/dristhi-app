package org.ei.telemedicine.sync;

import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.util.Log;
import org.ei.telemedicine.view.BackgroundAction;
import org.ei.telemedicine.view.LockingBackgroundTask;
import org.ei.telemedicine.view.ProgressIndicator;

public class SaveANMLocationTask {
    private final LockingBackgroundTask task;
    private AllSettings allSettings;

    public SaveANMLocationTask(AllSettings allSettings) {
        this.allSettings = allSettings;
        task = new LockingBackgroundTask(new ProgressIndicator() {
            @Override
            public void setVisible() {
            }

            @Override
            public void setInvisible() {
                Log.logInfo("Successfully saved ANM Location information");
            }
        });
    }

    public void save(final String anmLocation) {
        task.doActionInBackground(new BackgroundAction<String>() {
            @Override
            public String actionToDoInBackgroundThread() {
                allSettings.saveANMLocation(anmLocation);
                return anmLocation;
            }

            @Override
            public void postExecuteInUIThread(String result) {
            }
        });
    }
}
