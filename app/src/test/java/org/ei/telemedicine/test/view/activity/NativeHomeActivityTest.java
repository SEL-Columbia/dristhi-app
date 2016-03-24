//package org.ei.telemedicine.test.view.activity;
//
//import org.ei.telemedicine.R;
//import org.ei.telemedicine.view.activity.NativeANCSmartRegisterActivity;
//import org.ei.telemedicine.view.activity.NativeChildSmartRegisterActivity;
//import org.ei.telemedicine.view.activity.NativeECSmartRegisterActivity;
//import org.ei.telemedicine.view.activity.NativeFPSmartRegisterActivity;
//import org.ei.telemedicine.view.activity.NativeHomeActivity;
//import org.ei.telemedicine.view.activity.NativePNCSmartRegisterActivity;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.shadows.ShadowActivity;
//
//import static org.junit.Assert.assertEquals;
//import static org.robolectric.Shadows.shadowOf;
//
//@RunWith(RobolectricTestRunner.class)
//public class NativeHomeActivityTest {
//
//    private NativeHomeActivity homeActivity;
//
//    @Before
//    public void setup() {
//        homeActivity = Robolectric.setupActivity(NativeHomeActivity.class);
//    }
//
//    @Test
//    public void shouldLaunchEcRegisterOnPressingEcRegisterButton() {
//        verifyLaunchOfActivityOnPressingButton(R.id.btn_ec_register, NativeECSmartRegisterActivity.class);
//    }
//
//    @Test
//    public void shouldLaunchAncRegisterOnPressingAncRegisterButton() {
//        verifyLaunchOfActivityOnPressingButton(R.id.btn_anc_register, NativeANCSmartRegisterActivity.class);
//    }
//
//    @Test
//    public void shouldLaunchPncRegisterOnPressingPncRegisterButton() {
//        verifyLaunchOfActivityOnPressingButton(R.id.btn_pnc_register, NativePNCSmartRegisterActivity.class);
//    }
//
//    @Test
//    public void shouldLaunchFpRegisterOnPressingFpRegisterButton() {
//        verifyLaunchOfActivityOnPressingButton(R.id.btn_fp_register, NativeFPSmartRegisterActivity.class);
//    }
//
//    @Test
//    public void shouldLaunchChildRegisterOnPressingChildRegisterButton() {
//        verifyLaunchOfActivityOnPressingButton(R.id.btn_child_register, NativeChildSmartRegisterActivity.class);
//
//    }
//
//
//
//    public <T> void verifyLaunchOfActivityOnPressingButton(int buttonId, Class<T> clazz) {
//        ShadowActivity shadowHome = shadowOf(homeActivity);
//
//        homeActivity.findViewById(buttonId).performClick();
//
//        assertEquals(clazz.getName(),
//                shadowHome.getNextStartedActivity().getComponent().getClassName());
//    }
//
//
//}