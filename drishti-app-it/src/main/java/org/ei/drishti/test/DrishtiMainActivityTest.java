package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

public class DrishtiMainActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {

    private FakeDrishtiService drishtiService;
    private DrishtiSolo solo;
    private String defaultSuffix;

    public DrishtiMainActivityTest() {
        super(DrishtiMainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);
        DrishtiMainActivity.setDrishtiService(drishtiService);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldLoadOnStartup() throws Throwable {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldUpdateWhenUpdateButtonInMenuIsPressed() throws Throwable {
        final String suffixForLoadingThroughMenuButton = String.valueOf(new Date().getTime());
        setupSuffix(suffixForLoadingThroughMenuButton);

        solo.updateAlerts();

        solo.assertBeneficiaryNames("Theresa 1 " + suffixForLoadingThroughMenuButton, "Theresa 2 " + suffixForLoadingThroughMenuButton);
    }

    private DrishtiService setupSuffix(String suffix) {
        drishtiService.setSuffix(suffix);
        return drishtiService;
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}