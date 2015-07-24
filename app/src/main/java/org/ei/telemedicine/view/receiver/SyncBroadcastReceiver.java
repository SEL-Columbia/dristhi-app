package org.ei.telemedicine.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.ei.telemedicine.sync.SyncAfterFetchListener;
import org.ei.telemedicine.sync.SyncProgressIndicator;
import org.ei.telemedicine.sync.UpdateActionsTask;

import static org.ei.telemedicine.util.Log.logInfo;

public class SyncBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        logInfo("Sync alarm triggered. Trying to Sync.");

        UpdateActionsTask updateActionsTask = new UpdateActionsTask(
                context,
                org.ei.telemedicine.Context.getInstance().actionService(),
                org.ei.telemedicine.Context.getInstance().formSubmissionSyncService(), new SyncProgressIndicator());

        updateActionsTask.updateFromServer(new SyncAfterFetchListener());
    }
}

