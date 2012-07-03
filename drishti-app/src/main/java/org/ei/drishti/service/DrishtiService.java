package org.ei.drishti.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.domain.Response;
import org.ei.drishti.dto.Action;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DrishtiService {
    private HTTPAgent agent = null;
    private String drishtiBaseURL;

    public DrishtiService(HTTPAgent agent, String drishtiBaseURL) {
        this.agent = agent;
        this.drishtiBaseURL = drishtiBaseURL;
    }

    public Response<List<Action>> fetchNewActions(String anmIdentifier, String previouslyFetchedIndex) {
        String anmID = URLEncoder.encode(anmIdentifier);
        Response<String> response = agent.fetch(drishtiBaseURL + "/actions?anmIdentifier=" + anmID + "&timeStamp=" + previouslyFetchedIndex);
        Type collectionType = new TypeToken<List<Action>>() { }.getType();
        List<Action> actions = null;
        try {
            actions = new Gson().fromJson(response.payload(), collectionType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Response<List<Action>>(response.status(), actions == null ? new ArrayList<Action>() : actions);
    }

}
