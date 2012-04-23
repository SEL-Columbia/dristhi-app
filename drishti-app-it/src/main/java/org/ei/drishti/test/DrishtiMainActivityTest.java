package org.ei.drishti.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import org.ei.drishti.activity.DrishtiMainActivity;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.service.DrishtiService;

import java.util.Date;

import static org.ei.drishti.util.Wait.waitForProgressBarToGoAway;

public class DrishtiMainActivityTest extends ActivityInstrumentationTestCase2<DrishtiMainActivity> {

    private FakeDrishtiService drishtiService;
    private Solo solo;
    private String defaultSuffix;

    public DrishtiMainActivityTest() {
        super(DrishtiMainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        defaultSuffix = String.valueOf(new Date().getTime() - 1);
        drishtiService = new FakeDrishtiService(defaultSuffix);
        DrishtiMainActivity.setDrishtiService(drishtiService);

        solo = new Solo(getInstrumentation(), getActivity());
        waitForProgressBarToGoAway(getActivity(), 2000);
    }

    public void testShouldLoadOnStartup() throws Throwable {
        ListView listView = solo.getCurrentListViews().get(0);

        assertEquals(2, listView.getCount());
        assertBeneficiaryName(0, "Theresa 1 " + defaultSuffix);
        assertBeneficiaryName(1, "Theresa 2 " + defaultSuffix);
    }

    public void testShouldUpdateWhenUpdateButtonInMenuIsPressed() throws Throwable {
        final String suffixForLoadingThroughMenuButton = String.valueOf(new Date().getTime());
        setupSuffix(suffixForLoadingThroughMenuButton);

        solo.sendKey(Solo.MENU);
        solo.clickOnText("Update");
        waitForProgressBarToGoAway(getActivity(), 2000);

        ListView listView = solo.getCurrentListViews().get(0);

        assertEquals(2, listView.getCount());
        assertBeneficiaryName(0, "Theresa 1 " + suffixForLoadingThroughMenuButton);
        assertBeneficiaryName(1, "Theresa 2 " + suffixForLoadingThroughMenuButton);
    }

    private DrishtiService setupSuffix(String suffix) {
        drishtiService.setSuffix(suffix);
        return drishtiService;
    }

    private void assertBeneficiaryName(int position, String expected) {
        Alert firstAlert = ((Alert) solo.getCurrentListViews().get(0).getItemAtPosition(position));
        assertEquals(expected, firstAlert.beneficiaryName());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}