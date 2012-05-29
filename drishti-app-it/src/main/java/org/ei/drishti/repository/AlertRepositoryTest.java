package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.util.Session;

import java.util.*;

import static java.util.Arrays.asList;

public class AlertRepositoryTest extends AndroidTestCase {
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        alertRepository = new AlertRepository();
        Session session = new Session().setPassword("password");
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), "drishti.db" + new Date().getTime(), session, alertRepository);
        alertRepository.deleteAllAlerts();
    }

    public void testShouldSaveAnAlert() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        List<Alert> alerts = alertRepository.allAlerts();

        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", 1, "2012-01-01")), alerts);
    }

    public void testShouldAddThreePointsOfPriorityForEveryLateAlertAndOnePointForEveryDueAlert() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "late", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "late", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        List<Alert> alerts = alertRepository.allAlerts();

        int expectedPriority = 1 + 3 + 1 + 3;

        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", expectedPriority, "2012-01-01")), alerts);
    }

    public void testShouldFetchAllAlerts() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa 1", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case Y", "create", dataForCreateAction("bherya", "due", "Theresa 2", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa 1", "TT 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case Y", "create", dataForCreateAction("bherya", "due", "Theresa 2", "IFA 1", "Thaayi 2", "2012-01-01"), "0"));

        assertEquals(asList(new Alert("Case X", "Theresa 1", "bherya", "ANC 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa 2", "bherya", "ANC 2", "Thaayi 2", 1, "2012-01-01"),
                new Alert("Case X", "Theresa 1", "bherya", "TT 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa 2", "bherya", "IFA 1", "Thaayi 2", 1, "2012-01-01")), alertRepository.allAlerts());
    }

    public void testShouldFetchAllUniqueLocations() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("Bherya 1", "due", "Theresa 1", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case Y", "create", dataForCreateAction("Bherya 2", "due", "Theresa 2", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case Z", "create", dataForCreateAction("Bherya 1", "due", "Theresa 3", "TT 1", "Thaayi 3", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case A", "create", dataForCreateAction("Bherya 3", "due", "Theresa 4", "IFA 1", "Thaayi 4", "2012-01-01"), "0"));

        assertEquals(asList("Bherya 1", "Bherya 2", "Bherya 3"), alertRepository.uniqueLocations());
    }

    public void testShouldDeleteAlertsBasedOnCaseIDAndVisitCode() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case Y", "create", dataForCreateAction("bherya", "due", "SomeOtherWoman", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));

        alertRepository.deleteAlertsForVisitCodeOfCase(new Action("Case X", "delete", dataForDeleteAction("ANC 1"), "0"));

        assertEquals(asList(new Alert("Case Y", "SomeOtherWoman", "bherya", "ANC 2", "Thaayi 2", 1, "2012-01-01")), alertRepository.allAlerts());
    }

    public void testShouldNotFailDeletionWhenNothingToDeleteExists() throws Exception {
        alertRepository.deleteAlertsForVisitCodeOfCase(new Action("Case X", "delete", dataForDeleteAction("ANC 1"), "0"));

        assertTrue(alertRepository.allAlerts().isEmpty());
    }

    public void testShouldDeleteAllAlertsForACase() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 2", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case Y", "create", dataForCreateAction("bherya", "due", "SomeOtherWoman", "ANC 2", "Thaayi 2", "2012-01-01"), "0"));
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "late", "Theresa", "ANC 3", "Thaayi 1", "2012-01-01"), "0"));

        alertRepository.deleteAllAlertsForCase(new Action("Case X", "deleteAll", null, "0"));

        assertEquals(asList(new Alert("Case Y", "SomeOtherWoman", "bherya", "ANC 2", "Thaayi 2", 1, "2012-01-01")), alertRepository.allAlerts());
    }

    public void testShouldDeleteAllAlerts() throws Exception {
        alertRepository.update(new Action("Case X", "create", dataForCreateAction("bherya", "due", "Theresa", "ANC 1", "Thaayi 1", "2012-01-01"), "0"));
        alertRepository.deleteAllAlerts();
        assertEquals(new ArrayList<Alert>(), alertRepository.allAlerts());
    }

    private Map<String, String> dataForDeleteAction(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }

    private Map<String, String> dataForCreateAction(String village, String lateness, String beneficiaryName, String visitCode, String thaayiCardNumber, String dueDate) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("beneficiaryName", beneficiaryName);
        map.put("village", village);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        map.put("dueDate", dueDate);
        return map;
    }
}
