package org.ei.drishti.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.receiver.SyncBroadcastReceiver;

import static android.widget.Toast.LENGTH_SHORT;
import static java.text.MessageFormat.format;
import static org.ei.drishti.event.Event.ON_LOGOUT;
import static org.ei.drishti.util.Log.logInfo;
import static org.joda.time.DateTimeConstants.MILLIS_PER_MINUTE;

public class DrishtiSyncScheduler {

    public static final int SYNC_INTERVAL = MILLIS_PER_MINUTE;
    //This has to be a field despite what Idea suggests as the ON_LOGOUT event holds a weak reference to this object to send a notification when user logs out.
    private static Listener<Boolean> logoutListener;

    public static void start(final Context context) {
        PendingIntent triggerSyncActivity = PendingIntent.getBroadcast(context, 0,
                new Intent(context, SyncBroadcastReceiver.class), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC,
                SystemClock.elapsedRealtime() + 2 * MILLIS_PER_MINUTE, SYNC_INTERVAL,
                triggerSyncActivity);

        logInfo(format("Scheduled to sync every {0} seconds.", SYNC_INTERVAL / 1000));
        Toast.makeText(context, format("Scheduled to sync every {0} seconds.", SYNC_INTERVAL / 1000), LENGTH_SHORT).show();

        logoutListener = new Listener<Boolean>() {
            public void onEvent(Boolean data) {
                stop(context);
            }
        };
        ON_LOGOUT.addListener(logoutListener);
    }

    public static void stop(Context context) {
        PendingIntent triggerSyncActivity = PendingIntent.getBroadcast(context, 0,
                new Intent(context, SyncBroadcastReceiver.class), 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(triggerSyncActivity);

        logInfo("Unscheduled sync.");
        Toast.makeText(context, "Unscheduled sync.", LENGTH_SHORT).show();
    }
}
