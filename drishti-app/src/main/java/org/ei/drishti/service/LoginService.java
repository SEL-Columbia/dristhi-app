package org.ei.drishti.service;

import org.ei.drishti.Context;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.Repository;

import static org.ei.drishti.event.Event.ON_LOGOUT;

public class LoginService {
    private CommCareService commCareService;
    private final Repository repository;
    private final AllSettings allSettings;
    private final AllAlerts allAlerts;
    private final AllEligibleCouples allEligibleCouples;

    public LoginService(CommCareService commCareService, Repository repository, AllSettings allSettings, AllAlerts allAlerts, AllEligibleCouples allEligibleCouples) {
        this.commCareService = commCareService;
        this.repository = repository;
        this.allSettings = allSettings;
        this.allAlerts = allAlerts;
        this.allEligibleCouples = allEligibleCouples;
    }

    public boolean isValidLocalLogin(String userName, String password) {
        return allSettings.fetchRegisteredANM().equals(userName) && repository.canUseThisPassword(password);
    }

    public boolean isValidRemoteLogin(String userName, String password) {
        return commCareService.isValidUser(userName, password);
    }

    public void loginWith(String userName, String password) {
        allSettings.registerANM(userName);
        Context.getInstance().setPassword(password);
    }

    public boolean hasARegisteredUser() {
        return !allSettings.fetchRegisteredANM().equals("");
    }

    public void logout() {
        ON_LOGOUT.notifyListeners(true);
        allSettings.registerANM("");
        allSettings.savePreviousFetchIndex("0");
        repository.deleteRepository();
    }
}
