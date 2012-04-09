package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.exception.AlertTypeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.AlertActionBuilder.*;
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
        AlertAction firstAction = actionForCreate("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1");
        AlertAction secondAction = actionForCreate("Case Y", "late", "Theresa 2", "ANC 2", "Thaayi 2");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));

        verify(alertRepository).update(firstAction);
        verify(alertRepository).update(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteFromRepositoryForDeleteActions() throws Exception {
        AlertAction firstAction = actionForDelete("Case X", "ANC 1");
        AlertAction secondAction = actionForDelete("Case Y", "ANC 2");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));

        verify(alertRepository).delete(firstAction);
        verify(alertRepository).delete(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllFromRepositoryForDeleteAllActions() throws Exception {
        AlertAction firstAction = actionForDeleteAll("Case X");
        AlertAction secondAction = actionForDeleteAll("Case Y");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));

        verify(alertRepository).deleteAll(firstAction);
        verify(alertRepository).deleteAll(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test(expected = AlertTypeException.class)
    public void shouldFailIfActionTypeIsNotExpected() throws Exception {
        allAlerts.saveNewAlerts(Arrays.asList(new AlertAction("Case X", "UNKNOWN-TYPE", new HashMap<String, String>())));
    }

    @Test
    public void shouldUpdateDeleteAndDeleteAllAlertActionsBasedOnTheirType() throws Exception {
        AlertAction firstCreateAction = actionForCreate("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1");
        AlertAction firstDeleteAction = actionForDelete("Case Y", "ANC 2");
        AlertAction secondCreateAction = actionForCreate("Case Z", "due", "Theresa 2", "ANC 2", "Thaayi 2");
        AlertAction deleteAllAction = actionForDeleteAll("Case A");
        AlertAction secondDeleteAction = actionForDelete("Case B", "ANC 3");

        allAlerts.saveNewAlerts(Arrays.asList(firstCreateAction, firstDeleteAction, secondCreateAction, deleteAllAction, secondDeleteAction));

        verify(alertRepository).update(firstCreateAction);
        verify(alertRepository).delete(firstDeleteAction);
        verify(alertRepository).update(secondCreateAction);
        verify(alertRepository).deleteAll(deleteAllAction);
        verify(alertRepository).delete(secondDeleteAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFetchAllAlertsFromRepository() throws Exception {
        List<Alert> expectedAlerts = Arrays.asList(new Alert("Case X", "Theresa 1", "ANC 1", "Thaayi 1", 1), new Alert("Case Y", "Theresa 2", "ANC 2", "Thaayi 2", 1));
        when(alertRepository.allAlerts()).thenReturn(expectedAlerts);

        List<Alert> alerts = allAlerts.fetchAllAlerts();

        assertEquals(expectedAlerts, alerts);
    }
}
