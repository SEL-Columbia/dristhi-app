package org.ei.drishti.util;

import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.dto.Action;
import org.ei.drishti.dto.AlertPriority;
import org.ei.drishti.dto.BeneficiaryType;
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

    public Response<List<Action>> actionsFor(String suffix) {
        Action deleteXAction = new Action("Case X", "alert", "deleteAllAlerts", new HashMap<String, String>(), "123456", true, new HashMap<String, String>());
        Action deleteYAction = new Action("Case Y", "alert", "deleteAllAlerts", new HashMap<String, String>(), "123456", true, new HashMap<String, String>());
        Action firstAction = new Action("Case X", "alert", "createAlert", dataForCreateAction("BCG", "2012-01-01"), "123456", true, new HashMap<String, String>());
        Action secondAction = new Action("Case Y", "alert", "createAlert", dataForCreateAction("OPV 1", "2100-04-09"), "123456", true, new HashMap<String, String>());
        Action firstCreateEC = new Action("Case A" + suffix, "eligibleCouple", "createEC", dataForCreateEC("Wife 1 " + suffix, "Husband 1", "EC 1" + suffix, "SubCenter 1", "Village 1", "PHC X"), "123456", true, new HashMap<String, String>());
        Action secondCreateEC = new Action("Case B" + suffix, "eligibleCouple", "createEC", dataForCreateEC("Wife 2 " + suffix, "Husband 2", "EC 2" + suffix, "SubCenter 2", "Village 2", "PHC X"), "123456", true, new HashMap<String, String>());

        return new Response<List<Action>>(ResponseStatus.success, new ArrayList<Action>(Arrays.asList(deleteXAction, deleteYAction, firstAction, secondAction, firstCreateEC, secondCreateEC)));
    }

    private static Map<String, String> dataForCreateAction(String visitCode, String dueDate) {
        return createAlert(BeneficiaryType.mother, visitCode, AlertPriority.normal, new DateTime(dueDate), new DateTime("2012-01-11")).data();
    }

    private Map<String, String> dataForCreateEC(String wifeName, String husbandName, String ecNumber, String village, String subCenter, String phc) {
        return createEligibleCouple(wifeName, husbandName, ecNumber, village, subCenter, phc, new HashMap<String, String>()).data();
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
