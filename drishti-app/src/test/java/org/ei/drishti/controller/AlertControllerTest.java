package org.ei.drishti.controller;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.util.ActionBuilder;
import org.ei.drishti.view.adapter.ListAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ResponseStatus.success;
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
    private ListAdapter<Alert> alertAdapter;
    @Mock
    private AllEligibleCouples allEligibleCouples;

    private AlertController alertController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        alertController = new AlertController(alertAdapter, allAlerts);
    }

    @Test
    public void shouldRetrieveAlertsAndRefreshView() throws Exception {
        setupAlertActions(success, asList(ActionBuilder.actionForCreateAlert("Case X", "due", "Theresa", "ANC 1", "Thaayi 1", "0")));

        List<Alert> alerts = asList(new Alert("Case X", "Theresa", "bherya", "ANC 1", "Thaayi 1", 1, "2012-01-01"));
        when(allAlerts.fetchAll()).thenReturn(alerts);

        alertController.refreshAlertsFromDB();

        verify(allAlerts).fetchAll();
        verify(alertAdapter).updateItems(alerts);
        verifyNoMoreInteractions(drishtiService);
        verifyNoMoreInteractions(alertAdapter);
    }

    private void setupAlertActions(ResponseStatus status, List<Action> list) {
        when(allSettings.fetchPreviousFetchIndex()).thenReturn("1234");
        when(allSettings.fetchANMIdentifier()).thenReturn("ANM X");
        when(drishtiService.fetchNewAlertActions("ANM X", "1234")).thenReturn(new Response<List<Action>>(status, list));
    }
}
