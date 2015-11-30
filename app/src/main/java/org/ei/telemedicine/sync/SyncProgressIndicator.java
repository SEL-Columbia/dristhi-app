package org.ei.telemedicine.sync;

import org.ei.telemedicine.view.ProgressIndicator;

import static org.ei.telemedicine.event.Event.SYNC_COMPLETED;
import static org.ei.telemedicine.event.Event.SYNC_STARTED;

public class SyncProgressIndicator implements ProgressIndicator {
    @Override
    public void setVisible() {
        org.ei.telemedicine.Context.getInstance().allSharedPreferences().saveIsSyncInProgress(true);
        SYNC_STARTED.notifyListeners(true);
    }

    @Override
    public void setInvisible() {
        org.ei.telemedicine.Context.getInstance().allSharedPreferences().saveIsSyncInProgress(false);
        SYNC_COMPLETED.notifyListeners(true);
    }
}
