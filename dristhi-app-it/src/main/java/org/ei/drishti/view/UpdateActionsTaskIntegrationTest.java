package org.ei.drishti.view;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.service.ActionService;
import org.ei.drishti.service.FormSubmissionSyncService;
import org.ei.drishti.sync.AfterFetchListener;
import org.ei.drishti.sync.UpdateActionsTask;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeNavigationService;
import org.ei.drishti.view.activity.LoginActivity;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.ei.drishti.domain.FetchStatus.fetched;
import static org.ei.drishti.util.FakeContext.setupService;

public class UpdateActionsTaskIntegrationTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private CountDownLatch signal;
    private final FakeDrishtiService drishtiService;

    public UpdateActionsTaskIntegrationTest() {
        super(LoginActivity.class);
        drishtiService = new FakeDrishtiService("Default");
        drishtiService.setSuffix(String.valueOf(new Date().getTime()));
        signal = new CountDownLatch(1);
    }

    public void testShouldNotUpdateWhileAnotherUpdateIsInProgress() throws Throwable {
        setupService(drishtiService, 1000000, new FakeNavigationService()).updateApplicationContext(getActivity().getApplicationContext());

        ActionServiceWithSimulatedLongRunningTask actionService = new ActionServiceWithSimulatedLongRunningTask();
        FakeFormSubmissionSyncService syncService = new FakeFormSubmissionSyncService();
        FakeProgressIndicator progressIndicator = new FakeProgressIndicator();
        final UpdateActionsTask updateActionsTask = new UpdateActionsTask(null, actionService, syncService, progressIndicator);

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
                updateActionsTask.updateFromServer(new AfterFetchListener() {
                    public void afterFetch(FetchStatus status) {
                    }
                });
            }
        });

        signal.await(6, TimeUnit.SECONDS);
        assertEquals(1, actionService.numberOfTimesFetchWasCalled());
        assertEquals(LoginActivity.class, getActivity().getClass());
    }

    private class ActionServiceWithSimulatedLongRunningTask extends ActionService {
        private int counter = 0;

        public ActionServiceWithSimulatedLongRunningTask() {
            super(null, null, null);
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

    private class FakeFormSubmissionSyncService extends FormSubmissionSyncService {
        public FakeFormSubmissionSyncService() {
            super(null, null, null);
        }

        @Override
        public void sync() {
        }
    }

    private class FakeProgressIndicator implements ProgressIndicator {
        private int currentStatus;

        @Override
        public void setVisible() {
            this.currentStatus = View.VISIBLE;
        }

        @Override
        public void setInvisible() {
            this.currentStatus = View.INVISIBLE;
        }

    }
}
