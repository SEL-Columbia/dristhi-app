package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.view.Context;
import org.ei.drishti.view.activity.AlertsActivity;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<AlertsActivity> {
    private DrishtiSolo solo;
    private FakeDrishtiService drishtiService;

    public SettingsActivityTest() {
        super("org.ei.drishti.test", AlertsActivity.class);
        drishtiService = new FakeDrishtiService("Default");
    }

    @Override
    public void setUp() throws Exception {
        Context.setInstance(new Context() {
            @Override
            protected DrishtiService drishtiService() {
                return drishtiService;
            }
        }).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldFetchAlertsForNewANMWHenANMIsChanged() throws Exception {
        String suffix = String.valueOf(new Date().getTime());
        String newUser = "NEW ANM" + suffix;
        drishtiService.expect(newUser, "0", suffix);

        solo.changeUser(newUser);

        solo.assertBeneficiaryNames("Theresa 1 " + suffix, "Theresa 2 " + suffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
