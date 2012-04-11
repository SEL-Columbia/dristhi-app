package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import org.ei.drishti.R;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.service.DrishtiService;

import java.util.*;

import static android.view.KeyEvent.KEYCODE_MENU;

public class DrishtiMainActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {

    private final FakeDrishtiService drishtiService;

    public DrishtiMainActivityTest() {
        super(DrishtiMainActivity.class);
        drishtiService = new FakeDrishtiService();
    }

    public void testShouldLoadOnStartup() throws Throwable {
        final String suffix = String.valueOf(new Date().getTime());
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffix));

        final DrishtiMainActivity activity = getActivity();

        waitForProgressBarToGoAway();

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertEquals(2, listView.getCount());

        Alert firstAlert = ((Alert) listView.getItemAtPosition(0));
        Alert secondAlert = ((Alert) listView.getItemAtPosition(1));

        assertEquals("Theresa 1 " + suffix, firstAlert.beneficiaryName());
        assertEquals("Theresa 2 " + suffix, secondAlert.beneficiaryName());
    }

    public void testShouldUpdateWhenUpdateButtonInMenuIsPressed() throws Throwable {
        final String suffixForLoadingDuringStartup = String.valueOf(new Date().getTime() - 1);
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffixForLoadingDuringStartup));
        final DrishtiMainActivity activity = getActivity();

        waitForProgressBarToGoAway();

        final String suffixForLoadingThroughMenuButton = String.valueOf(new Date().getTime());
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffixForLoadingThroughMenuButton));
        getInstrumentation().sendKeyDownUpSync(KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(activity, R.id.updateMenuItem, 0);

        waitForProgressBarToGoAway();

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertEquals(2, listView.getCount());

        Alert firstAlert = ((Alert) listView.getItemAtPosition(0));
        Alert secondAlert = ((Alert) listView.getItemAtPosition(1));

        assertEquals("Theresa 1 " + suffixForLoadingThroughMenuButton, firstAlert.beneficiaryName());
        assertEquals("Theresa 2 " + suffixForLoadingThroughMenuButton, secondAlert.beneficiaryName());
    }

    private void waitForProgressBarToGoAway() throws InterruptedException {
        /* HACK! Get rid of this. */
        Thread.sleep(2000);
    }

    private DrishtiService fakeDrishtiService(String suffix) {
        drishtiService.setSuffix(suffix);
        return drishtiService;
    }

}