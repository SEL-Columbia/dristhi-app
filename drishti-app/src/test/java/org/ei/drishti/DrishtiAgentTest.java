package org.ei.drishti;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class DrishtiAgentTest {
    @Mock HTTPAgent httpAgent;

    public static final String EXPECTED_URL = "http://base.drishti.url/alerts?anmIdentifier=anm1&timeStamp=0";
    private DrishtiAgent drishtiAgent;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        drishtiAgent = new DrishtiAgent(httpAgent, "http://base.drishti.url");
    }

    @Test
    public void shouldFetchAlertActions() throws Exception {
        when(httpAgent.fetch(EXPECTED_URL)).thenReturn(new Response<String>(Response.ResponseStatus.success, IOUtils.toString(getClass().getResource("/alerts.json"))));

        Response<List<AlertAction>> alertActions = drishtiAgent.fetchNewAlerts("anm1", "0");

        verify(httpAgent).fetch(EXPECTED_URL);
        assertEquals(Arrays.asList(new AlertAction("Case X", "create", dataForCreateAction("due", "Theresa", "ANC 1", "Thaayi 1")), new AlertAction("Case Y", "delete", dataForDeleteAction("ANC 1"))), alertActions.payload());
        assertEquals(Response.ResponseStatus.success, alertActions.status());
    }

    @Test
    public void shouldFetchNoAlertActionsWhenJsonIsForEmptyList() throws Exception {
        when(httpAgent.fetch(EXPECTED_URL)).thenReturn(new Response<String>(Response.ResponseStatus.success, "[]"));

        Response<List<AlertAction>> alertActions = drishtiAgent.fetchNewAlerts("anm1", "0");

        assertTrue(alertActions.payload().isEmpty());
    }

    @Test
    public void shouldFetchNoAlertActionsWhenHTTPCallFails() throws Exception {
        when(httpAgent.fetch(EXPECTED_URL)).thenReturn(new Response<String>(Response.ResponseStatus.failure, null));

        Response<List<AlertAction>> alertActions = drishtiAgent.fetchNewAlerts("anm1", "0");

        assertTrue(alertActions.payload().isEmpty());
        assertEquals(Response.ResponseStatus.failure, alertActions.status());
    }

    private Map<String, String> dataForCreateAction(String lateness, String motherName, String visitCode, String thaayiCardNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("latenessStatus", lateness);
        map.put("motherName", motherName);
        map.put("visitCode", visitCode);
        map.put("thaayiCardNumber", thaayiCardNumber);
        return map;
    }

    private Map<String, String> dataForDeleteAction(String visitCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("visitCode", visitCode);
        return map;
    }
}