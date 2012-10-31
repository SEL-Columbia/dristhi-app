package org.ei.drishti.repository;

import android.content.SharedPreferences;

public class AllSettings {
    private SharedPreferences preferences;
    private SettingsRepository settingsRepository;

    public AllSettings(SharedPreferences preferences, SettingsRepository settingsRepository) {
        this.preferences = preferences;
        this.settingsRepository = settingsRepository;
    }

    public void registerANM(String userName) {
        preferences.edit().putString("anmIdentifier", userName).commit();
    }

    public String fetchRegisteredANM() {
        return preferences.getString("anmIdentifier", "").trim();
    }

    public void savePreviousFetchIndex(String value) {
        settingsRepository.updateSetting("previousFetchIndex", value);
    }

    public String fetchPreviousFetchIndex() {
        return settingsRepository.querySetting("previousFetchIndex", "0");
    }

    public void saveAppliedVillageFilter(String village) {
        settingsRepository.updateSetting("appliedVillageFilter", village);
    }
}
