package org.ei.telemedicine.test.doctor;


import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.EditText;

import org.ei.telemedicine.doctor.DoctorANCScreenActivity;
import org.ei.telemedicine.doctor.DoctorChildScreenActivity;
import org.ei.telemedicine.doctor.DoctorFormDataConstants;
import org.ei.telemedicine.R;
import org.json.JSONObject;

public class DoctorChildScreenActivityTest  extends ActivityUnitTestCase<DoctorChildScreenActivity> {

    DoctorChildScreenActivity doctorChildScreenActivity;
    EditText et_mother_name, et_child_name, et_reporting_date;

    String wife_name_string = "wife_name";
    String child_name_string = "child_name";
    String child_report_child_disease_date_string = "child_report_child_disease_date";

    Intent mLaunchIntent;

    public DoctorChildScreenActivityTest(){
        super(DoctorChildScreenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), DoctorChildScreenActivity.class);
        mLaunchIntent.putExtras(getBundleExtra());
        startActivity(mLaunchIntent, null, null);

        doctorChildScreenActivity = getActivity();
        setUpViews();


    }

    private void setUpViews() {
        et_mother_name = (EditText) doctorChildScreenActivity.findViewById(R.id.et_mother_name);
        et_reporting_date = (EditText) doctorChildScreenActivity.findViewById(R.id.et_reporting_date);
        et_child_name = (EditText) doctorChildScreenActivity.findViewById(R.id.et_child_name);
    }





    public Bundle getBundleExtra() {
        Bundle mBundle = new Bundle();
        JSONObject jsondata = new JSONObject();
        try {
            jsondata.put(DoctorFormDataConstants.wife_name, wife_name_string);
            jsondata.put(DoctorFormDataConstants.child_name, child_name_string);
            jsondata.put(DoctorFormDataConstants.child_report_child_disease_date, child_report_child_disease_date_string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mBundle.putString(DoctorFormDataConstants.formData, jsondata + "");
        return  mBundle;
    }

    @MediumTest
    public void testDataSetToViews(){
        assertEquals(et_mother_name.getText().toString(), wife_name_string);
        assertEquals(et_child_name.getText().toString(),child_name_string);
        assertEquals(et_reporting_date.getText().toString(),child_report_child_disease_date_string);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
