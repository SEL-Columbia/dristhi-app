package org.ei.opensrp.indonesia.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static org.ei.opensrp.util.Log.logInfo;

/**
 * Created by Dimas on 9/17/2015.
 */
public class SyncBidanBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        logInfo("Sync alarm triggered. Trying to Sync.");
    }
}
