package org.ei.drishti.service;

import org.ei.drishti.domain.LoginResponse;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.Repository;
import org.ei.drishti.util.Session;

import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.event.Event.ON_LOGOUT;

public class UserService {
    private final Repository repository;
    private final AllSettings allSettings;
    private HTTPAgent httpAgent;
    private Session session;

    public UserService(Repository repository, AllSettings allSettings, HTTPAgent httpAgent, Session session) {
        this.repository = repository;
        this.allSettings = allSettings;
        this.httpAgent = httpAgent;
        this.session = session;
    }

    public boolean isValidLocalLogin(String userName, String password) {
        return allSettings.fetchRegisteredANM().equals(userName) && repository.canUseThisPassword(password);
    }

    public LoginResponse isValidRemoteLogin(String userName, String password) {
        return httpAgent.urlCanBeAccessWithGivenCredentials(DRISHTI_BASE_URL, userName, password);
    }

    public void loginWith(String userName, String password) {
        setupContextForLogin(userName, password);
        allSettings.registerANM(userName, password);
    }

    public boolean hasARegisteredUser() {
        return !allSettings.fetchRegisteredANM().equals("");
    }

    public void logout() {
        logoutSession();
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

    public String switchLanguagePreference() {
        String preferredLocale = allSettings.fetchLanguagePreference();
        if (ENGLISH_LOCALE.equals(preferredLocale)) {
            allSettings.saveLanguagePreference(KANNADA_LOCALE);
            return KANNADA_LANGUAGE;
        } else {
            allSettings.saveLanguagePreference(ENGLISH_LOCALE);
            return ENGLISH_LANGUAGE;
        }
    }
}
