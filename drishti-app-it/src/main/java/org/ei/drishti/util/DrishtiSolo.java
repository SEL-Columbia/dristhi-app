package org.ei.drishti.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.Display;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.R;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.domain.AlertFilterCriterionForTime;
import org.ei.drishti.domain.AlertFilterCriterionForType;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.view.activity.AlertsActivity;
import org.ei.drishti.view.activity.LoginActivity;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.Wait.waitForFilteringToFinish;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class DrishtiSolo extends Solo {
    private Activity activity;
    private Instrumentation instrumentation;

    public DrishtiSolo(Instrumentation instrumentation, Activity activity) {
        super(instrumentation, activity);
        this.activity = activity;
        this.instrumentation = instrumentation;
        waitForProgressBarToGoAway(activity);
    }

    public ListView list() {
        return getCurrentListViews().get(0);
    }

    public void assertNamesInAlerts(String... beneficiaryNames) {
        ArrayList<String> names = new ArrayList<String>();
        int count = list().getCount() - 2;
        for (int i = 1; i <= count; i++) {
            names.add(((Alert) list().getItemAtPosition(i)).beneficiaryName());
        }

        assertEquals(asList(beneficiaryNames), names);
    }

    public void assertNamesInECs(String... wifeNames) {
        ArrayList<String> names = new ArrayList<String>();
        int count = list().getCount() - 2;

        for (int i = 1; i <= count; i++) {
            names.add(((EligibleCouple) list().getItemAtPosition(i)).wifeName());
        }

        assertEquals(asList(wifeNames), names);
    }

    public void assertEmptyList() {
        assertNamesInAlerts(new String[0]);
    }

    public void updateUsingMenuButton() {
        this.instrumentation.invokeMenuActionSync(activity, R.id.updateMenuItem, 0);
        waitForProgressBarToGoAway(activity);
    }

    public void updateUsingPullToRefresh() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        drag(0, 0, display.getHeight() / 2, (3 * display.getHeight()) / 4, 25);
        waitForProgressBarToGoAway(activity);
    }

    public void filterByText(String text) {
        enterText(0, text);
        waitForFilteringToFinish();
    }

    public void filterByType(AlertFilterCriterionForType type) {
        filter(1, type.visitCodePrefix());
    }

    public void filterByTime(AlertFilterCriterionForTime criterion) {
        filter(2, criterion.displayValue());
    }

    public void filterByLocation(String location) {
        filter(3, location);
    }

    public void switchActivity() {
        clickOnImageButton(0);
    }

    private void filter(int filterButtonIndex, String dialogSelectionItem) {
        clickOnImageButton(filterButtonIndex);
        clickOnText(dialogSelectionItem);
        waitForActivity(AlertsActivity.class.getSimpleName());
        waitForFilteringToFinish();
    }

    public DrishtiSolo assertCanLogin(String userName, String password) {
        enterText(0, userName);
        enterText(1, password);
        clickOnButton(0);
        waitForActivity(AlertsActivity.class.getSimpleName());
        waitForFilteringToFinish();
        return this;
    }

    public DrishtiSolo assertCannotLogin(String userName, String password) {
        enterText(0, userName);
        enterText(1, password);
        clickOnButton(0);
        waitForActivity(LoginActivity.class.getSimpleName());
        waitForFilteringToFinish();
        return this;
    }

    public void logout() {
        sendKey(MENU);
        clickOnText("Logout");
        waitForActivity(LoginActivity.class.getSimpleName());
    }
}
