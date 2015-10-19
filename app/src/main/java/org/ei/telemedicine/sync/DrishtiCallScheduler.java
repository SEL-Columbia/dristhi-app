package org.ei.telemedicine.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.view.receiver.CallReceiver;

import java.util.Calendar;

import static org.ei.telemedicine.event.Event.NETWORK_AVAILABLE;
import static org.ei.telemedicine.event.Event.ON_LOGOUT;
import static org.ei.telemedicine.util.Log.logInfo;
import static org.joda.time.DateTimeConstants.MILLIS_PER_SECOND;

/**
 * Created by Beloved on 9/21/2015.
 */
public class DrishtiCallScheduler {
    public static final int CALL_START_DELAY = 2 * MILLIS_PER_SECOND;
    public static final int CALL_CHECK = 30;
    private static Listener<Boolean> logoutListener;
    private static String TAG = "DrishtiCallScheduler";

    public static void start(final Context context) {
        if (org.ei.telemedicine.Context.getInstance().IsUserLoggedOut()) {
            return;
        }
        NETWORK_AVAILABLE.notifyListeners(true);
        Calendar updateTime = Calendar.getInstance();
        updateTime.set(Calendar.SECOND, CALL_CHECK);
        Intent alarmIntent = new Intent(context, CallReceiver.class);
        PendingIntent recurringCall= PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.cancel(recurringCall);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis() + CALL_START_DELAY, CALL_START_DELAY, recurringCall);
        //attachListenerToStopSyncOnLogout(context);
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

    public static void startOnlyIfConnectedToNetwork(Context context) {
        if (isNetWorkAvailable(context)) {
            Toast.makeText(context,"started",Toast.LENGTH_LONG).show();
            start(context);
        } else {
            logInfo("Device not connected to network so not starting sync scheduler.");
        }

    }

    public static void stop(Context context) {
        PendingIntent syncBroadcastReceiverIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, CallReceiver.class), 0);
        NETWORK_AVAILABLE.notifyListeners(false);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(syncBroadcastReceiverIntent);

        logInfo("Unscheduled sync.");
    }
}
