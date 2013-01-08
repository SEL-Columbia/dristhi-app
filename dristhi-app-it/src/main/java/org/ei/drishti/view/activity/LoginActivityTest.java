package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.Context;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeNavigationService;
import org.ei.drishti.util.FakeUserService;

import java.util.Date;

import static org.ei.drishti.domain.LoginResponse.UNKNOWN_RESPONSE;
import static org.ei.drishti.domain.LoginResponse.SUCCESS;
import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private DrishtiSolo solo;
    private FakeUserService userService;
    private FakeNavigationService navigationService;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        FakeDrishtiService drishtiService = new FakeDrishtiService(String.valueOf(new Date().getTime() - 1));
        userService = new FakeUserService();
        navigationService = new FakeNavigationService();

        setupService(drishtiService, userService, -1000, navigationService).updateApplicationContext(getActivity());
        Context.getInstance().session().setPassword(null);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldAllowLoginWithoutCheckingRemoteLoginWhenLocalLoginSucceeds() throws Exception {
        userService.setupFor("user", "password", true, true, UNKNOWN_RESPONSE);

        solo.assertCanLogin(navigationService, "user", "password");

        userService.assertOrderOfCalls("local", "login");
    }

    public void testShouldTryRemoteLoginWhenThereIsNoRegisteredUser() throws Exception {
        userService.setupFor("user", "password", false, false, SUCCESS);

        solo.assertCanLogin(navigationService, "user", "password");

        userService.assertOrderOfCalls("remote", "login");
    }

    public void testShouldFailToLoginWhenBothLoginMethodsFail() throws Exception {
        userService.setupFor("user", "password", false, false, UNKNOWN_RESPONSE);

        solo.assertCannotLogin("user", "password");

        userService.assertOrderOfCalls("remote");
    }

    public void testShouldNotTryRemoteLoginWhenRegisteredUserExistsEvenIfLocalLoginFails() throws Exception {
        userService.setupFor("user", "password", true, false, SUCCESS);

        solo.assertCannotLogin("user", "password");
        userService.assertOrderOfCalls("local");
    }

    public void testShouldNotTryLocalLoginWhenRegisteredUserDoesNotExist() throws Exception {
        userService.setupFor("user", "password", false, true, SUCCESS);

        solo.assertCanLogin(navigationService, "user", "password");
        userService.assertOrderOfCalls("remote", "login");
    }

    @Override
    public void tearDown() throws Exception {
        waitForFilteringToFinish();
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }
}
