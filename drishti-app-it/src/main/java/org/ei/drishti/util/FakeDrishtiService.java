package org.ei.drishti.util;

import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.dto.Action;
import org.ei.drishti.service.DrishtiService;
import org.joda.time.DateTime;

import java.util.*;

import static org.ei.drishti.dto.ActionData.createAlert;
import static org.ei.drishti.dto.ActionData.createEligibleCouple;

public class FakeDrishtiService extends DrishtiService {
    private List<Expectation> expectations;
    private Response<List<Action>> defaultActions;

    public FakeDrishtiService(String defaultSuffix) {
        super(null, null);
        setSuffix(defaultSuffix);
        this.expectations = new ArrayList<Expectation>();
    }

    @Override
    public Response<List<Action>> fetchNewActions(String anmIdentifier, String previouslyFetchedIndex) {
        for (Expectation expectation : expectations) {
            if (expectation.matches(anmIdentifier, previouslyFetchedIndex)) {
                return expectation.alertActions();
            }
        }
        return defaultActions;
    }

    public void setSuffix(String suffix) {
        this.defaultActions = actionsFor(suffix);
    }

    public void changeDefaultActions(Response<List<Action>> alertsToProvide) {
        this.defaultActions = alertsToProvide;
    }

    public Response<List<Action>> actionsFor(String suffix) {
        Action deleteXAction = new Action("Case X", "alert", "deleteAllAlerts", new HashMap<String, String>(), "123456");
        Action deleteYAction = new Action("Case Y", "alert", "deleteAllAlerts", new HashMap<String, String>(), "123456");
        Action firstAction = new Action("Case X", "alert", "createAlert", dataForCreateAction("Theresa 1 " + suffix, "Bherya 1", "Sub Center", "PHC X", "Thaayi 1 " + suffix, "BCG", "due", "2012-01-01"), "123456");
        Action secondAction = new Action("Case Y", "alert", "createAlert", dataForCreateAction("Theresa 2 " + suffix, "Bherya 2", "Sub Center", "PHC X", "Thaayi 2 " + suffix, "OPV 1", "due", "2100-04-09"), "123456");
        Action firstCreateEC = new Action("Case A" + suffix, "eligibleCouple", "createEC", dataForCreateEC("Wife 1 " + suffix, "Husband 1", "EC 1" + suffix, "SubCenter 1", "Village 1", "PHC X"), "123456");
        Action secondCreateEC = new Action("Case B" + suffix, "eligibleCouple", "createEC", dataForCreateEC("Wife 2 " + suffix, "Husband 2", "EC 2" + suffix, "SubCenter 2", "Village 2", "PHC X"), "123456");

        return new Response<List<Action>>(ResponseStatus.success, new ArrayList<Action>(Arrays.asList(deleteXAction, deleteYAction, firstAction, secondAction, firstCreateEC, secondCreateEC)));
    }

    public static Map<String, String> dataForCreateAction(String beneficiaryName, String village, String subCenter, String phc, String thaayiCardNumber, String visitCode, String lateness, String dueDate) {
        return createAlert(beneficiaryName, village, subCenter, phc, thaayiCardNumber, visitCode, lateness, new DateTime(dueDate)).data();
    }

    private Map<String, String> dataForCreateEC(String wifeName, String husbandName, String ecNumber, String village, String subCenter, String phc) {
        return createEligibleCouple(wifeName, husbandName, ecNumber, village, subCenter, phc).data();
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
