package org.ei.drishti;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.apache.commons.io.IOUtils;
import org.ei.drishti.agent.HTTPAgent;
import org.ei.drishti.domain.AlertAction;
import org.ei.drishti.domain.Response;
import org.ei.drishti.service.DrishtiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.ei.drishti.util.AlertActionBuilder.actionForCreate;
import static org.ei.drishti.util.AlertActionBuilder.actionForDelete;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class DrishtiAgentTest {
    @Mock
    HTTPAgent httpAgent;

    public static final String EXPECTED_URL = "http://base.drishti.url/alerts?anmIdentifier=anm1&timeStamp=0";
    private DrishtiService drishtiService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        drishtiService = new DrishtiService(httpAgent, "http://base.drishti.url");
    }

    @Test
    public void shouldFetchAlertActions() throws Exception {
        when(httpAgent.fetch(EXPECTED_URL)).thenReturn(new Response<String>(Response.ResponseStatus.success, IOUtils.toString(getClass().getResource("/alerts.json"))));

        Response<List<AlertAction>> alertActions = drishtiService.fetchNewAlertActions("anm1", "0");

        verify(httpAgent).fetch(EXPECTED_URL);
        assertEquals(Arrays.asList(actionForCreate("Case X", "due", "Theresa", "ANC 1", "Thaayi 1"), actionForDelete("Case Y", "ANC 1")), alertActions.payload());
        assertEquals(Response.ResponseStatus.success, alertActions.status());
    }

    @Test
    public void shouldFetchNoAlertActionsWhenJsonIsForEmptyList() throws Exception {
        when(httpAgent.fetch(EXPECTED_URL)).thenReturn(new Response<String>(Response.ResponseStatus.success, "[]"));

        Response<List<AlertAction>> alertActions = drishtiService.fetchNewAlertActions("anm1", "0");

        assertTrue(alertActions.payload().isEmpty());
    }

    @Test
    public void shouldFetchNoAlertActionsWhenHTTPCallFails() throws Exception {
        when(httpAgent.fetch(EXPECTED_URL)).thenReturn(new Response<String>(Response.ResponseStatus.failure, null));

        Response<List<AlertAction>> alertActions = drishtiService.fetchNewAlertActions("anm1", "0");

        assertTrue(alertActions.payload().isEmpty());
        assertEquals(Response.ResponseStatus.failure, alertActions.status());
    }
}