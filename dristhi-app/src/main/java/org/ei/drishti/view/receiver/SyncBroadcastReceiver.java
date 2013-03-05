package org.ei.drishti.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.ei.drishti.sync.SyncAfterFetchListener;
import org.ei.drishti.sync.SyncProgressIndicator;
import org.ei.drishti.sync.UpdateActionsTask;

public class SyncBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(
                context,
                org.ei.drishti.Context.getInstance().actionService(), new SyncProgressIndicator());

        updateActionsTask.updateFromServer(new SyncAfterFetchListener());
    }
}

