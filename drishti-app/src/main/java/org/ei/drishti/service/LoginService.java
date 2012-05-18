package org.ei.drishti.service;

import org.ei.drishti.Context;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.Repository;

public class LoginService {
    private CommCareService commCareService;
    private final Repository repository;
    private final AllSettings settings;

    public LoginService(CommCareService commCareService, Repository repository, AllSettings settings) {
        this.commCareService = commCareService;
        this.repository = repository;
        this.settings = settings;
    }

    public boolean isValidLocalLogin(String userName, String password) {
        return settings.fetchRegisteredANM().equals(userName) && repository.canUseThisPassword(password);
    }

    public boolean isValidRemoteLogin(String userName, String password) {
        return commCareService.isValidUser(userName, password);
    }

    public void loginWith(String userName, String password) {
        settings.registerANM(userName);
        Context.getInstance().setPassword(password);
    }

    public boolean hasARegisteredUser() {
        return !settings.fetchRegisteredANM().equals("");
    }
}
