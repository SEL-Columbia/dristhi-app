package org.ei.opensrp.test.vaksinator;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
  * Created by Dimas on 9/22/2015.
  */
public class ErrorReportingFacade {

    public static void initErrorHandler(Context context) {
        Fabric.with(context, new Crashlytics());
    }
        public static void setUsername(String fullName, String userName) {
        Crashlytics.setUserIdentifier(userName);
        Crashlytics.setUserName(userName);
    }
    
}