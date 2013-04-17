package org.ei.drishti.service;

import com.google.gson.Gson;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.FormSubmission;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.ResponseStatus;
import org.ei.drishti.dto.form.FormInstance;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.repository.FormDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.SyncStatus.PENDING;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class FormSubmissionSyncServiceTest {
    @Mock
    private FormDataRepository repository;
    @Mock
    private HTTPAgent httpAgent;
    @Mock
    private AllSettings allSettings;

    private FormSubmissionSyncService service;
    private List<org.ei.drishti.dto.form.FormSubmission> expectedFormSubmissions;
    private List<FormSubmission> submissions;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new FormSubmissionSyncService(httpAgent, repository, allSettings);

        String formInstanceJSON = "{form:{bind_type: 'ec'}}";
        submissions = asList(new FormSubmission("id 1", "entity id 1", "form name", formInstanceJSON, "123", PENDING));
        expectedFormSubmissions = asList(new org.ei.drishti.dto.form.FormSubmission(
                "anm id 1", "id 1", "entity id 1", "form name", new Gson().fromJson(formInstanceJSON, FormInstance.class), "123"));
        when(allSettings.fetchRegisteredANM()).thenReturn("anm id 1");
        when(repository.getPendingFormSubmissions()).thenReturn(submissions);
    }

    @Test
    public void shouldSyncPendingFormSubmissionsAndMarkThemAsSynced() throws Exception {
        when(httpAgent.post("https://drishti.modilabs.org" + "/form-submissions", new Gson().toJson(expectedFormSubmissions)))
                .thenReturn(new Response<String>(ResponseStatus.success, null));

        service.sync();

        inOrder(allSettings, httpAgent, repository);
        verify(allSettings).fetchRegisteredANM();
        verify(httpAgent).post("https://drishti.modilabs.org" + "/form-submissions", new Gson().toJson(expectedFormSubmissions));
        verify(repository).markFormSubmissionAsSynced(submissions);
    }

    @Test
    public void shouldNotMarkPendingSubmissionsAsSyncedIfPostFails() throws Exception {
        when(httpAgent.post("https://drishti.modilabs.org" + "/form-submissions", new Gson().toJson(expectedFormSubmissions)))
                .thenReturn(new Response<String>(ResponseStatus.failure, null));

        service.sync();

        verify(repository).getPendingFormSubmissions();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotSyncIfThereAreNoPendingSubmissions() throws Exception {
        when(repository.getPendingFormSubmissions()).thenReturn(Collections.<FormSubmission>emptyList());

        service.sync();

        verify(repository).getPendingFormSubmissions();
        verifyNoMoreInteractions(repository);
        verifyZeroInteractions(allSettings);
        verifyZeroInteractions(httpAgent);
    }
}
