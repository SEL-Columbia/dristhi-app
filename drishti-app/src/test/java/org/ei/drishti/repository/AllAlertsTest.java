package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Alert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
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
        Action firstAction = actionForCreateAlert("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1", "0");
        Action secondAction = actionForCreateAlert("Case Y", "late", "Theresa 2", "ANC 2", "Thaayi 2", "0");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).update(firstAction);
        verify(alertRepository).update(secondAction);
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

        verify(alertRepository).deleteAlertsForVisitCodeOfCase(firstAction);
        verify(alertRepository).deleteAlertsForVisitCodeOfCase(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllFromRepositoryForDeleteAllActions() throws Exception {
        Action firstAction = actionForDeleteAllAlert("Case X");
        Action secondAction = actionForDeleteAllAlert("Case Y");

        allAlerts.handleAction(firstAction);
        allAlerts.handleAction(secondAction);

        verify(alertRepository).deleteAllAlertsForCase(firstAction);
        verify(alertRepository).deleteAllAlertsForCase(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFindUniqueLocationsOfAlertsInDB() {
        List<String> locations = Arrays.asList("Location 1", "Location 2");
        when(alertRepository.uniqueLocations()).thenReturn(locations);

        assertEquals(locations, allAlerts.uniqueLocations());
    }

    @Test
    public void shouldNotFailIfActionTypeIsNotExpected() throws Exception {
        allAlerts.handleAction(new Action("Case X", "alert", "UNKNOWN-TYPE", new HashMap<String, String>(), "0"));
    }

    @Test
    public void shouldUpdateDeleteAndDeleteAllAlertActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = actionForCreateAlert("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1", "0");
        Action firstDeleteAction = actionForDeleteAlert("Case Y", "ANC 2", "0");
        Action secondCreateAction = actionForCreateAlert("Case Z", "due", "Theresa 2", "ANC 2", "Thaayi 2", "0");
        Action deleteAllAction = actionForDeleteAllAlert("Case A");
        Action secondDeleteAction = actionForDeleteAlert("Case B", "ANC 3", "0");

        allAlerts.handleAction(firstCreateAction);
        allAlerts.handleAction(firstDeleteAction);
        allAlerts.handleAction(secondCreateAction);
        allAlerts.handleAction(deleteAllAction);
        allAlerts.handleAction(secondDeleteAction);

        InOrder inOrder = inOrder(alertRepository);
        inOrder.verify(alertRepository).update(firstCreateAction);
        inOrder.verify(alertRepository).deleteAlertsForVisitCodeOfCase(firstDeleteAction);
        inOrder.verify(alertRepository).update(secondCreateAction);
        inOrder.verify(alertRepository).deleteAllAlertsForCase(deleteAllAction);
        inOrder.verify(alertRepository).deleteAlertsForVisitCodeOfCase(secondDeleteAction);
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
