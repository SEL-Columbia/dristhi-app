package org.ei.drishti.view;

import android.view.View;
import android.widget.ProgressBar;
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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UpdateActionsTaskTest {
    @Mock
    private ProgressBar progressBar;
    @Mock
    private ActionService actionService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldShowProgressBarsWhileFetchingAlerts() throws Exception {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(null, actionService, new AndroidProgressIndicator(progressBar));
        when(actionService.fetchNewActions()).thenReturn(fetched);

        updateActionsTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
                assertEquals(fetched, status);
            }
        });

        InOrder inOrder = inOrder(actionService, progressBar);
        inOrder.verify(progressBar).setVisibility(View.VISIBLE);
        inOrder.verify(actionService).fetchNewActions();
        inOrder.verify(progressBar).setVisibility(View.INVISIBLE);
    }

    @Test
    public void shouldNotUpdateDisplayIfNothingWasFetched() throws Exception {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(null, actionService, new AndroidProgressIndicator(progressBar));
        when(actionService.fetchNewActions()).thenReturn(nothingFetched);

        updateActionsTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
                assertEquals(nothingFetched, status);
            }
        });
    }
}
