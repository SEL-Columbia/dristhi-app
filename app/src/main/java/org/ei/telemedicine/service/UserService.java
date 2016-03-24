package org.ei.telemedicine.service;

import org.ei.telemedicine.DristhiConfiguration;
import org.ei.telemedicine.domain.LoginResponse;
import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.repository.AllSharedPreferences;
import org.ei.telemedicine.repository.Repository;
import org.ei.telemedicine.sync.SaveANMLocationTask;
import org.ei.telemedicine.util.Log;
import org.ei.telemedicine.util.Session;

import static org.ei.telemedicine.AllConstants.LOGIN_URL_PATH;
import static org.ei.telemedicine.event.Event.ON_LOGOUT;

public class UserService {
    private final Repository repository;
    private final AllSettings allSettings;
    private final AllSharedPreferences allSharedPreferences;
    private HTTPAgent httpAgent;
    private Session session;
    private DristhiConfiguration configuration;
    private SaveANMLocationTask saveANMLocationTask;

    public UserService(Repository repository, AllSettings allSettings, AllSharedPreferences allSharedPreferences, HTTPAgent httpAgent, Session session,
                       DristhiConfiguration configuration, SaveANMLocationTask saveANMLocationTask) {
        this.repository = repository;
        this.allSettings = allSettings;
        this.allSharedPreferences = allSharedPreferences;
        this.httpAgent = httpAgent;
        this.session = session;
        this.configuration = configuration;
        this.saveANMLocationTask = saveANMLocationTask;
    }


    public boolean isValidLocalLogin(String userName, String password) {
        return allSharedPreferences.fetchRegisteredANM().equals(userName) && repository.canUseThisPassword(password);
    }

    public String getUserRole() {
        return allSharedPreferences.getUserRole();
    }

    public void setFormDetails(String formName, String entityId) {
        allSharedPreferences.saveFormName(formName, entityId);
    }

    public String getFormName() {
        return allSharedPreferences.getFormName();
    }

    public String getEntityId() {
        return allSharedPreferences.getEntityKey();
    }

    public LoginResponse isValidRemoteLogin(String userName, String password) {
        String requestURL = configuration.dristhiDjangoBaseURL() + LOGIN_URL_PATH + userName + "&pwd=" + password;
        android.util.Log.e("Login url", requestURL);
        return httpAgent.urlCanBeAccessWithGivenCredentials(requestURL, userName, password);
    }
//
//    public LoginResponse gettingVillagesWithRemoteLogin(String userName, String password) {
//        String requestURL = configuration.dristhiBaseURL() + VILLAGES_USER_URL_PATH + userName;
//        return httpAgent.urlCanBeAccessWithGivenCredentials(requestURL, userName, password);
//    }

    private void loginWith(String userName, String password) {
        setupContextForLogin(userName, password);
        allSettings.registerANM(userName, password);
    }

    private void loginWithUserRole(String userName, String password, String userRole) {
        setupContextForLogin(userName, password);
        allSettings.registerANMWithUserRole(userName, password, userRole);
    }

    public void localLogin(String userName, String password) {
        loginWith(userName, password);
    }

    public void remoteLogin(String userName, String password, String userRole, String anmLocation, String anmDrugs, String anmConfig, String anmCountryCode, String formFields) {
        loginWithUserRole(userName, password, userRole);
        saveANMLocationTask.save(anmLocation, anmDrugs, anmConfig, anmCountryCode, formFields);
    }

    public boolean hasARegisteredUser() {
        return !allSharedPreferences.fetchRegisteredANM().equals("");
    }

    public void logout() {
        logoutSession();
        allSettings.clearPreferences();
        allSettings.registerANM("", "");
        allSettings.savePreviousFetchIndex("0");
        repository.deleteRepository();
    }

    public void logoutSession() {
        session().expire();
        ON_LOGOUT.notifyListeners(true);
    }

    public boolean hasSessionExpired() {
        return session().hasExpired();
    }

    protected void setupContextForLogin(String userName, String password) {
        session().start(session().lengthInMilliseconds());
        session().setPassword(password);
    }

    protected Session session() {
        return session;
    }

    public String gettingFromRemoteURL(String requestURL) {
        return httpAgent.callingURL(requestURL);
    }

}
