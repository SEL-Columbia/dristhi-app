package org.ei.telemedicine.repository;

import android.content.SharedPreferences;

import static org.ei.telemedicine.AllConstants.ANC_REGISTERS_KEY;
import static org.ei.telemedicine.AllConstants.CHILD_REGISTERS_KEY;
import static org.ei.telemedicine.AllConstants.DEFAULT_LOCALE;
import static org.ei.telemedicine.AllConstants.EC_REGISTERS_KEY;
import static org.ei.telemedicine.AllConstants.FP_REGISTERS_KEY;
import static org.ei.telemedicine.AllConstants.IS_SYNC_IN_PROGRESS_PREFERENCE_KEY;
import static org.ei.telemedicine.AllConstants.LANGUAGE_PREFERENCE_KEY;
import static org.ei.telemedicine.AllConstants.PNC_REGISTERS_KEY;

public class AllSharedPreferences {

    public static final String ANM_IDENTIFIER_PREFERENCE_KEY = "anmIdentifier";
    public static final String IS_FIRST_LOGIN = "isFirstLogin";
    public static final String USER_ROLE_PREFERENCE_KEY = "userRole";
    public static final String FORM_NAME_KEY = "formName";
    public static final String VILLAGE_NAME_KEY = "villageName";
    public static final String ENTITY_KEY = "entityId";
    public static final String PASSWORD = "password";
    public static final String SCREEN = "screen";
    private SharedPreferences preferences;

    public AllSharedPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public boolean clearPreferences() {
        return preferences.edit().clear().commit();
    }

    public void updateUserRole(String userRole) {
        preferences.edit().putString(USER_ROLE_PREFERENCE_KEY, userRole).commit();
    }

    public String getUserRole() {
        return preferences.getString(USER_ROLE_PREFERENCE_KEY, "");
    }

    public void saveFormName(String formName, String entityId) {
        preferences.edit().putString(FORM_NAME_KEY, formName).commit();
        preferences.edit().putString(ENTITY_KEY, entityId).commit();
    }

    public void saveVillageName(String villageName) {
        preferences.edit().putString(VILLAGE_NAME_KEY, villageName).commit();
    }

    public String getVillageName() {
        return preferences.getString(VILLAGE_NAME_KEY, "");
    }

    public String getFormName() {
        return preferences.getString(FORM_NAME_KEY, "");
    }

    public String getEntityKey() {
        return preferences.getString(ENTITY_KEY, "");
    }

    public void updateANMUserName(String userName) {
        preferences.edit().putString(ANM_IDENTIFIER_PREFERENCE_KEY, userName).commit();
    }

    public void updateIsFirstLogin(boolean isFirstLogin) {
        preferences.edit().putBoolean(IS_FIRST_LOGIN, isFirstLogin).commit();
    }

    public boolean fetchIsFirstLogin() {
        return preferences.getBoolean(IS_FIRST_LOGIN, false);
    }

    public void savePwd(String password) {
        preferences.edit().putString(PASSWORD, password).commit();
    }

    public void saveCurrent(String currentScreen) {
        preferences.edit().putString(SCREEN, currentScreen).commit();
    }

    public String getScreen() {
        return preferences.getString(SCREEN, "");
    }

    public String getPwd() {
        return preferences.getString(PASSWORD, "");
    }

    public String fetchRegisteredANM() {
        return preferences.getString(ANM_IDENTIFIER_PREFERENCE_KEY, "").trim();
    }

    public String fetchLanguagePreference() {
        return preferences.getString(LANGUAGE_PREFERENCE_KEY, DEFAULT_LOCALE).trim();
    }

    public void saveLanguagePreference(String languagePreference) {
        preferences.edit().putString(LANGUAGE_PREFERENCE_KEY, languagePreference).commit();
    }

    public Boolean fetchIsSyncInProgress() {
        return preferences.getBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, false);
    }


    public void saveIsSyncInProgress(Boolean isSyncInProgress) {
        preferences.edit().putBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, isSyncInProgress).commit();
    }

    public Boolean getIsSyncInProgress() {
        return preferences.getBoolean(IS_SYNC_IN_PROGRESS_PREFERENCE_KEY, false);
    }


    public void saveECRegisterState(Boolean state) {
        preferences.edit().putBoolean(EC_REGISTERS_KEY, state).commit();
    }

    public void saveFPRegisterState(Boolean state) {
        preferences.edit().putBoolean(FP_REGISTERS_KEY, state).commit();
    }

    public void saveANCRegisterState(Boolean state) {
        preferences.edit().putBoolean(ANC_REGISTERS_KEY, state).commit();
    }

    public void savePNCRegisterState(Boolean state) {
        preferences.edit().putBoolean(PNC_REGISTERS_KEY, state).commit();
    }

    public void saveCHILDRegisterState(Boolean state) {
        preferences.edit().putBoolean(CHILD_REGISTERS_KEY, state).commit();
    }

    public Boolean registerState(String registername) {
        return preferences.getBoolean(registername, true);
    }
}

