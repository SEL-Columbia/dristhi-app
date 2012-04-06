package org.ei.drishti;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DrishtiAgent {
    private HTTPAgent agent = null;
    private String drishtiBaseURL;

    public DrishtiAgent(HTTPAgent agent, String drishtiBaseURL) {
        this.agent = agent;
        this.drishtiBaseURL = drishtiBaseURL;
    }

    public List<AlertAction> fetchNewAlerts(String anmIdentifier, String previouslyFetchedIndex) throws Exception {
        String content = agent.fetch(drishtiBaseURL + "/alerts?anmIdentifier=" + anmIdentifier + "&timeStamp=" + previouslyFetchedIndex);
        Type collectionType = new TypeToken<List<AlertAction>>(){}.getType();
        List<AlertAction> actions = new Gson().fromJson(content, collectionType);

        return actions == null ? new ArrayList<AlertAction>() : actions;
    }
}
