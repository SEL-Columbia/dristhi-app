package org.ei.drishti.controller;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.view.adapter.AlertAdapter;
import org.ei.drishti.domain.*;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.service.DrishtiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.domain.FetchStatus.fetchedFailed;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;
import static org.ei.drishti.domain.ResponseStatus.failure;
import static org.ei.drishti.domain.ResponseStatus.success;
import static org.ei.drishti.util.ActionBuilder.actionForCreate;
import static org.ei.drishti.util.ActionBuilder.actionForDelete;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AlertControllerTest {
    @Mock
    private DrishtiService drishtiService;
    @Mock
    private AllSettings allSettings;
    @Mock
    private AllAlerts allAlerts;
    @Mock
    private AlertAdapter adapter;

    private AlertController alertController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        alertController = new AlertController(drishtiService, allSettings, allAlerts, adapter);
    }

    @Test
    public void shouldFetchAlertActionsAndNotSaveAnythingIfThereIsNothingNewToSave() throws Exception {
        setupAlertActions(success, new ArrayList<Action>());

        assertEquals(nothingFetched, alertController.fetchNewActions());

        verify(drishtiService).fetchNewAlertActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(allAlerts);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    public void shouldNotSaveAnythingIfTheDrishtiResponseStatusIsFailure() throws Exception {
        setupAlertActions(failure, asList(actionForDelete("Case X", "ANC 1")));

        assertEquals(fetchedFailed, alertController.fetchNewActions());

        verify(drishtiService).fetchNewAlertActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(allAlerts);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    public void shouldFetchAlertActionsAndSaveThemToRepository() throws Exception {
        setupAlertActions(success, asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1")));

        assertEquals(fetched, alertController.fetchNewActions());

        verify(drishtiService).fetchNewAlertActions("ANM X", "1234");
        verify(allAlerts).saveNewAlerts(asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1")));
    }

    @Test
    public void shouldRetrieveAlertsAndRefreshView() throws Exception {
        setupAlertActions(success, asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1")));

        List<Alert> alerts = asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1, "2012-01-01"));
        when(allAlerts.fetchAlerts()).thenReturn(alerts);

        alertController.refreshAlertsFromDB();

        verify(allAlerts).fetchAlerts();
        verify(adapter).updateAlerts(alerts);
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    public void shouldUpdatePreviousIndexWithIndexOfLatestAlert() throws Exception {
        String indexOfLastMessage = "12345";
        setupAlertActions(success, asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1", "11111"), actionForCreate("Case Y", "due", "Theresa", "ANC 2", "Thaayi 2", indexOfLastMessage)));

        when(allAlerts.fetchAlerts()).thenReturn(asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa", "ANC 2", "Thaayi 2", 1, "2012-01-01")));

        alertController.fetchNewActions();

        verify(allSettings).savePreviousFetchIndex(indexOfLastMessage);
    }

    @Test
    public void shouldDeleteAllAlertsAndPreviousFetchIndexWhenUserChanged() throws Exception {
        alertController.changeUser();
        verify(allAlerts).deleteAllAlerts();
        verify(allSettings).savePreviousFetchIndex("0");
    }

    private void setupAlertActions(ResponseStatus status, List<Action> list) {
        when(allSettings.fetchPreviousFetchIndex()).thenReturn("1234");
        when(allSettings.fetchANMIdentifier()).thenReturn("ANM X");
        when(drishtiService.fetchNewAlertActions("ANM X", "1234")).thenReturn(new Response<List<Action>>(status, list));
    }
}
