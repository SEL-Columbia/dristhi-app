package org.ei.drishti.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import org.ei.drishti.R;

public class SettingsActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}