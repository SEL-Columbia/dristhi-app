package org.ei.telemedicine.test.view.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.test.util.DrishtiSolo;
import org.ei.telemedicine.test.util.FakeDrishtiService;
import org.ei.telemedicine.test.util.FakeUserService;
import org.ei.telemedicine.view.activity.LoginActivity;
import org.ei.telemedicine.view.activity.NativeANCSmartRegisterActivity;
import org.ei.telemedicine.view.activity.NativeChildSmartRegisterActivity;
import org.ei.telemedicine.view.activity.NativeECSmartRegisterActivity;
import org.ei.telemedicine.view.activity.NativeFPSmartRegisterActivity;
import org.ei.telemedicine.view.activity.NativeHomeActivity;
import org.ei.telemedicine.view.activity.NativePNCSmartRegisterActivity;
import org.ei.telemedicine.view.activity.ReportsActivity;

import java.util.Date;

import static org.ei.telemedicine.domain.LoginResponse.SUCCESS;
import static org.ei.telemedicine.test.util.FakeContext.setupService;
import static org.ei.telemedicine.test.util.Wait.waitForFilteringToFinish;
import static org.ei.telemedicine.test.util.Wait.waitForProgressBarToGoAway;


public class NativeHomeActivityTest extends ActivityInstrumentationTestCase2<NativeHomeActivity>
{
    private DrishtiSolo solo;
    private FakeUserService userService;
    private NativeHomeActivity homeActivity;

    public NativeHomeActivityTest()
    {
        super(NativeHomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        homeActivity = getActivity();
        FakeDrishtiService drishtiService = new FakeDrishtiService(String.valueOf(new Date().getTime() - 1));
        userService = new FakeUserService();

        setupService(drishtiService, userService, 100000).updateApplicationContext(getActivity());
        Context.getInstance().session().setPassword("password");

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    @MediumTest
    public void testIgnoringShouldGoBackToLoginScreenWhenLoggedOutWithAbilityToLogBackIn() throws Exception {
        userService.setupFor("user", "password", false, false, SUCCESS);

        solo.logout();
        solo.assertCurrentActivity("Should be in Login screen.", LoginActivity.class);

        userService.setupFor("user", "password", false, false, SUCCESS);
        solo.assertCanLogin("user", "password");
    }

    @MediumTest
    public void testShouldLaunchEcRegisterOnPressingEcRegisterButton() {
        verifyLaunchOfActivityOnPressingButton(R.id.btn_ec_register, NativeECSmartRegisterActivity.class);
    }

    @MediumTest
    public void testShouldLaunchAncRegisterOnPressingAncRegisterButton() {
        verifyLaunchOfActivityOnPressingButton(R.id.btn_anc_register, NativeANCSmartRegisterActivity.class);
    }

    @MediumTest
    public void testShouldLaunchPncRegisterOnPressingPncRegisterButton() {
        verifyLaunchOfActivityOnPressingButton(R.id.btn_pnc_register, NativePNCSmartRegisterActivity.class);
    }

    @MediumTest
    public void testShouldLaunchFpRegisterOnPressingFpRegisterButton() {
        verifyLaunchOfActivityOnPressingButton(R.id.btn_fp_register, NativeFPSmartRegisterActivity.class);
    }

    @MediumTest
    public void testShouldLaunchChildRegisterOnPressingChildRegisterButton() {
        verifyLaunchOfActivityOnPressingButton(R.id.btn_child_register, NativeChildSmartRegisterActivity.class);
    }

    @MediumTest
    public void testShouldLaunchReportingActivityOnPressingReportingButton() {
        verifyLaunchOfActivityOnPressingButton(R.id.btn_reporting, ReportsActivity.class);
    }

    public <T> void verifyLaunchOfActivityOnPressingButton(int buttonId, Class<T> clazz) {

        // TODO test launch of respective activity
        homeActivity = this.getActivity();
        Intent i = new Intent(homeActivity, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add a monitor before we start the activity
        Instrumentation.ActivityMonitor mGameActivityMonitor = new Instrumentation.ActivityMonitor(clazz.getName(), null, false);
        getInstrumentation().addMonitor(mGameActivityMonitor);

        homeActivity.startActivity(i);

        Activity activity = mGameActivityMonitor.waitForActivityWithTimeout(5 * 1000);
        assertNotNull("Activity was not started", activity);

    }

    @Override
    public void tearDown() throws Exception {
        waitForFilteringToFinish();
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }

}
