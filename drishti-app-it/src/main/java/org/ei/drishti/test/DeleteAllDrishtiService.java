package org.ei.drishti.test;

import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.service.DrishtiService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeleteAllDrishtiService extends DrishtiService {
    public DeleteAllDrishtiService() {
        super(null, null);
    }

    @Override
    public Response<List<AlertAction>> fetchNewAlertActions(String anmIdentifier, String previouslyFetchedIndex) {
        AlertAction deleteXAction = new AlertAction("Case X", "deleteAll", new HashMap<String, String>(), "123456");
        AlertAction deleteYAction = new AlertAction("Case Y", "deleteAll", new HashMap<String, String>(), "123456");

        return new Response<List<AlertAction>>(ResponseStatus.success, Arrays.asList(deleteXAction, deleteYAction));
    }
}
