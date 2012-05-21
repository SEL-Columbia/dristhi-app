package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.view.activity.AlertsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.BCG;
import static org.ei.drishti.domain.AlertFilterCriterionForType.OPV;
import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;

public class FilterAlertTest extends ActivityInstrumentationTestCase2<AlertsActivity> {
    private DrishtiSolo solo;

    private String defaultSuffix;
    private FakeDrishtiService drishtiService;
    private SimpleDateFormat inputFormat;

    public FilterAlertTest() {
        super(AlertsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);
        setupService(drishtiService, 1000000).updateApplicationContext(getActivity().getApplicationContext());

        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        solo = new DrishtiSolo(getInstrumentation(), getActivity());
    }

    public void testShouldFilterListByMotherName() throws Exception {
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByText("Theresa 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByText("theresa 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);
    }

    public void testShouldFilterListByThaayiCardNumber() throws Exception {
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByText("Thaayi 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByText("thaayi 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnTypeDialog() throws Exception {
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByType(OPV);
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnBothTypeFilterValueAndTextFilter() throws Exception {
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.filterByText("Thaayi 2");
        solo.assertEmptyList();

        solo.filterByType(OPV);
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);
    }

    public void testShouldApplyFilterAfterUpdation() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByType(BCG);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        drishtiService.setSuffix(newSuffix);

        solo.updateUsingPullToRefresh();

        solo.assertNamesInAlerts("Theresa 1 " + newSuffix);
    }

    public void testShouldFilterListBasedOnValueInTimeFilterDialog() throws Exception {
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByTime(PastDue);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByTime(Upcoming);
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);

        solo.filterByTime(ShowAll);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnLocation() throws Exception {
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);

        solo.filterByLocation("Bherya 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByLocation("Bherya 2");
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);

        solo.filterByLocation("All");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix);
    }

/*
    public void testShouldShowAlertsForTodayInUpcomingFilteredValues() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());
        String newUser = "NEW ANM" + newSuffix;

        Action alertAction = new Action("Case M", "createAlert", dataForCreateAction("due", "Mom " + newSuffix, "ANC 1", "TC 12", inputFormat.format(DateUtil.today()), "Bherya 1"), "1234567");
        drishtiService.expect(newUser, "0", new Response<List<Action>>(ResponseStatus.success, asList(alertAction)));
        solo.changeUser(newUser);

        solo.assertNamesInAlerts("Mom " + newSuffix);

        solo.filterByTime(AlertFilterCriterionForTime.Upcoming);
        solo.assertNamesInAlerts("Mom " + newSuffix);

        solo.filterByTime(AlertFilterCriterionForTime.PastDue);
        solo.assertEmptyList();

        solo.filterByTime(AlertFilterCriterionForTime.ShowAll);
        solo.assertNamesInAlerts("Mom " + newSuffix);
    }
*/

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
