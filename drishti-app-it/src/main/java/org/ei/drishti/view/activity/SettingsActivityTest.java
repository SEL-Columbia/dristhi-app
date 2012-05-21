package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.Context;
import org.ei.drishti.repository.AllSettings;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeLoginService;

import static org.ei.drishti.util.FakeContext.setupService;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<AlertsActivity> {
    private DrishtiSolo solo;
    private FakeDrishtiService drishtiService;
    private FakeLoginService loginService;

    public SettingsActivityTest() {
        super("org.ei.drishti.test", AlertsActivity.class);
        drishtiService = new FakeDrishtiService("Default");
        loginService = new FakeLoginService();
    }

    @Override
    public void setUp() throws Exception {
        setupService(drishtiService, loginService, 1000000).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldRegisterANM() throws Exception {
        AllSettings settings = Context.getInstance().allSettings();

        settings.registerANM("XYZ");

        assertEquals("XYZ", settings.fetchRegisteredANM());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
