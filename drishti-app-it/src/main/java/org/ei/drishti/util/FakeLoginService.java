package org.ei.drishti.util;

import org.ei.drishti.service.LoginService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class FakeLoginService extends LoginService {
    private boolean shouldSucceedLocalLogin;
    private boolean shouldSucceedRemoteLogin;
    private String expectedUserName = "";
    private String expectedPassword = "";
    private List<String> actualCalls = new ArrayList<String>();
    private boolean hasARegisteredUser;

    public FakeLoginService() {
        super(null, null, null);
    }

    @Override
    public boolean isValidLocalLogin(String userName, String password) {
        assertExpectedCredentials(userName, password);
        actualCalls.add("local");
        return shouldSucceedLocalLogin;
    }

    @Override
    public boolean isValidRemoteLogin(String userName, String password) {
        assertExpectedCredentials(userName, password);
        actualCalls.add("remote");
        return shouldSucceedRemoteLogin;
    }

    @Override
    public void loginWith(String userName, String password) {
        super.setupContextForLogin(userName, password);
        actualCalls.add("login");
        assertExpectedCredentials(userName, password);
    }

    @Override
    public boolean hasARegisteredUser() {
        return hasARegisteredUser;
    }

    @Override
    public void logout() {
        super.logoutSession();
        actualCalls.add("logout");
    }

    @Override
    public void logoutSession() {
        super.logoutSession();
    }

    private void assertExpectedCredentials(String userName, String password) {
        if (!expectedUserName.equals(userName)) {
            throw new RuntimeException("Expected user: " + expectedUserName + ". Actual: " + userName);
        }
        if (!expectedPassword.equals(password)) {
            throw new RuntimeException("Expected user: " + expectedUserName + ". Actual: " + password);
        }
    }

    public void setupFor(String userName, String password, boolean hasARegisteredUser, boolean shouldSucceedLocalLogin, boolean shouldSucceedRemoteLogin) {
        this.expectedUserName = userName;
        this.expectedPassword = password;
        this.hasARegisteredUser = hasARegisteredUser;
        this.shouldSucceedLocalLogin = shouldSucceedLocalLogin;
        this.shouldSucceedRemoteLogin = shouldSucceedRemoteLogin;
    }

    public void assertOrderOfCalls(String... calls) {
        if (!actualCalls.equals(asList(calls))) {
            throw new RuntimeException("Expected calls: " + asList(calls) + ". Actual: " + actualCalls);
        }
    }
}
