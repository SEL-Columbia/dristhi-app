package org.ei.drishti.util;

import android.app.Activity;
import android.test.InstrumentationTestCase;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForTime;
import org.ei.drishti.view.activity.DrishtiMainActivity;

import java.util.ArrayList;

import static android.test.TouchUtils.dragQuarterScreenDown;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class DrishtiSolo extends Solo {
    private Activity activity;
    private InstrumentationTestCase instrumentationTestCase;

    public DrishtiSolo(InstrumentationTestCase instrumentationTestCase, Activity activity) {
        super(instrumentationTestCase.getInstrumentation(), activity);
        this.activity = activity;
        this.instrumentationTestCase = instrumentationTestCase;
        waitForProgressBarToGoAway(activity);
    }

    public ListView list() {
        return getCurrentListViews().get(0);
    }

    public void assertBeneficiaryNames(String... beneficiaryNames) {
        ArrayList<String> names = new ArrayList<String>();
        int count = list().getCount() - 2;
        for (int i = 1; i <= count; i++) {
            names.add(((Alert) list().getItemAtPosition(i)).beneficiaryName());
        }

        assertEquals(asList(beneficiaryNames), names);
    }

    public void assertEmptyList() {
        assertBeneficiaryNames(new String[0]);
    }

    public void changeUser(String anmId) {
        sendKey(MENU);
        clickOnText("Settings");
        clickOnText("ANM ID");
        clearEditText(0);
        enterText(0, anmId);
        clickOnButton("OK");
        goBack();
        waitForProgressBarToGoAway(activity);
    }

    public void updateAlertsUsingMenuButton() {
        sendKey(MENU);
        clickOnText("Update");
        waitForProgressBarToGoAway(activity);
    }

    public void updateAlertsUsingPullToRefresh() {
        dragQuarterScreenDown(instrumentationTestCase, activity);
        waitForProgressBarToGoAway(activity);
    }

    public void filterByText(String text) {
        enterText(0, text);
        waitForFilteringToFinish();
    }

    public void filterByType(String type) {
        clickOnImageButton(1);
        clickOnText(type);
        waitForActivity(DrishtiMainActivity.class.getSimpleName());
        waitForFilteringToFinish();
    }

    public void filterByTime(AlertFilterCriterionForTime criterion) {
        clickOnImageButton(2);
        clickOnText(criterion.toString());
        waitForActivity(DrishtiMainActivity.class.getSimpleName());
        waitForFilteringToFinish();
    }
}
