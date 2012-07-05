package org.ei.drishti.view.activity;

import android.test.ActivityInstrumentationTestCase2;
import org.ei.drishti.domain.Response;
import org.ei.drishti.dto.Action;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.DrishtiSolo;
import org.ei.drishti.util.FakeDrishtiService;
import org.ei.drishti.util.FakeUserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.ei.drishti.domain.AlertFilterCriterionForTime.*;
import static org.ei.drishti.domain.AlertFilterCriterionForType.BCG;
import static org.ei.drishti.domain.AlertFilterCriterionForType.OPV;
import static org.ei.drishti.util.FakeContext.setupService;
import static org.ei.drishti.util.FakeDrishtiService.dataForCreateAction;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class FilterAlertTest extends ActivityInstrumentationTestCase2<AlertsActivity> {
    private DrishtiSolo solo;

    private String defaultSuffix;
    private FakeDrishtiService drishtiService;

    public FilterAlertTest() {
        super(AlertsActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);

        drishtiService = new FakeDrishtiService(defaultSuffix);
        Response<List<Action>> actionsToUse = drishtiService.actionsFor(defaultSuffix);
        Action alertActionForToday = new Action("Case M", "alert", "createAlert", dataForCreateAction("Mom " + defaultSuffix, "Bherya 1",
                "Sub Center", "PHC X", "TC 12", "ANC 1", "due", new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.today())), "1234567");
        actionsToUse.payload().add(alertActionForToday);
        drishtiService.changeDefaultActions(actionsToUse);

        setupService(drishtiService, new FakeUserService(), 1000000).updateApplicationContext(getActivity().getApplicationContext());

        solo = new DrishtiSolo(getInstrumentation(), getActivity());
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix, "Mom " + defaultSuffix);
    }

    public void testShouldFilterListByMotherName() throws Exception {
        solo.filterByText("Theresa 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix, "Mom " + defaultSuffix);

        solo.filterByText("theresa 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);
    }

    public void testShouldFilterListByThaayiCardNumber() throws Exception {
        solo.filterByText("Thaayi 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByText("X");
        solo.assertEmptyList();

        solo.clearEditText(0);
        waitForFilteringToFinish();
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix, "Mom " + defaultSuffix);

        solo.filterByText("thaayi 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnTypeDialog() throws Exception {
        solo.filterByType(BCG);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByType(OPV);
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);
    }

    public void testShouldFilterListBasedOnBothTypeFilterValueAndTextFilter() throws Exception {
        solo.filterByType(BCG);
        solo.filterByText("Thaayi 2");
        solo.assertEmptyList();

        solo.filterByType(OPV);
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);
    }

    public void testShouldApplyFilterAfterUpdation() throws Exception {
        String newSuffix = String.valueOf(new Date().getTime());

        solo.filterByType(BCG);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        drishtiService.setSuffix(newSuffix);

        solo.updateUsingPullToRefresh();

        solo.assertNamesInAlerts("Theresa 1 " + newSuffix);
    }

    public void testShouldFilterListBasedOnLocation() throws Exception {
        solo.filterByLocation("Bherya 1");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Mom " + defaultSuffix);

        solo.filterByLocation("Bherya 2");
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix);

        solo.filterByLocation("All");
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix, "Mom " + defaultSuffix);
    }

    public void testShouldFilterByTimeAndShowAlertsForTodayInUpcomingFilteredValues() throws Exception {
        solo.filterByTime(Upcoming);
        solo.assertNamesInAlerts("Theresa 2 " + defaultSuffix, "Mom " + defaultSuffix);

        solo.filterByTime(PastDue);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix);

        solo.filterByTime(ShowAll);
        solo.assertNamesInAlerts("Theresa 1 " + defaultSuffix, "Theresa 2 " + defaultSuffix, "Mom " + defaultSuffix);
    }

    @Override
    public void tearDown() throws Exception {
        waitForFilteringToFinish();
        waitForProgressBarToGoAway(getActivity());
        solo.finishOpenedActivities();
    }
}
