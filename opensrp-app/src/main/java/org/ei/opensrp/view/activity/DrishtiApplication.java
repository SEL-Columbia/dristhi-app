package org.ei.opensrp.view.activity;

import android.app.Application;
import android.util.Log;

import org.ei.opensrp.Context;

import java.util.Locale;


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