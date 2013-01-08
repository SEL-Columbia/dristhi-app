package org.ei.drishti.service;

import org.ei.drishti.domain.LoginResponse;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.Repository;
import org.ei.drishti.util.Session;

import static org.ei.drishti.event.Event.ON_LOGOUT;

public class UserService {
    private CommCareHQService commCareService;
    private final Repository repository;
    private final AllSettings allSettings;
    private Session session;

    public UserService(CommCareHQService commCareService, Repository repository, AllSettings allSettings, Session session) {
        this.commCareService = commCareService;
        this.repository = repository;
        this.allSettings = allSettings;
        this.session = session;
    }

    public boolean isValidLocalLogin(String userName, String password) {
        return allSettings.fetchRegisteredANM().equals(userName) && repository.canUseThisPassword(password);
    }

    public LoginResponse isValidRemoteLogin(String userName, String password) {
        return commCareService.isValidUser(userName, password);
    }

    public void loginWith(String userName, String password) {
        setupContextForLogin(userName, password);
        allSettings.registerANM(userName);
    }

    public boolean hasARegisteredUser() {
        return !allSettings.fetchRegisteredANM().equals("");
    }

    public void logout() {
        logoutSession();
        allSettings.registerANM("");
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

}
