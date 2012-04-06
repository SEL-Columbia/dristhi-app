package org.ei.drishti.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.agent.HTTPAgent;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.AlertAction;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DrishtiService {
    private HTTPAgent agent = null;
    private String drishtiBaseURL;

    public DrishtiService(HTTPAgent agent, String drishtiBaseURL) {
        this.agent = agent;
        this.drishtiBaseURL = drishtiBaseURL;
    }

    public Response<List<AlertAction>> fetchNewAlertActions(String anmIdentifier, String previouslyFetchedIndex) throws Exception {
        Response<String> response = agent.fetch(drishtiBaseURL + "/alerts?anmIdentifier=" + anmIdentifier + "&timeStamp=" + previouslyFetchedIndex);
        Type collectionType = new TypeToken<List<AlertAction>>(){}.getType();
        List<AlertAction> actions = new Gson().fromJson(response.payload(), collectionType);

        return new Response<List<AlertAction>>(response.status(), actions == null ? new ArrayList<AlertAction>() : actions);
    }

}
