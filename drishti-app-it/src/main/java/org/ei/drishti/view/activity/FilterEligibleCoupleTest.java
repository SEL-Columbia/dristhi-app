package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;

public class FilterEligibleCoupleTest extends ActivityInstrumentationTestCase2<EligibleCoupleActivity> {
    private DrishtiSolo solo;

    private String defaultSuffix;
    private FakeDrishtiService drishtiService;

    public FilterEligibleCoupleTest() {
        super(EligibleCoupleActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);
        setupService(drishtiService, 1000000).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldFilterListByWifeName() throws Exception {
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.filterByText("Wife 1");
        solo.assertNamesInECs("Wife 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.filterByText("Wife 1");
        solo.assertNamesInECs("Wife 1 " + defaultSuffix);
    }

    public void testShouldFilterListByECNumber() throws Exception {
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.filterByText("EC 1");
        solo.assertNamesInECs("Wife 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.filterByText("ec 1");
        solo.assertNamesInECs("Wife 1 " + defaultSuffix);
    }

    public void testShouldApplyFilterAfterUpdation() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.filterByText("EC 1");
        solo.assertNamesInECs("Wife 1 " + defaultSuffix);

        drishtiService.setSuffix(newSuffix);

        solo.updateUsingPullToRefresh();

        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 1 " + newSuffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
