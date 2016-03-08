package org.ei.opensrp.indonesia.service;

import org.apache.commons.io.IOUtils;
import org.ei.opensrp.DristhiConfiguration;
import org.ei.opensrp.domain.FetchStatus;
import org.ei.opensrp.domain.Response;
import org.ei.opensrp.domain.ResponseStatus;
import org.ei.opensrp.indonesia.repository.AllSettingsINA;
import org.ei.opensrp.indonesia.view.controller.UniqueIdController;
import org.ei.opensrp.repository.AllSettings;
import org.ei.opensrp.repository.AllSharedPreferences;
import org.ei.opensrp.service.HTTPAgent;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Dimas on 9/23/2015.
 */
@RunWith(RobolectricTestRunner.class)
public class UniqueIdServiceTest {

    @Mock
    private HTTPAgent httpAgent;
    @Mock
    private DristhiConfiguration configuration;
    @Mock
    private UniqueIdController controller;
    @Mock
    private AllSharedPreferences allSharedPreferences;
    @Mock
    private AllSettingsINA allSettings;

    private UniqueIdService uniqueIdService;

    public static final String URL = "http://opensrp_base_url";

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        uniqueIdService = new UniqueIdService(httpAgent, configuration, controller,
                allSettings, allSharedPreferences);
        when(configuration.dristhiBaseURL()).thenReturn(URL);
    }

    @Test
    public void shouldPullUniqueIdFromServer() throws Exception {
        when(httpAgent.fetchWithCredentials(URL + "/unique-id", "username", "password")).thenReturn(new Response<String>(ResponseStatus.success,
                IOUtils.toString(getClass().getResource("/uids.json"))));
        Response<String> responseStatus = uniqueIdService.pullUniqueIdFromServer("username", "password");
        verify(httpAgent).fetchWithCredentials(URL + "/unique-id", "username", "password");
        assertEquals(ResponseStatus.success, responseStatus.status());
    }

    @Test
    public void shouldFetchNoUniqueIdWhenJsonIsForEmptyList() throws Exception {
        when(httpAgent.fetchWithCredentials(URL + "/unique-id", "username", "password")).thenReturn(new Response<String>(ResponseStatus.success, null));
        Response<String> responseStatus = uniqueIdService.pullUniqueIdFromServer("username", "password");
        assertTrue(responseStatus.payload().isEmpty());
    }

    @Test
    public void shouldfetchWithCredentialssWhenHTTPCallFails() throws Exception {
        when(httpAgent.fetchWithCredentials(URL + "/unique-id", "username", "password")).thenReturn(new Response<String>(ResponseStatus.failure, null));
        Response<String> responseStatus = uniqueIdService.pullUniqueIdFromServer("username", "password");
        assertTrue(responseStatus.payload().isEmpty());
        assertEquals(ResponseStatus.failure, responseStatus.status());
    }

    @Test
    public void shouldUpdateLastUsedIdWithCurrent() throws Exception {
        when(allSettings.fetchLastUsedId()).thenReturn("1");
        when(allSettings.fetchCurrentId()).thenReturn("2");
        JSONObject json = new JSONObject();
        json.put("lastUsedId", "2");
        when(httpAgent.post(URL + "/last-used-id", json.toString())).thenReturn(new Response<String>(ResponseStatus.failure, null));
        uniqueIdService.sync();
        verify(allSettings).saveLastUsedId("2");
        verify(httpAgent).post(URL + "/last-used-id", json.toString());
    }

    @Test
    public void shouldUpdateCurrentIdWithLastUsed() throws Exception {
        when(allSettings.fetchLastUsedId()).thenReturn("2");
        when(allSettings.fetchCurrentId()).thenReturn("1");
        uniqueIdService.sync();
        verify(allSettings).saveCurrentId("2");
    }

    @Test
    public void shouldGetLastUsedIdFromServer() throws Exception {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "1");
        jsonResponse.put("lastUsedId", "123");
        when(httpAgent.fetchWithCredentials(URL + "/last-used-id", "username", "password")).thenReturn(new Response<>(ResponseStatus.success, jsonResponse.toString()));
        uniqueIdService.getLastUsedId("username", "password");
        verify(allSettings).saveLastUsedId("123");
        verify(allSettings).saveCurrentId("123");
    }

    @Test
    public void shouldRefillUniqueId() throws Exception {
        when(controller.needToRefillUniqueId()).thenReturn(true);
        when(httpAgent.fetch(URL + "/refill-unique-id")).thenReturn(new Response<>(ResponseStatus.success, IOUtils.toString(getClass().getResource("/uids.json"))));
        when(allSettings.fetchLastUsedId()).thenReturn("1");
        when(allSettings.fetchCurrentId()).thenReturn("0");
        FetchStatus status = uniqueIdService.sync();

        verify(httpAgent).fetch(URL + "/refill-unique-id");
        verify(controller).saveUniqueId("10019");
        assertEquals(status, FetchStatus.fetched);
    }

}
