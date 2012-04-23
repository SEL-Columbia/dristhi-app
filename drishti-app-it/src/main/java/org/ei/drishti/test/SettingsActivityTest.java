package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.domain.Alert;

import java.util.Date;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private FakeDrishtiService drishtiService;

    public SettingsActivityTest() {
        super("org.ei.drishti.test", DrishtiMainActivity.class);
        drishtiService = new FakeDrishtiService("Default");
    }

    @Override
    public void setUp() throws Exception {
        DrishtiMainActivity.setDrishtiService(drishtiService);

        solo = new Solo(getInstrumentation(), getActivity());
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

        assertEquals(2, solo.getCurrentListViews().get(0).getCount());
        assertEquals("Thaayi 1 " + suffix, ((Alert) solo.getCurrentListViews().get(0).getItemAtPosition(0)).thaayiCardNo());
        assertEquals("Thaayi 2 " + suffix, ((Alert) solo.getCurrentListViews().get(0).getItemAtPosition(1)).thaayiCardNo());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
