package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.Context;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeNavigationService;
import org.ei.drishti.util.FakeUserService;

import java.util.Date;

import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    private DrishtiSolo solo;
    private FakeUserService userService;
    private FakeNavigationService navigationService;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        FakeDrishtiService drishtiService = new FakeDrishtiService(String.valueOf(new Date().getTime() - 1));
        userService = new FakeUserService();
        navigationService = new FakeNavigationService();

        setupService(drishtiService, userService, 100000, navigationService).updateApplicationContext(getActivity());
        Context.getInstance().session().setPassword("password");

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    /* Bug in Android. Cannot run webview with emulator: http://code.google.com/p/android/issues/detail?id=12987 */
    public void ignoringTestShouldGoBackToLoginScreenWhenLoggedOutWithAbilityToLogBackIn() throws Exception {
        userService.setupFor("user", "password", false, false, true);

        solo.logout();
        solo.assertCurrentActivity("Should be in Login screen.", LoginActivity.class);

        userService.setupFor("user", "password", false, false, true);
        solo.assertCanLogin(navigationService, "user", "password");
    }

    @Override
    public void tearDown() throws Exception {
        waitForFilteringToFinish();
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }
}
