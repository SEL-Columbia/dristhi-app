package org.ei.drishti.view;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ProgressBar;
import org.ei.drishti.R;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class UpdateAlertsTaskIntegrationTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {
    private CountDownLatch signal;
    private FakeDrishtiService drishtiService;

    public UpdateAlertsTaskIntegrationTest() {
        super(DrishtiMainActivity.class);
        drishtiService = new FakeDrishtiService("Default");
        drishtiService.setSuffix(String.valueOf(new Date().getTime()));
        DrishtiMainActivity.setDrishtiService(drishtiService);
        signal = new CountDownLatch(1);
    }

    public void testShouldNotUpdateWhileAnotherUpdateIsInProgress() throws Throwable {
        AlertControllerWithSimulatedLongRunningTask controller = new AlertControllerWithSimulatedLongRunningTask();
        final UpdateAlertsTask updateAlertsTask = new UpdateAlertsTask(controller, (ProgressBar) getActivity().findViewById(R.id.progressBar));

        waitForProgressBarToGoAway(getActivity(), 4000);

        runTestOnUiThread(new Runnable() {
            public void run() {
                updateAlertsTask.updateFromServer();
                updateAlertsTask.updateFromServer();
                updateAlertsTask.updateFromServer();
            }
        });

        signal.await(6, TimeUnit.SECONDS);
        assertEquals(1, controller.numberOfTimesFetchWasCalled());
    }

    private class AlertControllerWithSimulatedLongRunningTask extends AlertController {
        private int counter = 0;

        public AlertControllerWithSimulatedLongRunningTask() {
            super(null, null, null, null);
        }

        @Override
        public FetchStatus fetchNewAlerts() {
            counter++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            signal.countDown();
            return fetched;
        }

        @Override
        public void refreshAlertsFromDB() {
        }

        public int numberOfTimesFetchWasCalled() {
            return counter;
        }
    }
}
