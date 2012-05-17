package org.ei.drishti.service;

public class LoginService {
    private CommCareService commCareService;

    public LoginService(CommCareService commCareService) {
        this.commCareService = commCareService;
    }

    public boolean isValidLocalLogin(String userName, String password) {
        return false;
    }

    public boolean isValidRemoteLogin(String userName, String password) {
        return commCareService.isValidUser(userName, password);
    }

    public void loginWith(String userName, String password) {
    }
}
