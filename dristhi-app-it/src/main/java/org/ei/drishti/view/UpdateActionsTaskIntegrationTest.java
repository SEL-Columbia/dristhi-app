package org.ei.drishti.view;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.service.ActionService;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeNavigationService;
import org.ei.drishti.view.activity.HomeActivity;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.FakeContext.setupService;

public class UpdateActionsTaskIntegrationTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    private CountDownLatch signal;
    private final FakeDrishtiService drishtiService;

    public UpdateActionsTaskIntegrationTest() {
        super(HomeActivity.class);
        drishtiService = new FakeDrishtiService("Default");
        drishtiService.setSuffix(String.valueOf(new Date().getTime()));
        signal = new CountDownLatch(1);
    }

    public void willLookAtItOnMondayTestShouldNotUpdateWhileAnotherUpdateIsInProgress() throws Throwable {
        setupService(drishtiService, 1000000, new FakeNavigationService()).updateApplicationContext(getActivity().getApplicationContext());

        ActionServiceWithSimulatedLongRunningTask service = new ActionServiceWithSimulatedLongRunningTask();
        FakeProgressIndicator progressIndicator = new FakeProgressIndicator();
        final UpdateActionsTask updateAlertsTask = new UpdateActionsTask(null, service, progressIndicator);

        for (int i = 0; i < 40; i++) {
            if (progressIndicator.currentStatus == View.VISIBLE) {
                break;
            }
            Thread.sleep(100);
        }

        runTestOnUiThread(new Runnable() {
            public void run() {
                updateFromServer();
                updateFromServer();
                updateFromServer();
            }

            private void updateFromServer() {
                updateAlertsTask.updateFromServer(new AfterFetchListener() {
                    public void afterFetch(FetchStatus status) {
                    }
                });
            }
        });

        signal.await(6, TimeUnit.SECONDS);
        assertEquals(1, service.numberOfTimesFetchWasCalled());
        assertEquals(HomeActivity.class, getActivity().getClass());
    }

    private class ActionServiceWithSimulatedLongRunningTask extends ActionService {

        private int counter = 0;
        public ActionServiceWithSimulatedLongRunningTask() {
            super(null, null, null, null, null);
        }

        @Override
        public FetchStatus fetchNewActions() {
            counter++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            signal.countDown();
            return fetched;
        }

        public int numberOfTimesFetchWasCalled() {
            return counter;
        }
    }

    private class FakeProgressIndicator implements ProgressIndicator {
        private int currentStatus;

        @Override
        public void setVisibile() {
            this.currentStatus = View.VISIBLE;
        }

        @Override
        public void setInvisible() {
            this.currentStatus = View.INVISIBLE;
        }
    }
}
