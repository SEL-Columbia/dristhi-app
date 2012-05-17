package org.ei.drishti.service;

public class LoginService {
    public boolean isValidLocalLogin(String userName, String password) {
        return false;
    }

    public boolean isValidRemoteLogin(String userName, String password) {
        return true;
    }

    public void loginWith(String userName, String password) {
    }
}
