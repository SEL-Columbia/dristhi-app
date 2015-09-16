package org.ei.telemedicine.test.view.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.activity.FormActivity;
import org.ei.telemedicine.view.activity.NativeECSmartRegisterActivity;
import org.junit.Test;

public class NativeECSmartRegisterActivityTest extends ActivityInstrumentationTestCase2<NativeECSmartRegisterActivity> {

    NativeECSmartRegisterActivity ecActivity;

    public NativeECSmartRegisterActivityTest() {
        super(NativeECSmartRegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ecActivity = getActivity();
    }

    @Test
    public void pressingNewRegisterButtonShouldOpenECRegistrationFormActivity() {
        ecActivity.findViewById(R.id.register_client)
                .performClick();
        verifyLaunchOfActivityOnPressingButton(R.id.register_client, FormActivity.class);


    }


    public <T> void verifyLaunchOfActivityOnPressingButton(int buttonId, Class<T> clazz) {

        ecActivity = this.getActivity();
        Intent i = new Intent(ecActivity, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add a monitor before we start the activity
        Instrumentation.ActivityMonitor mGameActivityMonitor = new Instrumentation.ActivityMonitor(clazz.getName(), null, false);
        getInstrumentation().addMonitor(mGameActivityMonitor);

        ecActivity.startActivity(i);

        Activity activity = mGameActivityMonitor.waitForActivityWithTimeout(5 * 1000);
        assertNotNull("Activity was not started", activity);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
