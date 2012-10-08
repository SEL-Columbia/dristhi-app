package org.ei.drishti.view.activity;

import android.app.Application;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "dFdKSGVjdDJ4ZThpRHQ0bm50VkdDeGc6MQ")
public class DrishtiApplication extends Application {
    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
    }
}
