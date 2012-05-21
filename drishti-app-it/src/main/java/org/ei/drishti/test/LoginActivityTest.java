package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.Context;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeLoginService;
import org.ei.drishti.view.activity.LoginActivity;

import java.util.Date;

import static org.ei.drishti.util.FakeContext.setupService;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private DrishtiSolo solo;
    private FakeLoginService loginService;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        FakeDrishtiService drishtiService = new FakeDrishtiService(String.valueOf(new Date().getTime() - 1));
        loginService = new FakeLoginService();

        setupService(drishtiService, loginService, -1000).updateApplicationContext(getActivity());
        Context.getInstance().setPassword(null);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldAllowLoginWithoutCheckingRemoteLoginWhenLocalLoginSucceeds() throws Exception {
        loginService.setupFor("user", "password", true, true, false);

        solo.assertCanLogin("user", "password");

        loginService.assertOrderOfCalls("local", "login");
    }

    public void testShouldTryRemoteLoginWhenThereIsNoRegisteredUser() throws Exception {
        loginService.setupFor("user", "password", false, false, true);

        solo.assertCanLogin("user", "password");

        loginService.assertOrderOfCalls("remote", "login");
    }

    public void testShouldFailToLoginWhenBothLoginMethodsFail() throws Exception {
        loginService.setupFor("user", "password", false, false, false);

        solo.assertCannotLogin("user", "password");

        loginService.assertOrderOfCalls("remote");
    }

    public void testShouldNotTryRemoteLoginWhenRegisteredUserExistsEvenIfLocalLoginFails() throws Exception {
        loginService.setupFor("user", "password", true, false, true);

        solo.assertCannotLogin("user", "password");
        loginService.assertOrderOfCalls("local");
    }

    public void testShouldNotTryLocalLoginWhenRegisteredUserDoesNotExist() throws Exception {
        loginService.setupFor("user", "password", false, true, true);

        solo.assertCanLogin("user", "password");
        loginService.assertOrderOfCalls("remote", "login");
    }

    public void testShouldGoBackToLoginScreenWhenLoggedOutWithAbilityToLogBackIn() throws Exception {
        loginService.setupFor("user", "password", false, false, true);
        solo.assertCanLogin("user", "password");

        solo.logout();
        solo.assertCurrentActivity("Should be in Login screen.", LoginActivity.class);

        loginService.setupFor("user", "password", false, false, true);
        solo.assertCanLogin("user", "password");
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
