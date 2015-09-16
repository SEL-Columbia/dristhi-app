package org.ei.telemedicine.test.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import static org.ei.telemedicine.domain.LoginResponse.UNKNOWN_RESPONSE;
import static org.ei.telemedicine.domain.LoginResponse.SUCCESS;
import static org.ei.telemedicine.test.util.FakeContext.setupService;
import static org.ei.telemedicine.test.util.Wait.waitForFilteringToFinish;
import static org.ei.telemedicine.test.util.Wait.waitForProgressBarToGoAway;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.test.util.DrishtiSolo;
import org.ei.telemedicine.test.util.FakeDrishtiService;
import org.ei.telemedicine.test.util.FakeUserService;
import org.ei.telemedicine.view.activity.LoginActivity;

import java.util.Date;


public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private DrishtiSolo solo;
    private FakeUserService userService;

    public LoginActivityTest() {
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

    @MediumTest
    public void testIgnoreTestShouldTryRemoteLoginWhenThereIsNoRegisteredUser() throws Exception {
        userService.setupFor("user", "password", false, false, SUCCESS);

        solo.assertCanLogin("user", "password");

        userService.assertOrderOfCalls("remote", "login");
    }

    @MediumTest
    public void testIgnoreTestShouldAllowLoginWithoutCheckingRemoteLoginWhenLocalLoginSucceeds() throws Exception {
        userService.setupFor("user", "password", true, true, UNKNOWN_RESPONSE);

        solo.assertCanLogin("user", "password");

        userService.assertOrderOfCalls("local", "login");
    }



    @MediumTest
    public void testIgnoreTestShouldFailToLoginWhenBothLoginMethodsFail() throws Exception {
        userService.setupFor("user", "password", false, false, UNKNOWN_RESPONSE);

        solo.assertCannotLogin("user", "password");

        userService.assertOrderOfCalls("remote");
    }

    @MediumTest
    public void testIgnoreTestShouldNotTryRemoteLoginWhenRegisteredUserExistsEvenIfLocalLoginFails() throws Exception {
        userService.setupFor("user", "password", true, false, SUCCESS);

        solo.assertCannotLogin("user", "password");
        userService.assertOrderOfCalls("local");
    }

    @MediumTest
    public void testIgnoreTestShouldNotTryLocalLoginWhenRegisteredUserDoesNotExist() throws Exception {
        userService.setupFor("user", "password", false, true, SUCCESS);

        solo.assertCanLogin("user", "password");
        userService.assertOrderOfCalls("remote", "login");
    }



    @Override
    public void tearDown() throws Exception {
        waitForFilteringToFinish();
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }




}