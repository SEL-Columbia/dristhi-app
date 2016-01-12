package org.ei.telemedicine.test.doctor;


import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageButton;

import com.robotium.solo.Solo;

import org.ei.telemedicine.R;
import org.ei.telemedicine.doctor.DoctorPlanofCareActivity;

public class DoctorPlanofCareActivityTest extends ActivityInstrumentationTestCase2<DoctorPlanofCareActivity> {

    public String CALLER_URL="http://202.153.34.169:8004/call?id=%s&peer_id=%s";
    private DoctorPlanofCareActivity poc;
    private Solo solo;

    public DoctorPlanofCareActivityTest()
    {
       super(DoctorPlanofCareActivity.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void testBroswerCall() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(DoctorPlanofCareActivity.class.getName(), null, false);

        // open current activity.
        DoctorPlanofCareActivity planofCareActivity = getActivity();
        final ImageButton button = (ImageButton) planofCareActivity.findViewById(R.id.iv_anm_logo);
        planofCareActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                button.performClick();
            }
        });

        //testIfBrowserCallIsInitiated.
        String caller_url = String.format(CALLER_URL,"tony","goke");
        Uri url = Uri.parse(caller_url);
        Intent _broswer = new Intent(Intent.ACTION_VIEW,url);
        assertNotNull(_broswer);
    }


    public void testCurrentAcitivity() {
        solo.assertCurrentActivity("Check acitivity", DoctorPlanofCareActivity.class);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
