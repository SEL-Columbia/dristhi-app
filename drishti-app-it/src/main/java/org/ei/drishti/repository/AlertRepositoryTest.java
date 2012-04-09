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
        alertRepository = new AlertRepository(new RenamingDelegatingContext(getContext(), "test_"));
    }

    public void testShouldSaveAnAlert() throws Exception {
        alertRepository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        List<Alert> alerts = alertRepository.allAlerts();

        assertEquals(Arrays.asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1)), alerts);
    }

    public void testShouldAddThreePointsOfPriorityForEveryLateAlertAndOnePointForEveryDueAlert() throws Exception {
        alertRepository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        alertRepository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1")));
        List<Alert> alerts = alertRepository.allAlerts();

        int expectedPriority = 1 + 3 + 1 + 3;

        assertEquals(Arrays.asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", expectedPriority)), alerts);
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
