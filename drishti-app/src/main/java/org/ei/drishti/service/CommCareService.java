package org.ei.drishti.service;

public class CommCareService {
    private final HTTPAgent agent;

    public CommCareService(HTTPAgent agent) {
        this.agent = agent;
    }

    public boolean isValidUser(String userName, String password) {
        return false;
    }
}
