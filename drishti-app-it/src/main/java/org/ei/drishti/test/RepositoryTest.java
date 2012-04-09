package org.ei.drishti.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.repository.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryTest extends AndroidTestCase {
    private Repository repository;

    @Override
    protected void setUp() throws Exception {
        repository = new Repository(new RenamingDelegatingContext(getContext(), "test_"));
    }

    public void testSettingsFetchAndSave() throws Exception {
        repository.updateSetting("abc", "def");

        assertEquals("def", repository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldGiveDefaultValueIfThereHasBeenNoSetValue() throws Exception {
        assertEquals("someDefaultValue", repository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldOverwriteExistingValueWhenUpdating() throws Exception {
        repository.updateSetting("abc", "def");
        repository.updateSetting("abc", "ghi");

        assertEquals("ghi", repository.querySetting("abc", "someDefaultValue"));
    }

    public void testShouldSaveAnAlert() throws Exception {
        repository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        List<Alert> alerts = repository.allAlerts();

        assertEquals(Arrays.asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1)), alerts);
    }

    public void testShouldAddThreePointsOfPriorityForEveryLateAlertAndOnePointForEveryDueAlert() throws Exception {
        repository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        repository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1")));
        repository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")));
        repository.updateAlert(new AlertAction("Case X", "create", dataForCreateAction("late", "Theresa", "ANC 1", "Thaayi 1")));
        List<Alert> alerts = repository.allAlerts();

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
