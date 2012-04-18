package org.ei.drishti.repository;

import android.content.SharedPreferences;

public class AllSettings {
    private SharedPreferences preferences;
    private SettingsRepository settingsRepository;

    public AllSettings(SharedPreferences preferences, SettingsRepository settingsRepository) {
        this.preferences = preferences;
        this.settingsRepository = settingsRepository;
    }

    public String fetchANMIdentifier() {
        return preferences.getString("anmIdentifier", "ANM").trim();
    }

    public void savePreviousFetchIndex(String value) {
        settingsRepository.updateSetting("previousFetchIndex", value);
    }

    public String fetchPreviousFetchIndex() {
        return settingsRepository.querySetting("previousFetchIndex", "0");
    }
}
