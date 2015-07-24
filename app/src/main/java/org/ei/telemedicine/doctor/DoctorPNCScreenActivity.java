package org.ei.telemedicine.doctor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ei.telemedicine.R;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naveen on 6/18/15.
 */
public class DoctorPNCScreenActivity extends DoctorPatientDetailSuperActivity implements View.OnClickListener {
    EditText et_pnc_num, et_woman_name, et_pnc_date, et_bp_sys, et_bp_dia, et_temp, et_blood_glucose, et_hb_level;
    TextView tv_difficuties, tv_vaginal_difficulties, tv_breast_difficulties, tv_kop_feel_hot, tv_urinating_problems, tv_abdominal_problems;
    Button bt_plan_of_care;
    Bundle bundle;
    ImageButton ib_play_stehoscope;
    private String documentId;


    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_pnc_info);
        et_pnc_num = (EditText) findViewById(R.id.et_pnc_num);
        et_woman_name = (EditText) findViewById(R.id.et_woman_name);
        et_pnc_date = (EditText) findViewById(R.id.et_pnc_visit_date);
        et_bp_sys = (EditText) findViewById(R.id.et_pnc_sysstolic);
        et_bp_dia = (EditText) findViewById(R.id.et_pnc_diastolic);
        et_temp = (EditText) findViewById(R.id.et_pnc_temperature);
        et_blood_glucose = (EditText) findViewById(R.id.et_pnc_blood_glucose);
        et_hb_level = (EditText) findViewById(R.id.et_pnc_hb_level);

        tv_difficuties = (TextView) findViewById(R.id.tv_difficulties);
        tv_vaginal_difficulties = (TextView) findViewById(R.id.tv_vaginal_problems);
        tv_breast_difficulties = (TextView) findViewById(R.id.tv_breast_problems);
        tv_kop_feel_hot = (TextView) findViewById(R.id.tv_kop_feel_hot);
        tv_urinating_problems = (TextView) findViewById(R.id.tv_urinating_difficulties);
        tv_abdominal_problems = (TextView) findViewById(R.id.tv_addominal_problems);
        ib_play_stehoscope = (ImageButton) findViewById(R.id.ib_play_stehoscope);

        bt_plan_of_care = (Button) findViewById(R.id.bt_plan_of_care);
        bt_plan_of_care.setOnClickListener(this);
        ib_play_stehoscope.setOnClickListener(this);
    }

    @Override
    protected String setDatatoViews(String formInfo) {

        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        et_pnc_num.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_number));
        et_woman_name.setText(getDatafromJson(formInfo, DoctorFormDataConstants.wife_name));
        et_pnc_date.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_visit_date));
        ib_play_stehoscope = (ImageButton) findViewById(R.id.ib_play_stehoscope);

        et_bp_sys.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys));
        et_bp_dia.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia));
        et_temp.setText(getDatafromJson(formInfo, DoctorFormDataConstants.temp_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.temp_data));
        et_blood_glucose.setText(getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose));
        et_hb_level.setText(getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data));

        tv_difficuties.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_difficulties));
        tv_abdominal_problems.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_abdominal_problems));
        tv_breast_difficulties.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_breast_problems));
        tv_vaginal_difficulties.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_vaginal_problems));
        tv_kop_feel_hot.setText(getDatafromJson(formInfo, DoctorFormDataConstants.kopfeel_heat_or_chills));
        tv_urinating_problems.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_urinating_problems));

        return documentId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_play_stehoscope:
                playData("http://202.153.34.169/hs/sound_1.wav");
                break;
            case R.id.bt_plan_of_care:
                getDrugData();
                break;
        }

    }
}
