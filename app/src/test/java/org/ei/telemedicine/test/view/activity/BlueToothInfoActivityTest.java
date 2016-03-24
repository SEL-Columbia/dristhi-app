//package org.ei.telemedicine.test.view.activity;
//
//import android.test.ActivityInstrumentationTestCase2;
//
//import org.ei.telemedicine.bluetooth.BlueToothInfoActivity;
//import org.ei.telemedicine.test.util.DrishtiSolo;
//import org.ei.telemedicine.test.util.FakeDrishtiService;
//import org.ei.telemedicine.test.util.FakeUserService;
//import org.junit.Test;
//
//import java.util.Date;
//
//import static org.ei.telemedicine.test.util.FakeContext.setupService;
//import static org.ei.telemedicine.test.util.Wait.waitForFilteringToFinish;
//import static org.ei.telemedicine.test.util.Wait.waitForProgressBarToGoAway;
//
//
//public class BlueToothInfoActivityTest extends ActivityInstrumentationTestCase2<BlueToothInfoActivity> {
//
//    private DrishtiSolo solo;
//    private FakeUserService userService;
//
//    public BlueToothInfoActivityTest() {
//        super(BlueToothInfoActivity.class);
//    }
//
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        FakeDrishtiService drishtiService = new FakeDrishtiService(String.valueOf(new Date().getTime() - 1));
//        userService = new FakeUserService();
//
//        setupService(drishtiService, userService, 100000).updateApplicationContext(getActivity());
//
//        solo = new DrishtiSolo(getInstrumentation(), getActivity());
//    }
//
//    @Test
//    public void checkBpInfo() throws Exception {
//        String str = "Heelo";
//        assertEquals("Heelo", str);
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        waitForFilteringToFinish();
//        waitForProgressBarToGoAway(getActivity());
//        solo.finishOpenedActivities();
//    }
//}
