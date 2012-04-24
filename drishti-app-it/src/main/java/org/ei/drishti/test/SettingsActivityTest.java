package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {
    private DrishtiSolo solo;
    private FakeDrishtiService drishtiService;

    public SettingsActivityTest() {
        super("org.ei.drishti.test", DrishtiMainActivity.class);
        drishtiService = new FakeDrishtiService("Default");
    }

    @Override
    public void setUp() throws Exception {
        DrishtiMainActivity.setDrishtiService(drishtiService);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
        waitForProgressBarToGoAway(getActivity(), 2000);
    }

    public void testShouldFetchAlertsForNewANMWHenANMIsChanged() throws Exception {
        String suffix = String.valueOf(new Date().getTime());
        String newUser = "duck" + suffix;
        drishtiService.expect(newUser, "0", suffix);

        solo.sendKey(Solo.MENU);
        solo.clickOnText("Settings");
        solo.clickOnText("ANM ID");
        solo.clearEditText(0);
        solo.enterText(0, newUser);
        solo.clickOnButton("OK");
        solo.goBack();

        solo.assertBeneficiaryNames("Theresa 1 " + suffix, "Theresa 2 " + suffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
