package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

import static org.ei.drishti.util.FakeContext.setupService;

public class EligibleCoupleActivityTest extends ActivityInstrumentationTestCase2<EligibleCoupleActivity> {
    private DrishtiSolo solo;
    private FakeDrishtiService drishtiService;
    private String defaultSuffix;

    public EligibleCoupleActivityTest() {
        super(EligibleCoupleActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);

        setupService(drishtiService, 1000000).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldLoadECsOnStartup() throws Exception {
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);
    }

    public void testShouldUpdateWhenUpdateButtonInMenuIsPressed() throws Throwable {
        final String suffixForLoadingThroughMenuButton = String.valueOf(new Date().getTime());
        setupSuffix(suffixForLoadingThroughMenuButton);

        solo.updateUsingMenuButton();

        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix, "Wife 1 " + suffixForLoadingThroughMenuButton, "Wife 2 " + suffixForLoadingThroughMenuButton);
    }

    public void testShouldUpdateWhenPullToRefreshIsPerformed() throws Throwable {
        final String suffixForLoadingThroughPullToRefresh = String.valueOf(new Date().getTime());
        setupSuffix(suffixForLoadingThroughPullToRefresh);

        solo.updateUsingPullToRefresh();

        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix, "Wife 1 " + suffixForLoadingThroughPullToRefresh, "Wife 2 " + suffixForLoadingThroughPullToRefresh);
    }

    public void testShouldBeAbleToSwitchActivity() throws Exception {
        solo.assertCurrentActivity("Wrong activity", EligibleCoupleActivity.class);
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.switchActivity();

        solo.assertCurrentActivity("Wrong activity", AlertsActivity.class);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
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
