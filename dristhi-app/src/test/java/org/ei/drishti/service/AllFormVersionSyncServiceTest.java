package org.ei.drishti.service;

import com.google.gson.Gson;
import org.ei.drishti.Context;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.domain.FormDefinitionVersion;
import org.ei.drishti.domain.Response;
import org.ei.drishti.domain.SyncStatus;
import org.ei.drishti.repository.FormsVersionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.domain.ResponseStatus.success;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Dimas Ciputra on 3/31/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AllFormVersionSyncServiceTest {

    @Mock
    AllFormVersionService allFormVersionService;
    @Mock
    HTTPAgent httpAgent;
    @Mock
    Context context;
    @Mock
    FormsVersionRepository formsVersionRepository;

    private AllFormVersionSyncService service;
    private List<FormDefinitionVersion> expectedFormDefinitionVersion;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        service = new AllFormVersionSyncService(allFormVersionService, httpAgent, context,
                formsVersionRepository);
        expectedFormDefinitionVersion = asList(new FormDefinitionVersion("form_ec", "2"));
        when(context.baseURLTest()).thenReturn("http://bidan_base_url");
    }

    @Test
    public void shouldNotDownloadIfThereIsNoPendingForms() throws Exception {
        when(formsVersionRepository.getAllFormWithSyncStatus(SyncStatus.PENDING))
                .thenReturn(Collections.<FormDefinitionVersion>emptyList());

        service.downloadAllPendingFormFromServer();

        verify(formsVersionRepository).getAllFormWithSyncStatus(SyncStatus.PENDING);
        verifyNoMoreInteractions(formsVersionRepository);
        verifyZeroInteractions(httpAgent);
    }

    @Test
    public void shouldDownloadIfThereIsAPendingForms() throws Exception {
        when(formsVersionRepository.getAllFormWithSyncStatus(SyncStatus.PENDING))
                .thenReturn(this.expectedFormDefinitionVersion);

        service.downloadAllPendingFormFromServer();

        verify(formsVersionRepository).getAllFormWithSyncStatus(SyncStatus.PENDING);
        verify(allFormVersionService).processDownloadPendingForms(this.expectedFormDefinitionVersion);
    }

    @Test
    public void shouldUpdateVersionIfThereIsNewerVersion() throws Exception {
        when(httpAgent.fetch("http://bidan_base_url/forms")).thenReturn(new Response<String>(success,
                new Gson().toJson(this.expectedFormDefinitionVersion)));

        final AllFormVersionService _allFormVersion = spy(new AllFormVersionService(formsVersionRepository, httpAgent));

        List<FormDefinitionVersion> repoForm = asList(new FormDefinitionVersion("form_ec", "1"));
        List<FormDefinitionVersion> expectedForm = asList(new FormDefinitionVersion("form_ec", "2"));

        when(formsVersionRepository.formExists("form_ec")).thenReturn(true);
        when(formsVersionRepository.getAllFormWithSyncStatus(SyncStatus.PENDING))
                .thenReturn(repoForm);
        when(formsVersionRepository.getVersion("form_ec")).thenReturn("1");

        FetchStatus fetchStatus = service.pullFormDefinitionFromServer();
        _allFormVersion.processForms(expectedForm);

        assertEquals(fetched, fetchStatus);

        verify(httpAgent).fetch("http://bidan_base_url/forms");
        verify(_allFormVersion).processForms(expectedForm);
        verify(formsVersionRepository).formExists("form_ec");
        verify(formsVersionRepository).getVersion("form_ec");
        verify(formsVersionRepository).updateServerVersion("form_ec", "2");
        verifyNoMoreInteractions(formsVersionRepository);
    }
}
