package org.ei.telemedicine.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.sync.SyncAfterFetchListener;
import org.ei.telemedicine.sync.SyncProgressIndicator;
import org.ei.telemedicine.sync.UpdateActionsTask;

import java.util.ArrayList;

import static org.ei.telemedicine.util.Log.logInfo;

public class SyncBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        logInfo("Sync alarm triggered. Trying to Sync.");

        UpdateActionsTask updateActionsTask = new UpdateActionsTask(
                context,
                org.ei.telemedicine.Context.getInstance().actionService(),
                org.ei.telemedicine.Context.getInstance().formSubmissionSyncService(), new SyncProgressIndicator());
        final ArrayList<String> villagesList = org.ei.telemedicine.Context.getInstance().allSettings().getVillages();
        for (String villageName : villagesList)
            updateActionsTask.updateFromServer(new SyncAfterFetchListener(), villageName);
    }
}

