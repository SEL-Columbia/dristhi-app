package org.ei.telemedicine.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.view.receiver.SyncBroadcastReceiver;

import static java.text.MessageFormat.format;
import static org.ei.telemedicine.event.Event.NETWORK_AVAILABLE;
import static org.ei.telemedicine.event.Event.ON_LOGOUT;
import static org.ei.telemedicine.util.Log.logInfo;
import static org.joda.time.DateTimeConstants.MILLIS_PER_MINUTE;
import static org.joda.time.DateTimeConstants.MILLIS_PER_SECOND;

public class DrishtiSyncScheduler {
    public static final int SYNC_INTERVAL = 2 * MILLIS_PER_MINUTE;
    public static final int SYNC_START_DELAY = 1 * MILLIS_PER_SECOND;
    private static Listener<Boolean> logoutListener;
    private static String TAG = "DrishtiSyncScheduler";

    public static void start(final Context context) {
        if (org.ei.telemedicine.Context.getInstance().IsUserLoggedOut()) {
            return;
        }
        NETWORK_AVAILABLE.notifyListeners(true);
        PendingIntent syncBroadcastReceiverIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, SyncBroadcastReceiver.class), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC,
                System.currentTimeMillis() + SYNC_START_DELAY,
                SYNC_INTERVAL,
                syncBroadcastReceiverIntent);

        logInfo(format("Scheduled to sync from server every {0} seconds.", SYNC_INTERVAL / 1000));

        attachListenerToStopSyncOnLogout(context);

    }

    private static void attachListenerToStopSyncOnLogout(final Context context) {
        ON_LOGOUT.removeListener(logoutListener);
        logoutListener = new Listener<Boolean>() {
            public void onEvent(Boolean data) {
                logInfo("User is logged out. Stopping Dristhi Sync scheduler.");
                stop(context);
            }
        };
        ON_LOGOUT.addListener(logoutListener);
    }

    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void startOnlyIfConnectedToNetwork(Context context, String userRole) {
        if (isNetWorkAvailable(context)) {
            start(context);
        } else {
            logInfo("Device not connected to network so not starting sync scheduler.");
        }

    }

    public static void stop(Context context) {
        PendingIntent syncBroadcastReceiverIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, SyncBroadcastReceiver.class), 0);
        NETWORK_AVAILABLE.notifyListeners(false);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(syncBroadcastReceiverIntent);

        logInfo("Unscheduled sync.");
    }
}
