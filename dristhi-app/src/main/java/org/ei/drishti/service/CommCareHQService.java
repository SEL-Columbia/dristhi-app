package org.ei.drishti.service;

public class CommCareHQService {
    private final HTTPAgent agent;
    private final String baseURL;
    private final String domain;

    public CommCareHQService(HTTPAgent agent, String baseURL, String domain) {
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
