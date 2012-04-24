package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;

import java.util.Date;

import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

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
        waitForProgressBarToGoAway(getActivity(), 2000);
    }

    public void testShouldFilterListByMotherName() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.enterText(0, "Theresa 1");
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.enterText(0, "X");
        waitForFilteringToFinish();
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListByThaayiCardNumber() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.enterText(0, "Thaayi 1");
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.enterText(0, "X");
        waitForFilteringToFinish();
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnDropDownValue() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.pressSpinnerItem(0, 1);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.pressSpinnerItem(0, 3);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnBothDropDownValueAndTextFilter() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.pressSpinnerItem(0, 1);
        solo.enterText(0, "Thaayi 2");
        waitForFilteringToFinish();
        solo.assertEmptyList();

        solo.pressSpinnerItem(0, 3);
        waitForFilteringToFinish();
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
