package org.ei.drishti.service;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.*;
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
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allPregnancies;
    @Mock
    private AllReports allReports;
    @Mock
    private AlertService alertService;

    private ActionService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ActionService(drishtiService, allSettings, allEligibleCouples, allPregnancies, allReports, alertService);
    }

    @Test
    public void shouldFetchAlertActionsAndNotSaveAnythingIfThereIsNothingNewToSave() throws Exception {
        setupActions(success, new ArrayList<Action>());

        assertEquals(nothingFetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(alertService);
    }

    @Test
    public void shouldNotSaveAnythingIfTheDrishtiResponseStatusIsFailure() throws Exception {
        setupActions(failure, asList(actionForCloseAlert("Case X", "ANC 1", "2012-01-01", "0")));

        assertEquals(fetchedFailed, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(alertService);
    }

    @Test
    public void shouldFetchAlertActionsAndSaveThemToRepository() throws Exception {
        setupActions(success, asList(ActionBuilder.actionForCreateAlert("Case X", "normal", "mother", "ANC 1", "2012-01-01", null, "0")));

        assertEquals(fetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verify(alertService).handleAction(ActionBuilder.actionForCreateAlert("Case X", "normal", "mother", "ANC 1", "2012-01-01", null, "0"));
    }

    @Test
    public void shouldUpdatePreviousIndexWithIndexOfEachActionThatIsHandled() throws Exception {

        Action firstAction = actionForCreateAlert("Case X", "normal", "mother", "ANC 1", "2012-01-01", "2012-01-22", "11111");
        Action secondAction = actionForCreateAlert("Case Y", "normal", "mother", "ANC 2", "2012-01-01", "2012-01-11", "12345");

        setupActions(success, asList(firstAction, secondAction));

        service.fetchNewActions();

        InOrder inOrder = inOrder(alertService, allSettings);
        inOrder.verify(alertService).handleAction(firstAction);
        inOrder.verify(allSettings).savePreviousFetchIndex("11111");
        inOrder.verify(alertService).handleAction(secondAction);
        inOrder.verify(allSettings).savePreviousFetchIndex("12345");
    }

    @Test
    public void shouldFetchECAndSaveThemToRepository() throws Exception {
        setupActions(success, asList(actionForCreateEC("Case X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", "PHC X")));

        assertEquals(fetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verify(allEligibleCouples).handleAction(actionForCreateEC("Case X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", "PHC X"));
    }

    @Test
    public void shouldHandleDifferentKindsOfActions() throws Exception {
        Action ecAction = actionForCreateEC("Case X", "Wife 1", "Husband 1", "EC Number", "Village 1", "SubCenter 1", "PHC X");
        Action alertAction = actionForCreateAlert("Case X", "normal", "mother", "ANC 1", "2012-01-01", null, "0");
        setupActions(success, asList(ecAction, alertAction));

        service.fetchNewActions();

        InOrder inOrder = inOrder(allEligibleCouples, alertService);
        inOrder.verify(allEligibleCouples).handleAction(ecAction);
        inOrder.verify(alertService).handleAction(alertAction);
    }

    private void setupActions(ResponseStatus status, List<Action> list) {
        when(allSettings.fetchPreviousFetchIndex()).thenReturn("1234");
        when(allSettings.fetchRegisteredANM()).thenReturn("ANM X");
        when(drishtiService.fetchNewActions("ANM X", "1234")).thenReturn(new Response<List<Action>>(status, list));
    }
}
