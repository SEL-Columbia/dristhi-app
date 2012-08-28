package org.ei.drishti.repository;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.VillageAlertSummary;
import org.ei.drishti.util.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertStatus.closed;
import static org.ei.drishti.domain.AlertStatus.open;
import static org.ei.drishti.dto.AlertPriority.normal;
import static org.ei.drishti.dto.AlertPriority.urgent;

public class AlertRepositoryTest extends AndroidTestCase {
    private AlertRepository alertRepository;

    @Override
    protected void setUp() throws Exception {
        alertRepository = new AlertRepository();
        Session session = new Session().setPassword("password").setRepositoryName("drishti.db" + new Date().getTime());
        new Repository(new RenamingDelegatingContext(getContext(), "test_"), session, alertRepository);
        alertRepository.deleteAllAlerts();
    }

    public void testShouldSaveAnAlert() throws Exception {
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        List<Alert> alerts = alertRepository.allAlerts();

        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open)), alerts);
    }

    public void testShouldUpdateAnExistingAlertInTheRepositoryOnlyIfThePriorityChanges() throws Exception {
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));

        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());

        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", urgent, "2012-01-01", "2012-01-11", open));
        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", urgent, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());

        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldFetchAllAlerts() throws Exception {
        Alert alert1 = new Alert("Case X", "Theresa 1", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open);
        Alert alert2 = new Alert("Case Y", "Theresa 2", "bherya", "ANC 2", "Thaayi 2", urgent, "2012-01-01", "2012-01-11", closed);
        Alert alert3 = new Alert("Case X", "Theresa 1", "bherya", "TT 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open);
        Alert alert4 = new Alert("Case Y", "Theresa 2", "bherya", "IFA 1", "Thaayi 2", urgent, "2012-01-01", "2012-01-11", closed);

        alertRepository.createAlert(alert1);
        alertRepository.createAlert(alert2);
        alertRepository.createAlert(alert3);
        alertRepository.createAlert(alert4);

        assertEquals(asList(alert1, alert2, alert3, alert4), alertRepository.allAlerts());
    }

    public void testShouldFetchAllAlertsForAVillage() throws Exception {
        Alert alert1 = new Alert("Case X", "Theresa 1", "bherya1", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open);
        Alert alert2 = new Alert("Case Y", "Theresa 2", "bherya2", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open);
        Alert alert3 = new Alert("Case X", "Theresa 1", "bherya1", "TT 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open);
        Alert alert4 = new Alert("Case Y", "Theresa 2", "bherya2", "IFA 1", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open);

        alertRepository.createAlert(alert1);
        alertRepository.createAlert(alert2);
        alertRepository.createAlert(alert3);
        alertRepository.createAlert(alert4);

        assertEquals(asList(alert1, alert3), alertRepository.allForVillage("bherya1"));
        assertEquals(asList(alert2, alert4), alertRepository.allForVillage("bherya2"));
    }

    public void testShouldFetchSummaryAllUniqueLocations() throws Exception {
        alertRepository.createAlert(new Alert("Case X", "Theresa 1", "Bherya 1", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case Y", "Theresa 2", "Bherya 2", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case Z", "Theresa 3", "Bherya 1", "TT 1", "Thaayi 3", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case A", "Theresa 4", "Bherya 3", "IFA 1", "Thaayi 4", normal, "2012-01-01", "2012-01-11", open));

        assertEquals(asList(new VillageAlertSummary("Bherya 1", 2), new VillageAlertSummary("Bherya 2", 1), new VillageAlertSummary("Bherya 3", 1)), alertRepository.summary());
    }

    public void testShouldMarkAlertsAsClosedBasedOnCaseIDAndVisitCode() throws Exception {
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case Y", "SomeOtherWoman", "bherya", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open));

        alertRepository.markAlertAsClosed("Case X", "ANC 1");

        assertEquals(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", closed),
                new Alert("Case Y", "SomeOtherWoman", "bherya", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldNotFailClosingAlertWhenNoAlertExists() throws Exception {
        alertRepository.markAlertAsClosed("Case X", "ANC 1");

        assertTrue(alertRepository.allAlerts().isEmpty());
    }

    public void testShouldDeleteAllAlertsForACase() throws Exception {
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 2", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case Y", "SomeOtherWoman", "bherya", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 3", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));

        alertRepository.deleteAllAlertsForCase("Case X");

        assertEquals(asList(new Alert("Case Y", "SomeOtherWoman", "bherya", "ANC 2", "Thaayi 2", normal, "2012-01-01", "2012-01-11", open)), alertRepository.allAlerts());
    }

    public void testShouldDeleteAllAlerts() throws Exception {
        alertRepository.createAlert(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", normal, "2012-01-01", "2012-01-11", open));
        alertRepository.deleteAllAlerts();
        assertEquals(new ArrayList<Alert>(), alertRepository.allAlerts());
    }
}
