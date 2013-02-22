package org.ei.drishti.view.activity;

import android.app.Application;
import android.content.res.Configuration;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.ei.drishti.Context;

import java.util.Locale;

@ReportsCrashes(formKey = "dFdKSGVjdDJ4ZThpRHQ0bm50VkdDeGc6MQ")
public class DrishtiApplication extends Application {
    private Locale locale = null;

    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();

        applyUserLanguagePreference();
    }

    private void applyUserLanguagePreference() {
        Context context = Context.getInstance();
        context.updateApplicationContext(getApplicationContext());

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
