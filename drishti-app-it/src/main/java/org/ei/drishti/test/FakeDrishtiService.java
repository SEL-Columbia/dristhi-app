package org.ei.drishti.test;

import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.domain.Response;
import org.ei.drishti.service.DrishtiService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeDrishtiService extends DrishtiService {
    private String suffix;

    public FakeDrishtiService() {
        super(null, null);
    }

    @Override
    public Response<List<AlertAction>> fetchNewAlertActions(String anmIdentifier, String previouslyFetchedIndex) {
        AlertAction deleteXAction = new AlertAction("Case X", "deleteAll", new HashMap<String, String>(), "123456");
        AlertAction deleteYAction = new AlertAction("Case Y", "deleteAll", new HashMap<String, String>(), "123456");
        AlertAction firstAction = new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa 1 " + suffix, "ANC 1", "Thaayi 1 " + suffix, "2012-04-09"), "123456");
        AlertAction secondAction = new AlertAction("Case Y", "create", dataForCreateAction("due", "Theresa 2 " + suffix, "ANC 1", "Thaayi 2 " + suffix, "2012-04-09"), "123456");

        return new Response<List<AlertAction>>(Response.ResponseStatus.success, Arrays.asList(deleteXAction, deleteYAction, firstAction, secondAction));
    }

    private Map<String, String> dataForCreateAction(String lateness, String motherName, String visitCode, String thaayiCardNumber, String dueDate) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("motherName", motherName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("dueDate", dueDate);
        return map;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
