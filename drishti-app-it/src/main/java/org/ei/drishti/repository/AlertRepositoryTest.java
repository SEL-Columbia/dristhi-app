package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertRepositoryTest extends AndroidTestCase {
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        alertRepository = new AlertRepository();
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), alertRepository);
    }

    public void testShouldSaveAnAlert() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        List<Alert> alerts = alertRepository.allAlerts();

        assertEquals(Arrays.asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1)), alerts);
    }

    public void testShouldAddThreePointsOfPriorityForEveryLateAlertAndOnePointForEveryDueAlert() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1")));
        List<Alert> alerts = alertRepository.allAlerts();

        int expectedPriority = 1 + 3 + 1 + 3;

        assertEquals(Arrays.asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", expectedPriority)), alerts);
    }

    public void testShouldDeleteAlertsBasedOnCaseIDAndVisitCode() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.update(new AlertAction("Case Y", "create", dataForCreateAction("due", "SomeOtherWoman", "ANC 2", "Thaayi 2")));

        alertRepository.delete(new AlertAction("Case X", "delete", dataForDeleteAction("ANC 1")));

        assertEquals(Arrays.asList(new Alert("Case Y", "SomeOtherWoman", "ANC 2", "Thaayi 2", 1)), alertRepository.allAlerts());
    }

    public void testShouldNotFailDeletionWhenNothingToDeleteExists() throws Exception {
        alertRepository.delete(new AlertAction("Case X", "delete", dataForDeleteAction("ANC 1")));

        assertTrue(alertRepository.allAlerts().isEmpty());
    }

    public void testShouldDeleteAllAlertsForACase() throws Exception {
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 2", "Thaayi 1")));
        alertRepository.update(new AlertAction("Case Y", "create", dataForCreateAction("due", "SomeOtherWoman", "ANC 2", "Thaayi 2")));
        alertRepository.update(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 3", "Thaayi 1")));

        alertRepository.deleteAll(new AlertAction("Case X", "deleteAll", null));

        assertEquals(Arrays.asList(new Alert("Case Y", "SomeOtherWoman", "ANC 2", "Thaayi 2", 1)), alertRepository.allAlerts());
    }

    private Map<String, String> dataForDeleteAction(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }

    private Map<String, String> dataForCreateAction(String lateness, String motherName, String visitCode, String thaayiCardNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("motherName", motherName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        return map;
    }
}
