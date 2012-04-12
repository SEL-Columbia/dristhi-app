package org.ei.drishti.view;

import android.view.View;
import android.widget.ProgressBar;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.controller.AlertController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.inOrder;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UpdateAlertsTaskTest {
    @Mock
    private AlertController alertController;
    @Mock
    private ProgressBar progressBar;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldShowProgressBarsWhileFetchingAlerts() throws Exception {
        UpdateAlertsTask updateAlertsTask = new UpdateAlertsTask(alertController, progressBar);
        updateAlertsTask.updateFromServer();

        InOrder inOrder = inOrder(alertController, progressBar);
        inOrder.verify(progressBar).setVisibility(View.VISIBLE);
        inOrder.verify(alertController).fetchNewAlerts();
        inOrder.verify(progressBar).setVisibility(View.INVISIBLE);
        inOrder.verify(alertController).refreshAlertsOnView();
    }
}
