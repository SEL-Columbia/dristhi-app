package org.ei.opensrp.indonesia.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.ei.opensrp.indonesia.view.receiver.SyncBidanBroadcastReceiver;
import org.ei.opensrp.sync.DrishtiSyncScheduler;

import static java.text.MessageFormat.format;
import static org.ei.opensrp.util.Log.logInfo;

/**
 * Created by Dimas on 9/17/2015.
 */
public class BidanSyncScheduler extends DrishtiSyncScheduler {

    public static void start(final Context context) {
        if (org.ei.opensrp.Context.getInstance().IsUserLoggedOut()) {
            return;
        }

        PendingIntent syncBroadcastReceiverIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, SyncBidanBroadcastReceiver.class), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC,
                System.currentTimeMillis() + SYNC_START_DELAY,
                SYNC_INTERVAL,
                syncBroadcastReceiverIntent);

        logInfo(format("Scheduled to sync from server every {0} seconds.", SYNC_INTERVAL / 1000));

        // attachListenerToStopSyncOnLogout(context);
    }

    public static void startOnlyIfConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            start(context);
        } else {
            logInfo("Device not connected to network so not starting sync scheduler.");
        }
    }
}
