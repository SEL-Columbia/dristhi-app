package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.Context;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeUserService;

import java.util.Date;

import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class LoginActivityWhenAppIsKilledByAndroidTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private DrishtiSolo solo;
    private FakeUserService userService;

    public LoginActivityWhenAppIsKilledByAndroidTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        FakeDrishtiService drishtiService = new FakeDrishtiService(String.valueOf(new Date().getTime() - 1));
        userService = new FakeUserService();

        setupService(drishtiService, userService, -1000).updateApplicationContext(getActivity());
        Context.getInstance().session().setPassword(null);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldGoBackToLoginScreenWhenThePasswordIsNotSetBecauseOfTheActivityHavingBeenStoppedByTheOSBeforeExpiryOfSession() {
        userService.setupFor("user", "password", false, false, true);
        solo.assertCanLogin("user", "password");
        waitForProgressBarToGoAway(getActivity());

        Context.getInstance().session().setPassword(null);
        solo.switchActivity();

        solo.waitForActivity(LoginActivity.class.getSimpleName());
    }

    @Override
    public void tearDown() throws Exception {
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }
}
