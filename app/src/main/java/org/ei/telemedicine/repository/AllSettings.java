package org.ei.telemedicine.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllSettings {
    public static final String APPLIED_VILLAGE_FILTER_SETTING_KEY = "appliedVillageFilter";
    public static final String PREVIOUS_FETCH_INDEX_SETTING_KEY = "previousFetchIndex";
    public static final String PREVIOUS_FORM_SYNC_INDEX_SETTING_KEY = "previousFormSyncIndex";
    private static final String ANM_PASSWORD_PREFERENCE_KEY = "anmPassword";
    private static final String ANM_LOCATION = "anmLocation";
    private static final String ANM_DRUGS = "anmDrugs";
    private static final String ANM_CONFIG = "anmConfiguration";
    private static final String ANM_COUNTRY_CODE = "anmCountryCode";
    private static final String FORM_FIELDS = "formFieldLabels";
    private AllSharedPreferences preferences;
    private SettingsRepository settingsRepository;

    public AllSettings(AllSharedPreferences preferences, SettingsRepository settingsRepository) {
        this.preferences = preferences;
        this.settingsRepository = settingsRepository;
    }

    public void clearPreferences() {
        preferences.clearPreferences();
    }

    public void registerANM(String userName, String password) {
        preferences.updateANMUserName(userName);
        settingsRepository.updateSetting(ANM_PASSWORD_PREFERENCE_KEY, password);
    }

    public void registerANMWithUserRole(String userName, String password, String userRole) {
        preferences.updateANMUserName(userName);
        preferences.updateUserRole(userRole);
        settingsRepository.updateSetting(ANM_PASSWORD_PREFERENCE_KEY, password);
    }

    public void savePreviousFetchIndex(String value) {
        settingsRepository.updateSetting(PREVIOUS_FETCH_INDEX_SETTING_KEY, value);
    }

    public String fetchPreviousFetchIndex() {
        return settingsRepository.querySetting(PREVIOUS_FETCH_INDEX_SETTING_KEY, "0");
    }

    public void saveAppliedVillageFilter(String village) {
        settingsRepository.updateSetting(APPLIED_VILLAGE_FILTER_SETTING_KEY, village);
    }

    public String appliedVillageFilter(String defaultFilterValue) {
        return settingsRepository.querySetting(APPLIED_VILLAGE_FILTER_SETTING_KEY, defaultFilterValue);
    }

    public String fetchANMPassword() {
        return settingsRepository.querySetting(ANM_PASSWORD_PREFERENCE_KEY, "");
    }

    public String fetchPreviousFormSyncIndex() {
        return settingsRepository.querySetting(PREVIOUS_FORM_SYNC_INDEX_SETTING_KEY, "0");
    }

    public void savePreviousFormSyncIndex(String value) {
        settingsRepository.updateSetting(PREVIOUS_FORM_SYNC_INDEX_SETTING_KEY, value);
    }

    public void saveANMLocation(String anmLocation) {
        settingsRepository.updateSetting(ANM_LOCATION, anmLocation);
    }

    public void saveANMInfo(String anmLocation, String anmDrugs, String anmConfig, String countryCode, String formFields) {
        settingsRepository.updateSetting(ANM_LOCATION, anmLocation);
        settingsRepository.updateSetting(ANM_DRUGS, anmDrugs);
        settingsRepository.updateSetting(ANM_CONFIG, anmConfig);
        settingsRepository.updateSetting(ANM_COUNTRY_CODE, countryCode);
        settingsRepository.updateSetting(FORM_FIELDS, formFields);
    }


    public String fetchANMLocation() {
        String setting = settingsRepository.querySetting(ANM_LOCATION, "");
        return setting;
    }

    public String fetchDrugs() {
        return settingsRepository.querySetting(ANM_DRUGS, "");
    }

    public String fetchCountryCode() {
        return settingsRepository.querySetting(ANM_COUNTRY_CODE, "");
    }

    public String fetchFieldLabels(String regType) {
        String fieldsData = settingsRepository.querySetting(FORM_FIELDS, "");
        if (fieldsData != null && !fieldsData.equals("")) {
            try {
                JSONArray fieldsArray = new JSONArray(fieldsData);
                for (int i = 0; i < fieldsArray.length(); i++) {
                    JSONObject fieldsJson = fieldsArray.getJSONObject(i);
                    if (fieldsJson.has(regType)) {
                        return fieldsJson.getJSONArray(regType).toString();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String fetchANMConfiguration(String configKey) {
        String config = settingsRepository.querySetting(ANM_CONFIG, "");
        if (config != null && !config.equals("")) {
            try {
                JSONObject configJson = new JSONObject(config);
                return configJson.has(configKey) && configJson.getString(configKey) != null ? configJson.getString(configKey) : "0";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "0";
    }

    public ArrayList<String> getVillages() {
        final ArrayList<String> villagesList = new ArrayList<String>();
        String fetchLocation = fetchANMLocation();
        if (fetchLocation != null) {
            try {
                JSONObject locationJson = new JSONObject(fetchLocation);
                JSONArray villageArray = locationJson.has("villages") ? locationJson.getJSONArray("villages") : new JSONArray();
                for (int i = 0; i < villageArray.length(); i++) {
                    villagesList.add(villageArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return villagesList;
    }
}
