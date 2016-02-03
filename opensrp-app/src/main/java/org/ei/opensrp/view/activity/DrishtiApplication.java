package org.ei.opensrp.view.activity;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.ei.opensrp.Context;
import org.ei.opensrp.sync.DrishtiSyncScheduler;
import org.ei.opensrp.view.receiver.SyncBroadcastReceiver;

import java.util.Locale;

import static org.ei.opensrp.util.Log.logInfo;

public class DrishtiApplication extends Application {
    private static final String TAG = "DrishtiApplication";

    protected Locale locale = null;
    protected Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void logoutCurrentUser(){
        Log.e(TAG, "Child classes should implement this function");
    }
}