package org.ei.drishti.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.domain.Alert;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

public class DrishtiSolo extends Solo {
    public DrishtiSolo(Instrumentation instrumentation, Activity activity) {
        super(instrumentation, activity);
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
}
