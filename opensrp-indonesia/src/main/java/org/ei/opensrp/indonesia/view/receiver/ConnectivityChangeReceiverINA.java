package org.ei.opensrp.indonesia.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.ei.opensrp.indonesia.sync.BidanSyncScheduler;

import static org.ei.opensrp.util.Log.logInfo;

/**
 * Created by Dimas on 9/17/2015.
 */
public class ConnectivityChangeReceiverINA extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        logInfo("Connectivity change receiver triggered.");
        if (intent.getExtras() != null) {
            if (isDeviceDisconnectedFromNetwork(intent)) {
                logInfo("Device got disconnected from network. Stopping Bidan Sync scheduler.");
                // BidanSyncScheduler.stop(context);
                return;
            }
            if (isDeviceConnectedToNetwork(intent)) {
                logInfo("Device got connected to network. Trying to start Bidan Sync scheduler.");
                BidanSyncScheduler.start(context);
            }
        }
    }

    private boolean isDeviceDisconnectedFromNetwork(Intent intent) {
        return intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
    }

    private boolean isDeviceConnectedToNetwork(Intent intent) {
        NetworkInfo networkInfo = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
        return networkInfo != null && networkInfo.isConnected();
    }
}
