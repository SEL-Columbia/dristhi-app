package org.ei.drishti.service;

public class CommCareService {
    private final HTTPAgent agent;
    private final String baseURL;
    private final String domain;

    public CommCareService(HTTPAgent agent, String baseURL, String domain) {
        this.agent = agent;
        this.baseURL = baseURL;
        this.domain = domain;
    }

    public boolean isValidUser(String userName, String password) {
        String url = baseURL + "/a/" + domain + "/phone/restore";
        String userNameWithDomain = userName + "@" + domain + ".commcarehq.org";

        return agent.urlCanBeAccessWithGivenCredentials(url, userNameWithDomain, password);
    }
}
