package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.VillageAlertSummary;
import org.ei.drishti.dto.Action;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.ActionBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllAlertsTest {
    @Mock
    private AlertRepository alertRepository;
    private AllAlerts allAlerts;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allAlerts = new AllAlerts(alertRepository);
    }

    @Test
    public void shouldUpdateAlertRepositoryForCreateAlertActions() throws Exception {
        Action firstAction = actionForCreateAlert("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1", "0", "bherya", "Sub Center", "PHC X", "2012-01-01");
        Action secondAction = actionForCreateAlert("Case Y", "due", "Theresa 2", "ANC 2", "Thaayi 2", "0", "bherya", "Sub Center", "PHC X", "2012-01-01");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).createAlert(new Alert("Case X", "Theresa 1", "bherya", "ANC 1", "Thaayi 1", 0, "2012-01-01"));
        verify(alertRepository).createAlert(new Alert("Case Y", "Theresa 2", "bherya", "ANC 2", "Thaayi 2", 0, "2012-01-01"));
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllAlerts() throws Exception {
        allAlerts.deleteAllAlerts();

        verify(alertRepository).deleteAllAlerts();
    }

    @Test
    public void shouldDeleteFromRepositoryForDeleteActions() throws Exception {
        Action firstAction = actionForDeleteAlert("Case X", "ANC 1", "0");
        Action secondAction = actionForDeleteAlert("Case Y", "ANC 2", "0");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case X", "ANC 1");
        verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case Y", "ANC 2");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllFromRepositoryForDeleteAllActions() throws Exception {
        Action firstAction = actionForDeleteAllAlert("Case X");
        Action secondAction = actionForDeleteAllAlert("Case Y");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).deleteAllAlertsForCase("Case X");
        verify(alertRepository).deleteAllAlertsForCase("Case Y");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFindUniqueLocationsOfAlertsInDB() {
        List<VillageAlertSummary> summaries = Arrays.asList(new VillageAlertSummary("Location 1", 1), new VillageAlertSummary("Location 2", 1));
        when(alertRepository.summary()).thenReturn(summaries);

        assertEquals(summaries, allAlerts.villageSummary());
    }

    @Test
    public void shouldNotFailIfActionTypeIsNotExpected() throws Exception {
        allAlerts.handleAction(unknownAction("alert"));
    }

    @Test
    public void shouldUpdateDeleteAndDeleteAllAlertActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = actionForCreateAlert("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1", "0", "bherya", "Sub Center", "PHC X", "2012-01-01");
        Action firstDeleteAction = actionForDeleteAlert("Case Y", "ANC 2", "0");
        Action secondCreateAction = actionForCreateAlert("Case Z", "due", "Theresa 2", "ANC 2", "Thaayi 2", "0", "bherya", "Sub Center", "PHC X", "2012-01-01");
        Action deleteAllAction = actionForDeleteAllAlert("Case A");
        Action secondDeleteAction = actionForDeleteAlert("Case B", "ANC 3", "0");

        allAlerts.handleAction(firstCreateAction);
        allAlerts.handleAction(firstDeleteAction);
        allAlerts.handleAction(secondCreateAction);
        allAlerts.handleAction(deleteAllAction);
        allAlerts.handleAction(secondDeleteAction);

        InOrder inOrder = inOrder(alertRepository);
        inOrder.verify(alertRepository).createAlert(new Alert("Case X", "Theresa 1", "bherya", "ANC 1", "Thaayi 1", 0, "2012-01-01"));
        inOrder.verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case Y", "ANC 2");
        inOrder.verify(alertRepository).createAlert(new Alert("Case Z", "Theresa 2", "bherya", "ANC 2", "Thaayi 2", 0, "2012-01-01"));
        inOrder.verify(alertRepository).deleteAllAlertsForCase("Case A");
        inOrder.verify(alertRepository).deleteAlertsForVisitCodeOfCase("Case B", "ANC 3");
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFetchAllAlertsFromRepository() throws Exception {
        List<Alert> expectedAlerts = Arrays.asList(new Alert("Case X", "Theresa 1", "bherya", "ANC 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa 2", "bherya", "ANC 2", "Thaayi 2", 1, "2012-01-01"));
        when(alertRepository.allAlerts()).thenReturn(expectedAlerts);

        List<Alert> alerts = allAlerts.fetchAll();

        assertEquals(expectedAlerts, alerts);
    }
}
