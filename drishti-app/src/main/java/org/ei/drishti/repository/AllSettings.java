package org.ei.drishti.repository;

public class AllSettings {
    private SettingsRepository settingsRepository;

    public AllSettings(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void saveANMIdentifier(String value) {
        settingsRepository.updateSetting("anm", value);
    }

    public String fetchANMIdentifier() {
        return settingsRepository.querySetting("anm", "ANM");
    }

    public void savePreviousFetchIndex(String value) {
        settingsRepository.updateSetting("previousFetchIndex", value);
    }

    public String fetchPreviousFetchIndex() {
        return settingsRepository.querySetting("previousFetchIndex", "0");
    }
}
