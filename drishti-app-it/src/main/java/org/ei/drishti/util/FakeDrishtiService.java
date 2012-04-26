package org.ei.drishti.util;

import org.ei.drishti.domain.AlertAction;
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
    public Response<List<AlertAction>> fetchNewAlertActions(String anmIdentifier, String previouslyFetchedIndex) {
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

    public void expect(String anmId, String previousIndex, Response<List<AlertAction>> suffixOfAlertsToReturn) {
        expectations.add(new Expectation(anmId, previousIndex, suffixOfAlertsToReturn));
    }

    private Response<List<AlertAction>> actionsFor(String suffix) {
        AlertAction deleteXAction = new AlertAction("Case X", "deleteAll", new HashMap<String, String>(), "123456");
        AlertAction deleteYAction = new AlertAction("Case Y", "deleteAll", new HashMap<String, String>(), "123456");
        AlertAction firstAction = new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa 1 " + suffix, "BCG", "Thaayi 1 " + suffix, "2012-01-01"), "123456");
        AlertAction secondAction = new AlertAction("Case Y", "create", dataForCreateAction("due", "Theresa 2 " + suffix, "OPV 1", "Thaayi 2 " + suffix, "2100-04-09"), "123456");

        return new Response<List<AlertAction>>(ResponseStatus.success, Arrays.asList(deleteXAction, deleteYAction, firstAction, secondAction));
    }

    private class Expectation {
        private String anmId;
        private String previousIndex;
        private Response<List<AlertAction>> alertsToProvide;

        public Expectation(String anmId, String previousIndex, Response<List<AlertAction>> alertsToProvide) {
            this.anmId = anmId;
            this.previousIndex = previousIndex;
            this.alertsToProvide = alertsToProvide;
        }

        public Response<List<AlertAction>> alertActions() {
            return alertsToProvide;
        }

        public boolean matches(String anmIdentifier, String previouslyFetchedIndex) {
            return anmId.equals(anmIdentifier) && previousIndex.equals(previouslyFetchedIndex);
        }
    }
}
