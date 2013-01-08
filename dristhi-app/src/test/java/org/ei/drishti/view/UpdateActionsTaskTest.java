package org.ei.drishti.view;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.service.ActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.domain.FetchStatus.nothingFetched;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UpdateActionsTaskTest {
    @Mock
    private ActionService actionService;
    @Mock
    private ProgressIndicator progressIndicator;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldShowProgressBarsWhileFetchingAlerts() throws Exception {
        progressIndicator = mock(ProgressIndicator.class);
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(null, actionService, progressIndicator);
        when(actionService.fetchNewActions()).thenReturn(fetched);

        updateActionsTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
                assertEquals(fetched, status);
            }
        });

        InOrder inOrder = inOrder(actionService, progressIndicator);
        inOrder.verify(progressIndicator).setVisible();
        inOrder.verify(actionService).fetchNewActions();
        inOrder.verify(progressIndicator).setInvisible();
    }

    @Test
    public void shouldNotUpdateDisplayIfNothingWasFetched() throws Exception {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(null, actionService, progressIndicator);
        when(actionService.fetchNewActions()).thenReturn(nothingFetched);

        updateActionsTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
                assertEquals(nothingFetched, status);
            }
        });
    }
}
