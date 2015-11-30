package org.ei.telemedicine.test.doctor;


import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.EditText;
import android.widget.TextView;

import org.ei.telemedicine.doctor.DoctorANCScreenActivity;
import org.ei.telemedicine.doctor.DoctorFormDataConstants;
import org.ei.telemedicine.doctor.DoctorPNCScreenActivity;
import org.ei.telemedicine.R;
import org.json.JSONObject;

public class DoctorPNCScreenActivityTest  extends ActivityUnitTestCase<DoctorPNCScreenActivity> {

    DoctorPNCScreenActivity doctorPNCScreenActivity;
    EditText et_pnc_num, et_woman_name, et_pnc_date, et_bp_sys, et_bp_dia, et_temp, et_blood_glucose, et_hb_level;
    TextView tv_difficuties, tv_vaginal_difficulties, tv_breast_difficulties, tv_kop_feel_hot, tv_urinating_problems, tv_abdominal_problems;


    String pnc_number_string = "pnc_number";
    String wife_name_string = "wife_name";
    String pnc_visit_date_string = "pnc_visit_date";
    String bp_sys_string = "bp_sys";
    String bp_dia_string = "bp_dia";
    String temp_data_string = "temp_data";
    String blood_glucose_string = "blood_glucose";
    String fetal_data_string = "fetal_data";
    String pnc_difficulties_string = "pnc_difficulties";
    String pnc_abdominal_problems_string = "pnc_abdominal_problems";
    String pnc_breast_problems_string = "pnc_breast_problems";
    String pnc_vaginal_problems_string = "pnc_vaginal_problems";
    String kopfeel_heat_or_chills_string = "kopfeel_heat_or_chills";
    String pnc_urinating_problems_string = "pnc_urinating_problems";

    Intent mLaunchIntent;

    public DoctorPNCScreenActivityTest() {
        super(DoctorPNCScreenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(), DoctorPNCScreenActivity.class);
        mLaunchIntent.putExtras(getBundleExtra());
        startActivity(mLaunchIntent, null, null);

        doctorPNCScreenActivity = getActivity();
        setUpViews();
    }

    private void setUpViews() {

        et_pnc_num = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_num);
        et_woman_name = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_woman_name);
        et_pnc_date = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_visit_date);
        et_bp_sys = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_sysstolic);
        et_bp_dia = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_diastolic);
        et_temp = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_temperature);
        et_blood_glucose = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_blood_glucose);
        et_hb_level = (EditText) doctorPNCScreenActivity.findViewById(R.id.et_pnc_hb_level);
        tv_difficuties = (TextView) doctorPNCScreenActivity.findViewById(R.id.tv_difficulties);
        tv_vaginal_difficulties = (TextView) doctorPNCScreenActivity.findViewById(R.id.tv_vaginal_problems);
        tv_breast_difficulties = (TextView) doctorPNCScreenActivity.findViewById(R.id.tv_breast_problems);
        tv_kop_feel_hot = (TextView) doctorPNCScreenActivity.findViewById(R.id.tv_kop_feel_hot);
        tv_urinating_problems = (TextView) doctorPNCScreenActivity.findViewById(R.id.tv_urinating_difficulties);
        tv_abdominal_problems = (TextView) doctorPNCScreenActivity.findViewById(R.id.tv_addominal_problems);
    }




        public Bundle getBundleExtra() {
            Bundle mBundle = new Bundle();
            JSONObject jsondata = new JSONObject();
            try {
                jsondata.put(DoctorFormDataConstants.pnc_number, pnc_number_string);
                jsondata.put(DoctorFormDataConstants.wife_name, wife_name_string);
                jsondata.put(DoctorFormDataConstants.pnc_visit_date, pnc_visit_date_string);
                jsondata.put(DoctorFormDataConstants.bp_sys, bp_sys_string);
                jsondata.put(DoctorFormDataConstants.bp_dia, bp_dia_string);
                jsondata.put(DoctorFormDataConstants.temp_data, temp_data_string);
                jsondata.put(DoctorFormDataConstants.blood_glucose, blood_glucose_string);
                jsondata.put(DoctorFormDataConstants.fetal_data, fetal_data_string);
                jsondata.put(DoctorFormDataConstants.pnc_difficulties, pnc_difficulties_string);
                jsondata.put(DoctorFormDataConstants.pnc_abdominal_problems, pnc_abdominal_problems_string);
                jsondata.put(DoctorFormDataConstants.pnc_breast_problems, pnc_breast_problems_string);
                jsondata.put(DoctorFormDataConstants.pnc_vaginal_problems, pnc_vaginal_problems_string);
                jsondata.put(DoctorFormDataConstants.kopfeel_heat_or_chills, kopfeel_heat_or_chills_string);
                jsondata.put(DoctorFormDataConstants.pnc_urinating_problems, pnc_urinating_problems_string);

            } catch (Exception e) {
                e.printStackTrace();
            }

            mBundle.putString(DoctorFormDataConstants.formData, jsondata + "");
            return  mBundle;
        }

    @MediumTest
    public void testDataSetToViews() {
        assertEquals(et_pnc_num.getText().toString(), pnc_number_string);
        assertEquals(et_woman_name.getText().toString(), wife_name_string);
        assertEquals(et_pnc_date.getText().toString(), pnc_visit_date_string);
        assertEquals(et_bp_sys.getText().toString(), bp_sys_string.equals("") ? "Not captured" : bp_sys_string);
        assertEquals(et_bp_dia.getText().toString(), bp_dia_string.equals("") ? "Not captured" : bp_dia_string);
        assertEquals(et_temp.getText().toString(), temp_data_string.equals("") ? "Not captured" : temp_data_string);
        assertEquals(et_blood_glucose.getText().toString(), blood_glucose_string.equals("") ? "Not captured" : blood_glucose_string);
        assertEquals(et_hb_level.getText().toString(), fetal_data_string.equals("") ? "Not captured" : fetal_data_string);
        assertEquals(tv_difficuties.getText().toString(), pnc_difficulties_string);
        assertEquals(tv_abdominal_problems.getText().toString(), pnc_abdominal_problems_string);
        assertEquals(tv_breast_difficulties.getText().toString(), pnc_breast_problems_string);
        assertEquals(tv_vaginal_difficulties.getText().toString(), pnc_vaginal_problems_string);
        assertEquals(tv_kop_feel_hot.getText().toString(), kopfeel_heat_or_chills_string);
        assertEquals(tv_urinating_problems.getText().toString(), pnc_urinating_problems_string);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
