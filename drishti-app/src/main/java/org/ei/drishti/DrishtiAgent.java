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

    public Response<List<AlertAction>> fetchNewAlerts(String anmIdentifier, String previouslyFetchedIndex) throws Exception {
        Response<String> response = agent.fetch(drishtiBaseURL + "/alerts?anmIdentifier=" + anmIdentifier + "&timeStamp=" + previouslyFetchedIndex);
        Type collectionType = new TypeToken<List<AlertAction>>(){}.getType();
        List<AlertAction> actions = new Gson().fromJson(response.payload(), collectionType);

        return new Response<List<AlertAction>>(response.status(), actions == null ? new ArrayList<AlertAction>() : actions);
    }

}
