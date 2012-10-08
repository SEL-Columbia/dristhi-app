package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.Context;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.service.NavigationService;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeUserService;

import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    private DrishtiSolo solo;
    private FakeDrishtiService drishtiService;
    private FakeUserService userService;

    public SettingsActivityTest() {
        super("org.ei.drishti.test", HomeActivity.class);
        drishtiService = new FakeDrishtiService("Default");
        userService = new FakeUserService();
    }

    @Override
    public void setUp() throws Exception {
        setupService(drishtiService, userService, 1000000, new NavigationService()).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldRegisterANM() throws Exception {
        AllSettings settings = Context.getInstance().allSettings();

        settings.registerANM("XYZ");

        assertEquals("XYZ", settings.fetchRegisteredANM());
    }

    @Override
    public void tearDown() throws Exception {
        waitForFilteringToFinish();
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }
}
