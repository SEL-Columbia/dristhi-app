package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.service.DrishtiService;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.view.Context;
import org.ei.drishti.view.activity.EligibleCoupleActivity;

import java.util.Date;

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
        Context.setInstance(new Context() {
            @Override
            protected DrishtiService drishtiService() {
                return drishtiService;
            }
        }).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
        solo.changeUser("ANM " + defaultSuffix);
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

    public void testShouldApplyFilterAfterUserChange() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        String newUser = "NEW ANM" + newSuffix;
        drishtiService.expect(newUser, "0", newSuffix);
        solo.assertNamesInECs("Wife 1 " + defaultSuffix, "Wife 2 " + defaultSuffix);

        solo.filterByText("EC 1");
        solo.assertNamesInECs("Wife 1 " + defaultSuffix);

        solo.changeUser(newUser);

        solo.assertNamesInECs("Wife 1 " + newSuffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
