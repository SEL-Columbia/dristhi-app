package org.ei.drishti.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.domain.Alert;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class DrishtiSolo extends Solo {
    private Activity activity;

    public DrishtiSolo(Instrumentation instrumentation, Activity activity) {
        super(instrumentation, activity);
        this.activity = activity;
    }

    public ListView list() {
        return getCurrentListViews().get(0);
    }

    public void assertBeneficiaryNames(String... beneficiaryNames) {
        ArrayList<String> names = new ArrayList<String>();
        int count = list().getCount();
        for (int i = 0; i < count; i++) {
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
    }

    public void updateAlerts() {
        sendKey(MENU);
        clickOnText("Update");
        waitForProgressBarToGoAway(activity, 2000);
    }
}
