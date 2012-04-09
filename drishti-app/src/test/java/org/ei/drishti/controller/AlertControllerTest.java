package org.ei.drishti.controller;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.adapter.AlertAdapter;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.domain.Response;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.service.DrishtiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.ei.drishti.domain.Response.ResponseStatus.failure;
import static org.ei.drishti.domain.Response.ResponseStatus.success;
import static org.ei.drishti.util.AlertActionBuilder.actionForCreate;
import static org.ei.drishti.util.AlertActionBuilder.actionForDelete;
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
        setupAlertActions(success, new ArrayList<AlertAction>());

        alertController.refreshAlerts();

        verify(drishtiService).fetchNewAlertActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(allAlerts);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    public void shouldNotSaveAnythingIfTheDrishtiResponseStatusIsFailure() throws Exception {
        setupAlertActions(failure, Arrays.asList(actionForDelete("Case X", "ANC 1")));

        alertController.refreshAlerts();

        verify(drishtiService).fetchNewAlertActions("ANM X", "1234");
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(allAlerts);
        verifyNoMoreInteractions(adapter);
    }

    @Test
    public void shouldFetchAlertActionsAndSaveThemToRepository() throws Exception {
        setupAlertActions(success, Arrays.asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1")));

        alertController.refreshAlerts();

        verify(drishtiService).fetchNewAlertActions("ANM X", "1234");
        verify(allAlerts).saveNewAlerts(Arrays.asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1")));
    }

    @Test
    public void shouldRetrieveAlertsAndRefreshView() throws Exception {
        setupAlertActions(success, Arrays.asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1")));

        List<Alert> alerts = Arrays.asList(new Alert("Case X", "Theresa", "ANC 1", "Thaayi 1", 1));
        when(allAlerts.fetchAllAlerts()).thenReturn(alerts);

        alertController.refreshAlerts();

        verify(allAlerts).fetchAllAlerts();
        verify(adapter).refresh(alerts);
    }

    private void setupAlertActions(Response.ResponseStatus status, List<AlertAction> list) {
        when(allSettings.fetchPreviousFetchIndex()).thenReturn("1234");
        when(allSettings.fetchANMIdentifier()).thenReturn("ANM X");
        when(drishtiService.fetchNewAlertActions("ANM X", "1234")).thenReturn(new Response<List<AlertAction>>(status, list));
    }
}
