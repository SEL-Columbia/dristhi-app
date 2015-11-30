package org.ei.telemedicine.test.service;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import org.ei.telemedicine.domain.Response;
import org.ei.telemedicine.domain.ResponseStatus;
import org.ei.telemedicine.dto.Action;
import org.ei.telemedicine.repository.AllEligibleCouples;
import org.ei.telemedicine.repository.AllReports;
import org.ei.telemedicine.repository.AllSettings;
import org.ei.telemedicine.repository.AllSharedPreferences;
import org.ei.telemedicine.router.ActionRouter;
import org.ei.telemedicine.service.ActionService;
import org.ei.telemedicine.service.DrishtiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static org.ei.telemedicine.domain.FetchStatus.fetched;
import static org.ei.telemedicine.domain.FetchStatus.fetchedFailed;
import static org.ei.telemedicine.domain.FetchStatus.nothingFetched;
import static org.ei.telemedicine.domain.ResponseStatus.failure;
import static org.ei.telemedicine.domain.ResponseStatus.success;
import static org.ei.telemedicine.test.util.ActionBuilder.actionForCloseAlert;
import static org.ei.telemedicine.test.util.ActionBuilder.actionForCloseMother;
import static org.ei.telemedicine.test.util.ActionBuilder.actionForCreateAlert;
import static org.ei.telemedicine.test.util.ActionBuilder.actionForReport;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ActionServiceTest {

    @Mock
    private DrishtiService drishtiService;
    @Mock
    private AllSettings allSettings;
    @Mock
    private AllSharedPreferences allSharedPreferences;
   @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllReports allReports;
    @Mock
    private ActionRouter actionRouter;

    private ActionService service;




    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new ActionService(drishtiService, allSettings, allSharedPreferences, allReports, actionRouter);
    }

    @Test
    public void testShouldFetchAlertActionsAndNotSaveAnythingIfThereIsNothingNewToSave() throws Exception {
        setupActions(success, new ArrayList<Action>());

        assertEquals(nothingFetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(actionRouter);
    }

    @Test
    public void testShouldNotSaveAnythingIfTheDrishtiResponseStatusIsFailure() throws Exception {
        setupActions(failure, asList(actionForCloseAlert("Case X", "ANC 1", "2012-01-01", "0")));

        assertEquals(fetchedFailed, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(actionRouter);
    }

    @Test
    public void testShouldFetchAlertActionsAndSaveThemToRepository() throws Exception {
        Action action = actionForCreateAlert("Case X", "normal", "mother", "Ante Natal Care - Normal", "ANC 1", "2012-01-01", null, "0");
        setupActions(success, asList(action));

        assertEquals(fetched, service.fetchNewActions());

        verify(drishtiService).fetchNewActions("ANM X", "1234");
        verify(actionRouter).directAlertAction(action);

    }

    @Test
    public void testShouldUpdatePreviousIndexWithIndexOfEachActionThatIsHandled() throws Exception {

        Action firstAction = actionForCreateAlert("Case X", "normal", "mother", "Ante Natal Care - Normal", "ANC 1", "2012-01-01", "2012-01-22", "11111");
        Action secondAction = actionForCreateAlert("Case Y", "normal", "mother", "Ante Natal Care - Normal", "ANC 2", "2012-01-01", "2012-01-11", "12345");

        setupActions(success, asList(firstAction, secondAction));

        service.fetchNewActions();

        InOrder inOrder = inOrder(actionRouter, allSettings);
        inOrder.verify(actionRouter).directAlertAction(firstAction);
        inOrder.verify(allSettings).savePreviousFetchIndex("11111");
        inOrder.verify(actionRouter).directAlertAction(secondAction);
        inOrder.verify(allSettings).savePreviousFetchIndex("12345");
    }

    @Test
    public void testShouldHandleDifferentKindsOfActions() throws Exception {
        Action reportAction = actionForReport("Case X", "annual target");
        Action alertAction = actionForCreateAlert("Case X", "normal", "mother", "Ante Natal Care - Normal", "ANC 1", "2012-01-01", null, "0");
        Action closeMotherAction = actionForCloseMother("Case X");
        setupActions(success, asList(reportAction, alertAction, closeMotherAction));

        service.fetchNewActions();

        verify(allReports).handleAction(reportAction);
        verify(actionRouter).directAlertAction(alertAction);
        verify(actionRouter).directMotherAction(closeMotherAction);
    }

    private void setupActions(ResponseStatus status, List<Action> list) {

        //AllSettings allSettings = Mockito.mock(AllSettings.class);

        when(allSettings.fetchPreviousFetchIndex()).thenReturn("1234");
        when(allSharedPreferences.fetchRegisteredANM()).thenReturn("ANM X");
        when(drishtiService.fetchNewActions("ANM X", "1234")).thenReturn(new Response<List<Action>>(status, list));
    }
}
