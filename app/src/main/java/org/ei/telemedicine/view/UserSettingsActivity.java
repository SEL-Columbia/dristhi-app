package org.ei.telemedicine.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import org.ei.telemedicine.R;

public class UserSettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings);

    }
}
