package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.ActionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.domain.FetchStatus.*;
import static org.ei.drishti.domain.ResponseStatus.failure;
import static org.ei.drishti.domain.ResponseStatus.success;
import static org.ei.drishti.util.ActionBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class ActionServiceTest {
    @Mock
    private DrishtiService drishtiService;
    @Mock
    private AllSettings allSettings;
    @Mock
    private AllAlerts allAlerts;
    @Mock
    private AllEligibleCouples allEligibleCouples;

    private ActionService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ActionService(drishtiService, allSettings, allAlerts, allEligibleCouples);
    }

    @Test
    public void shouldFetchAlertActionsAndNotSaveAnythingIfThereIsNothingNewToSave() throws Exception {
        setupActions(success, new ArrayList<Action>());

        assertEquals(nothingFetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(allAlerts);
    }

    @Test
    public void shouldNotSaveAnythingIfTheDrishtiResponseStatusIsFailure() throws Exception {
        setupActions(failure, asList(actionForDeleteAlert("Case X", "ANC 1", "0")));

        assertEquals(fetchedFailed, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(allAlerts);
    }

    @Test
    public void shouldFetchAlertActionsAndSaveThemToRepository() throws Exception {
        setupActions(success, asList(ActionBuilder.actionForCreateAlert("Case X", "due", "Theresa", "ANC 1", "Thaayi 1", "0")));

        assertEquals(fetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verify(allAlerts).handleAction(ActionBuilder.actionForCreateAlert("Case X", "due", "Theresa", "ANC 1", "Thaayi 1", "0"));
    }
    @Test
    public void shouldUpdatePreviousIndexWithIndexOfLatestAlert() throws Exception {
        String indexOfLastMessage = "12345";
        setupActions(success, asList(actionForCreateAlert("Case X", "due", "Theresa", "ANC 1", "Thaayi 1", "11111"), actionForCreateAlert("Case Y", "due", "Theresa", "ANC 2", "Thaayi 2", indexOfLastMessage)));

        when(allAlerts.fetchAll()).thenReturn(asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa", "bherya", "ANC 2", "Thaayi 2", 1, "2012-01-01")));

        service.fetchNewActions();

        verify(allSettings).savePreviousFetchIndex(indexOfLastMessage);
    }

    @Test
    public void shouldFetchECActionsAndSaveThemToRepository() throws Exception {
        setupActions(success, asList(actionForCreateEC("Case X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1")));

        assertEquals(fetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verify(allEligibleCouples).handleAction(actionForCreateEC("Case X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1"));
    }

    @Test
    public void shouldHandleDifferentKindsOfActions() throws Exception {
        Action ecAction = actionForCreateEC("Case X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1");
        Action alertAction = actionForCreateAlert("Case X", "due", "Theresa", "ANC 1", "Thaayi 1", "0");
        setupActions(success, asList(ecAction, alertAction));

        service.fetchNewActions();

        InOrder inOrder = inOrder(allEligibleCouples, allAlerts);
        inOrder.verify(allEligibleCouples).handleAction(ecAction);
        inOrder.verify(allAlerts).handleAction(alertAction);
    }

    private void setupActions(ResponseStatus status, List<Action> list) {
        when(allSettings.fetchPreviousFetchIndex()).thenReturn("1234");
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");
        when(drishtiService.fetchNewActions("ANM X", "1234")).thenReturn(new Response<List<Action>>(status, list));
    }
}
