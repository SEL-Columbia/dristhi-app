package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.Spinner;
import org.ei.drishti.R;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.service.DrishtiService;

import java.util.Date;

import static android.view.KeyEvent.KEYCODE_MENU;
import static org.ei.drishti.adapter.AlertFilterCriterion.*;
import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class DrishtiMainActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {

    private final FakeDrishtiService drishtiService;

    public DrishtiMainActivityTest() {
        super(DrishtiMainActivity.class);
        drishtiService = new FakeDrishtiService();
    }

    public void testShouldLoadOnStartup() throws Throwable {
        final String suffix = String.valueOf(new Date().getTime());
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffix));

        final DrishtiMainActivity activity = getMainActivity();

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertEquals(2, listView.getCount());

        Alert firstAlert = ((Alert) listView.getItemAtPosition(0));
        Alert secondAlert = ((Alert) listView.getItemAtPosition(1));

        assertEquals("Theresa 1 " + suffix, firstAlert.beneficiaryName());
        assertEquals("Theresa 2 " + suffix, secondAlert.beneficiaryName());
    }

    public void testShouldDisplayAlertsBasedOnFilterOptionSelected() throws Throwable {
        final String suffix = String.valueOf(new Date().getTime());
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffix));

        final DrishtiMainActivity activity = getMainActivity();

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertEquals(2, listView.getCount());

        setSpinnerTo(activity, 1);
        waitForProgressBarToGoAway(activity, 2000);

        assertEquals(1, listView.getCount());
    }

    public void testShouldPopulateSpinnerOptions() throws Throwable {
        final String suffix = String.valueOf(new Date().getTime());
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffix));

        final DrishtiMainActivity activity = getMainActivity();

        final Spinner spinner = (Spinner) activity.findViewById(R.id.filterSpinner);
        assertEquals(4, spinner.getCount());
        assertEquals(All, spinner.getItemAtPosition(0));
        assertEquals(ANC, spinner.getItemAtPosition(1));
        assertEquals(IFA, spinner.getItemAtPosition(2));
        assertEquals(TT, spinner.getItemAtPosition(3));
    }

    public void testShouldUpdateWhenUpdateButtonInMenuIsPressed() throws Throwable {
        final String suffixForLoadingDuringStartup = String.valueOf(new Date().getTime() - 1);
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffixForLoadingDuringStartup));
        final DrishtiMainActivity activity = getMainActivity();

        final String suffixForLoadingThroughMenuButton = String.valueOf(new Date().getTime());
        DrishtiMainActivity.setDrishtiService(fakeDrishtiService(suffixForLoadingThroughMenuButton));
        getInstrumentation().sendKeyDownUpSync(KEYCODE_MENU);
        getInstrumentation().invokeMenuActionSync(activity, R.id.updateMenuItem, 0);

        waitForProgressBarToGoAway(activity, 2000);

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        assertEquals(2, listView.getCount());

        Alert firstAlert = ((Alert) listView.getItemAtPosition(0));
        Alert secondAlert = ((Alert) listView.getItemAtPosition(1));

        assertEquals("Theresa 1 " + suffixForLoadingThroughMenuButton, firstAlert.beneficiaryName());
        assertEquals("Theresa 2 " + suffixForLoadingThroughMenuButton, secondAlert.beneficiaryName());
    }

    private DrishtiMainActivity getMainActivity() throws Throwable {
        final DrishtiMainActivity activity = getActivity();
        setSpinnerTo(activity, 0);
        waitForProgressBarToGoAway(activity, 2000);
        return activity;
    }

    private void setSpinnerTo(DrishtiMainActivity activity, final int position) throws Throwable {
        final Spinner spinner = (Spinner) activity.findViewById(R.id.filterSpinner);
        runTestOnUiThread(new Runnable(){
            public void run() {
                spinner.setSelection(position);
            }
        });
    }

    private DrishtiService fakeDrishtiService(String suffix) {
        drishtiService.setSuffix(suffix);
        return drishtiService;
    }

}