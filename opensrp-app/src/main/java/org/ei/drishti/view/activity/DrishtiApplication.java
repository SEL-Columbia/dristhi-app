package org.ei.drishti.view.activity;

import android.app.Application;
import android.content.res.Configuration;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.ei.drishti.Context;
import org.ei.drishti.sync.DrishtiSyncScheduler;

import java.util.Locale;

import static org.ei.drishti.util.Log.logInfo;

@ReportsCrashes(
        formKey = "",
        formUri = "https://drishtiapp.cloudant.com/acra-drishtiapp/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.POST,
        formUriBasicAuthLogin = "sompleakereepeavoldiftle",
        formUriBasicAuthPassword = "ecUMrMeTKf1X1ODxHqo3b43W",
        mode = ReportingInteractionMode.SILENT
)
public class DrishtiApplication extends Application {
    private Locale locale = null;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);

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
        logInfo("Application is terminating. Stopping Dristhi Sync scheduler and resetting isSyncInProgress setting.");
        cleanUpSyncState();
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