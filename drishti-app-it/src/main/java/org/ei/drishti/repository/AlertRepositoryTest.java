package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.ei.drishti.adapter.AlertFilterCriterion.All;
import static org.ei.drishti.adapter.AlertFilterCriterion.ANC;

public class AlertRepositoryTest extends AndroidTestCase {
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        alertRepository = new AlertRepository();
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), alertRepository);
    }

    public void testShouldSaveAnAlert() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        List<Alert> alerts = alertRepository.alertsFor(All.visitCodePrefix());

        assertEquals(asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1, "2012-01-01")), alerts);
    }

    public void testShouldAddThreePointsOfPriorityForEveryLateAlertAndOnePointForEveryDueAlert() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        List<Alert> alerts = alertRepository.alertsFor(All.visitCodePrefix());

        int expectedPriority = 1 + 3 + 1 + 3;

        assertEquals(asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", expectedPriority, "2012-01-01")), alerts);
    }

    public void testShouldFetchAlertsForSpecifiedType() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa 1", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case Y", "create", dataForCreateAction("due", "Theresa 2", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa 1", "TT 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case Y", "create", dataForCreateAction("due", "Theresa 2", "IFA 1", "Thaayi 2", "2012-01-01"), "0"));

        assertEquals(asList(new Alert("Case X", "Theresa 1", "ANC 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa 2", "ANC 2", "Thaayi 2", 1, "2012-01-01")), alertRepository.alertsFor(ANC.visitCodePrefix()));
        assertEquals(asList(new Alert("Case X", "Theresa 1", "TT 1", "Thaayi 1", 1, "2012-01-01")), alertRepository.alertsFor("TT"));
        assertEquals(asList(new Alert("Case Y", "Theresa 2", "IFA 1", "Thaayi 2", 1, "2012-01-01")), alertRepository.alertsFor("IFA"));
    }

    public void testShouldDeleteAlertsBasedOnCaseIDAndVisitCode() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case Y", "create", dataForCreateAction("due", "SomeOtherWoman", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));

        alertRepository.delete(new AlertAction("Case X", "delete", dataForDeleteAction("ANC 1"), "0"));

        assertEquals(asList(new Alert("Case Y", "SomeOtherWoman", "ANC 2", "Thaayi 2", 1, "2012-01-01")), alertRepository.alertsFor(All.visitCodePrefix()));
    }

    public void testShouldNotFailDeletionWhenNothingToDeleteExists() throws Exception {
        alertRepository.delete(new AlertAction("Case X", "delete", dataForDeleteAction("ANC 1"), "0"));

        assertTrue(alertRepository.alertsFor(All.visitCodePrefix()).isEmpty());
    }

    public void testShouldDeleteAllAlertsForACase() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 2", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case Y", "create", dataForCreateAction("due", "SomeOtherWoman", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 3", "Thaayi 1", "2012-01-01"), "0"));

        alertRepository.deleteAll(new AlertAction("Case X", "deleteAll", null, "0"));

        assertEquals(asList(new Alert("Case Y", "SomeOtherWoman", "ANC 2", "Thaayi 2", 1, "2012-01-01")), alertRepository.alertsFor(All.visitCodePrefix()));
    }

    public void testShouldDeleteAllAlerts() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.deleteAllAlerts();
        assertEquals(new ArrayList<Alert>(), alertRepository.alertsFor(All.visitCodePrefix()));
    }

    private Map<String, String> dataForDeleteAction(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }

    private Map<String, String> dataForCreateAction(String lateness, String beneficiaryName, String visitCode, String thaayiCardNumber, String dueDate) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("beneficiaryName", beneficiaryName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("dueDate", dueDate);
        return map;
    }
}
