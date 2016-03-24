package org.ei.telemedicine.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.customControls.CustomFontTextView;

import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anmPoc;

/**
 * Created by naveen on 6/18/15.
 */
public class DoctorPNCScreenActivity extends DoctorPatientDetailSuperActivity {
    EditText et_pnc_num, et_woman_name, et_pnc_date, et_bp_sys, et_bp_dia, et_temp, et_blood_glucose, et_hb_level, et_bp_pulse;
    TextView tv_difficuties, tv_vaginal_difficulties, tv_breast_difficulties, tv_kop_feel_hot, tv_urinating_problems, tv_abdominal_problems, tv_stehoscope_title, tv_temp_format;
    Button bt_plan_of_care, bt_doc_refer;
    ImageButton ib_bp_graph, ib_fetal_graph, ib_bgm_graph, ib_temp_graph;
    Bundle bundle;
    ImageButton ib_play_stehoscope, ib_pause_stehoscope;
    private String documentId, visitId, phoneNumber, entityId = null, wifeName = null, tempFormat = "", tempVal = "";
    org.ei.telemedicine.view.customControls.CustomFontTextView tv_anm_poc;


    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_pnc_info);
        et_pnc_num = (EditText) findViewById(R.id.et_pnc_num);
        et_woman_name = (EditText) findViewById(R.id.et_woman_name);
        et_pnc_date = (EditText) findViewById(R.id.et_pnc_visit_date);
        et_bp_sys = (EditText) findViewById(R.id.et_pnc_sysstolic);
        et_bp_dia = (EditText) findViewById(R.id.et_pnc_diastolic);
        et_bp_pulse = (EditText) findViewById(R.id.et_pnc_pulse);

        et_temp = (EditText) findViewById(R.id.et_pnc_temperature);
        et_blood_glucose = (EditText) findViewById(R.id.et_pnc_blood_glucose);
        et_hb_level = (EditText) findViewById(R.id.et_pnc_hb_level);

        tv_stehoscope_title = (TextView) findViewById(R.id.tv_stehoscope_title);
        tv_difficuties = (TextView) findViewById(R.id.tv_difficulties);
        tv_vaginal_difficulties = (TextView) findViewById(R.id.tv_vaginal_problems);
        tv_breast_difficulties = (TextView) findViewById(R.id.tv_breast_problems);
        tv_kop_feel_hot = (TextView) findViewById(R.id.tv_kop_feel_hot);
        tv_urinating_problems = (TextView) findViewById(R.id.tv_urinating_difficulties);
        tv_abdominal_problems = (TextView) findViewById(R.id.tv_addominal_problems);
        tv_temp_format = (TextView) findViewById(R.id.tv_temp_format);

        ib_pause_stehoscope = (ImageButton) findViewById(R.id.ib_pause_stehoscope);
        ib_play_stehoscope = (ImageButton) findViewById(R.id.ib_play_stehoscope);
        ib_bp_graph = (ImageButton) findViewById(R.id.ib_bp_graph);
        ib_temp_graph = (ImageButton) findViewById(R.id.ib_temp_graph);
        ib_bgm_graph = (ImageButton) findViewById(R.id.ib_bgm_graph);

        bt_plan_of_care = (Button) findViewById(R.id.bt_plan_of_care);
        bt_plan_of_care.setOnClickListener(this);
        bt_doc_refer = (Button) findViewById(R.id.bt_refer);
        bt_doc_refer.setOnClickListener(this);
        tv_anm_poc = (CustomFontTextView) findViewById(R.id.tv_anm_poc);

        ib_play_stehoscope.setOnClickListener(this);
        ib_bp_graph.setOnClickListener(this);
        ib_bgm_graph.setOnClickListener(this);
        ib_temp_graph.setOnClickListener(this);
        ib_pause_stehoscope.setOnClickListener(this);

    }

    @Override
    protected String[] setDatatoViews(String formInfo) {
        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        phoneNumber = getDatafromJson(formInfo, DoctorFormDataConstants.phoneNumber);

        visitId = getDatafromJson(formInfo, DoctorFormDataConstants.pnc_entityId);
        entityId = getDatafromJson(formInfo, DoctorFormDataConstants.entityId);
        wifeName = getDatafromJson(formInfo, DoctorFormDataConstants.wife_name);
        et_pnc_num.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_number));
        et_woman_name.setText(wifeName);
        et_pnc_date.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_visit_date));
        ib_play_stehoscope = (ImageButton) findViewById(R.id.ib_play_stehoscope);


        et_bp_sys.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys));
        et_bp_dia.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia));
        et_bp_pulse.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_pulse).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_pulse));
        String tempStr = getDatafromJson(formInfo, DoctorFormDataConstants.temp_data);
        if (!tempStr.equals("") && tempStr.contains("-")) {
            String[] temp = tempStr.split("-");
            tempFormat = temp[temp.length - 1];
            tempVal = temp[0];
        } else {
            tempVal = "Not Captured";
        }
        tv_temp_format.setText(tempFormat);
        et_temp.setText(tempVal);

//        et_temp.setText(getDatafromJson(formInfo, DoctorFormDataConstants.temp_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.temp_data));
        et_blood_glucose.setText(getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose));
        et_hb_level.setText(getDatafromJson(formInfo, DoctorFormDataConstants.hb_level).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.hb_level));

        ib_play_stehoscope.setVisibility(!getDatafromJson(formInfo, DoctorFormDataConstants.stethoscope_data).equals("") ? View.VISIBLE : View.GONE);
        tv_stehoscope_title.setVisibility(!getDatafromJson(formInfo, DoctorFormDataConstants.stethoscope_data).equals("") ? View.VISIBLE : View.GONE);

        tv_difficuties.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_difficulties));
        tv_abdominal_problems.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_abdominal_problems));
        tv_breast_difficulties.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_breast_problems));
        tv_vaginal_difficulties.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_vaginal_problems));
        tv_kop_feel_hot.setText(getDatafromJson(formInfo, DoctorFormDataConstants.kopfeel_heat_or_chills));
        tv_urinating_problems.setText(getDatafromJson(formInfo, DoctorFormDataConstants.pnc_urinating_problems));
        tv_anm_poc.setText(getDatafromJsonArray(getDatafromJson(formInfo, anmPoc)));
        return new String[]{documentId, phoneNumber, visitId};
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (ib_play_stehoscope != null && ib_pause_stehoscope != null) {
//            ib_play_stehoscope.setVisibility(View.VISIBLE);
//            ib_pause_stehoscope.setVisibility(View.INVISIBLE);
//        }
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_play_stehoscope:
                playData("http://202.153.34.169/hs/sound_1.wav", ib_play_stehoscope, ib_pause_stehoscope);
                ib_pause_stehoscope.setVisibility(View.VISIBLE);
                ib_play_stehoscope.setVisibility(View.INVISIBLE);
                break;
            case R.id.ib_pause_stehoscope:
                pausePlay();
                ib_pause_stehoscope.setVisibility(View.INVISIBLE);
                ib_play_stehoscope.setVisibility(View.VISIBLE);

                break;

            case R.id.bt_plan_of_care:
                getDrugData();
                break;
            case R.id.ib_bp_graph:
                getVitalsData(AllConstants.GraphFields.BP, visitId);
                break;
            case R.id.ib_temp_graph:
                getVitalsData(AllConstants.GraphFields.TEMPERATURE, visitId);
                break;
            case R.id.ib_bgm_graph:
                getVitalsData(AllConstants.GraphFields.BLOODGLUCOSEDATA, visitId);
                break;
            case R.id.bt_refer:
                referAnotherDoctor(Context.getInstance().allSharedPreferences().fetchRegisteredANM(), visitId, entityId, documentId, "PNC", wifeName);
                break;
        }

    }
}
