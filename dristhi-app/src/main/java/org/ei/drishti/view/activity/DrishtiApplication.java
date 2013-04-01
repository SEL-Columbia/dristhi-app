package org.ei.drishti.view.activity;

import android.app.Application;
import android.content.res.Configuration;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.ei.drishti.Context;
import org.ei.drishti.sync.DrishtiSyncScheduler;

import java.util.Locale;

import static org.ei.drishti.util.Log.logInfo;

@ReportsCrashes(formKey = "dFdKSGVjdDJ4ZThpRHQ0bm50VkdDeGc6MQ")
public class DrishtiApplication extends Application {
    private Locale locale = null;
    private Context context;

    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();

        context = Context.getInstance();
        context.updateApplicationContext(getApplicationContext());
        applyUserLanguagePreference();
        cleanUpSyncState();
    }

    private void cleanUpSyncState() {
        DrishtiSyncScheduler.stop(getApplicationContext());
        context.allSettings().saveIsSyncInProgress(false);
    }

    @Override
    public void onTerminate() {
        logInfo("Application is terminating. Stopping Dristhi Sync scheduler and resetting isSyncInProgress setting.");
        cleanUpSyncState();
    }

    private void applyUserLanguagePreference() {
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = context.allSettings().fetchLanguagePreference();
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