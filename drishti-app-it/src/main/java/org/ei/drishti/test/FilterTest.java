package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.view.activity.DrishtiMainActivity;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

import static org.ei.drishti.util.Wait.waitForFilteringToFinish;

public class FilterTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {
    private DrishtiSolo solo;

    private String defaultSuffix;
    private FakeDrishtiService drishtiService;

    public FilterTest() {
        super(DrishtiMainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);
        DrishtiMainActivity.setDrishtiService(drishtiService);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldFilterListByMotherName() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByText("Theresa 1");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListByThaayiCardNumber() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByText("Thaayi 1");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnTypeDialog() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType("BCG");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.filterByType("OPV");
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnBothTypeFilterValueAndTextFilter() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType("BCG");
        solo.filterByText("Thaayi 2");
        solo.assertEmptyList();

        solo.filterByType("OPV");
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);
    }

    public void testShouldApplyFilterAfterUpdation() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType("BCG");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        drishtiService.setSuffix(newSuffix);

        solo.updateAlerts();

        solo.assertBeneficiaryNames("Theresa 1 " + newSuffix);
    }

    public void testShouldApplyFilterAfterUserChange() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        String newUser = "NEW ANM" + newSuffix;
        drishtiService.expect(newUser, "0", newSuffix);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType("BCG");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.changeUser(newUser);

        solo.assertBeneficiaryNames("Theresa 1 " + newSuffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
