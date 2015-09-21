package org.ei.opensrp.indonesia.view.activity;

import android.app.Application;
import android.content.res.Configuration;

import org.ei.opensrp.Context;
import org.ei.opensrp.indonesia.view.receiver.SyncBidanBroadcastReceiver;
import org.ei.opensrp.sync.DrishtiSyncScheduler;

import java.util.Locale;

import static org.ei.opensrp.util.Log.logInfo;

/**
 * Created by Dimas Ciputra on 9/18/15.
 */
public class BidanApplication extends Application {
    private Locale locale = null;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        DrishtiSyncScheduler.setReceiverClass(SyncBidanBroadcastReceiver.class);

        context = Context.getInstance();
        context.updateApplicationContext(getApplicationContext());
        applyUserLanguagePreference();
        cleanUpSyncState();
    }

    private void cleanUpSyncState() {
        DrishtiSyncScheduler.stop(getApplicationContext());
        context.allSharedPreferences().saveIsSyncInProgress(false);
    }

    @Override
    public void onTerminate() {
        logInfo("Application is terminating. Stopping Bidan Sync scheduler and resetting isSyncInProgress setting.");
        cleanUpSyncState();
        super.onTerminate();
    }

    private void applyUserLanguagePreference() {
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = context.allSharedPreferences().fetchLanguagePreference();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            updateConfiguration(config);
        }
    }

    private void updateConfiguration(Configuration config) {
        config.locale = locale;
        Locale.setDefault(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
