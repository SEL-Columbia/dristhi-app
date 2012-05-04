package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.domain.*;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.view.activity.DrishtiMainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.BCG;
import static org.ei.drishti.domain.AlertFilterCriterionForType.OPV;
import static org.ei.drishti.util.FakeDrishtiService.dataForCreateAction;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;

public class FilterTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {
    private DrishtiSolo solo;

    private String defaultSuffix;
    private FakeDrishtiService drishtiService;
    private SimpleDateFormat inputFormat;

    public FilterTest() {
        super(DrishtiMainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);
        DrishtiMainActivity.setDrishtiService(drishtiService);

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        solo.changeUser("ANM " + defaultSuffix);
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

        solo.filterByText("theresa 1");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);
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

        solo.filterByText("thaayi 1");
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnTypeDialog() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.filterByType(OPV);
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnBothTypeFilterValueAndTextFilter() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.filterByText("Thaayi 2");
        solo.assertEmptyList();

        solo.filterByType(OPV);
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);
    }

    public void testShouldApplyFilterAfterUpdation() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        drishtiService.setSuffix(newSuffix);

        solo.updateAlertsUsingPullToRefresh();

        solo.assertBeneficiaryNames("Theresa 1 " + newSuffix);
    }

    public void testShouldApplyFilterAfterUserChange() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        String newUser = "NEW ANM" + newSuffix;
        drishtiService.expect(newUser, "0", newSuffix);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.changeUser(newUser);

        solo.assertBeneficiaryNames("Theresa 1 " + newSuffix);
    }

    public void testShouldFilterListBasedOnValueInTimeFilterDialog() throws Exception {
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByTime(PastDue);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix);

        solo.filterByTime(Upcoming);
        solo.assertBeneficiaryNames("Theresa 2 " + defaultSuffix);

        solo.filterByTime(ShowAll);
        solo.assertBeneficiaryNames("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldShowAlertsForTodayInUpcomingFilteredValues() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        String newUser = "NEW ANM" + newSuffix;

        AlertAction alertAction = new AlertAction("Case M", "createAlert", dataForCreateAction("due", "Mom " + newSuffix, "ANC 1", "TC 12", inputFormat.format(DateUtil.today())), "1234567");
        drishtiService.expect(newUser, "0", new Response<List<AlertAction>>(ResponseStatus.success, asList(alertAction)));
        solo.changeUser(newUser);

        solo.assertBeneficiaryNames("Mom " + newSuffix);

        solo.filterByTime(AlertFilterCriterionForTime.Upcoming);
        solo.assertBeneficiaryNames("Mom " + newSuffix);

        solo.filterByTime(AlertFilterCriterionForTime.PastDue);
        solo.assertEmptyList();

        solo.filterByTime(AlertFilterCriterionForTime.ShowAll);
        solo.assertBeneficiaryNames("Mom " + newSuffix);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
