package org.ei.drishti.sync;

import org.ei.drishti.view.ProgressIndicator;

import static org.ei.drishti.event.Event.SYNC_COMPLETED;
import static org.ei.drishti.event.Event.SYNC_STARTED;

public class SyncProgressIndicator implements ProgressIndicator {
    @Override
    public void setVisible() {
        org.ei.drishti.Context.getInstance().allSharedPreferences().saveIsSyncInProgress(true);
        SYNC_STARTED.notifyListeners(true);
    }

    @Override
    public void setInvisible() {
        org.ei.drishti.Context.getInstance().allSharedPreferences().saveIsSyncInProgress(false);
        SYNC_COMPLETED.notifyListeners(true);
    }
}
