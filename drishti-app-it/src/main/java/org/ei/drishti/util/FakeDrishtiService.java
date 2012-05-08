package org.ei.drishti.util;

import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.service.DrishtiService;

import java.util.*;

public class FakeDrishtiService extends DrishtiService {
    private String defaultSuffix;
    private List<Expectation> expectations;

    public FakeDrishtiService(String defaultSuffix) {
        super(null, null);
        this.defaultSuffix = defaultSuffix;
        this.expectations = new ArrayList<Expectation>();
    }

    @Override
    public Response<List<Action>> fetchNewAlertActions(String anmIdentifier, String previouslyFetchedIndex) {
        for (Expectation expectation : expectations) {
            if (expectation.matches(anmIdentifier, previouslyFetchedIndex)) {
                return expectation.alertActions();
            }
        }
        return actionsFor(defaultSuffix);
    }

    public static Map<String, String> dataForCreateAction(String lateness, String beneficiaryName, String visitCode, String thaayiCardNumber, String dueDate) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("beneficiaryName", beneficiaryName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("dueDate", dueDate);
        return map;
    }

    public void setSuffix(String suffix) {
        this.defaultSuffix = suffix;
    }

    public void expect(String anmId, String previousIndex, String suffixOfAlertsToReturn) {
        expectations.add(new Expectation(anmId, previousIndex, actionsFor(suffixOfAlertsToReturn)));
    }

    public void expect(String anmId, String previousIndex, Response<List<Action>> suffixOfAlertsToReturn) {
        expectations.add(new Expectation(anmId, previousIndex, suffixOfAlertsToReturn));
    }

    private Response<List<Action>> actionsFor(String suffix) {
        Action deleteXAction = new Action("Case X", "deleteAllAlerts", new HashMap<String, String>(), "123456");
        Action deleteYAction = new Action("Case Y", "deleteAllAlerts", new HashMap<String, String>(), "123456");
        Action firstAction = new Action("Case X", "createAlert", dataForCreateAction("due", "Theresa 1 " + suffix, "BCG", "Thaayi 1 " + suffix, "2012-01-01"), "123456");
        Action secondAction = new Action("Case Y", "createAlert", dataForCreateAction("due", "Theresa 2 " + suffix, "OPV 1", "Thaayi 2 " + suffix, "2100-04-09"), "123456");
        Action blah = new Action("Case A", "createEC", dataForCreateEC("Wife 1" + suffix, "Husband 1", "EC 1" + suffix), "123456");
        Action boo = new Action("Case B", "createEC", dataForCreateEC("Wife 2" + suffix, "Husband 2", "EC 2" + suffix), "123456");

        return new Response<List<Action>>(ResponseStatus.success, Arrays.asList(deleteXAction, deleteYAction, firstAction, secondAction, blah, boo));
    }

    private Map<String, String> dataForCreateEC(String wifeName, String husbandName, String ecNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("wife", wifeName);
        map.put("husband", husbandName);
        map.put("ecNumber", ecNumber);
        return map;
    }

    private class Expectation {
        private String anmId;
        private String previousIndex;
        private Response<List<Action>> alertsToProvide;

        public Expectation(String anmId, String previousIndex, Response<List<Action>> alertsToProvide) {
            this.anmId = anmId;
            this.previousIndex = previousIndex;
            this.alertsToProvide = alertsToProvide;
        }

        public Response<List<Action>> alertActions() {
            return alertsToProvide;
        }

        public boolean matches(String anmIdentifier, String previouslyFetchedIndex) {
            return anmId.equals(anmIdentifier) && previousIndex.equals(previouslyFetchedIndex);
        }
    }
}
