package org.ei.telemedicine.doctor;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.customControls.CustomFontTextView;
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorANCScreenActivity extends DoctorPatientDetailSuperActivity implements View.OnClickListener {
    Button bt_poc;
    ProgressDialog progressDialog;
    static String resultData;
    private String TAG = "DoctorANCSCreenActivity";

    EditText et_anc_num, et_woman_name, et_anc_visit_date, et_other_risks, et_bp_sys, et_bp_dia, et_temp, et_bloodGlucose, et_fetal;
    LinearLayout ll_woman_risks;
    String documentId = null;
    ImageButton ib_play_stehoscope;
    String formData = null;
    org.ei.telemedicine.view.customControls.CustomFontTextView tv_risks;

    @Override
    protected String setDatatoViews(String formInfo) {
        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        et_anc_num.setText("Visit No " + getDatafromJson(formInfo, DoctorFormDataConstants.anc_visit_number));
        et_woman_name.setText(getDatafromJson(formInfo, DoctorFormDataConstants.wife_name));
        et_anc_visit_date.setText(getDatafromJson(formInfo, DoctorFormDataConstants.anc_visit_date));
        et_bp_sys.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys));
        et_bp_dia.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia));
        et_temp.setText(getDatafromJson(formInfo, DoctorFormDataConstants.temp_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.temp_data));
        et_bloodGlucose.setText(getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose));
        et_fetal.setText(getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data));
        String risks = getDatafromJson(formInfo, DoctorFormDataConstants.risk_symptoms);
        tv_risks.setText(risks.replace(" ", ", "));
        return documentId;

    }

    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_anc_info);
        ib_play_stehoscope = (ImageButton) findViewById(R.id.ib_play_stehoscope);
        et_anc_num = (EditText) findViewById(R.id.et_anc_num);
        et_woman_name = (EditText) findViewById(R.id.et_woman_name);
        et_anc_visit_date = (EditText) findViewById(R.id.et_anc_visit_date);
        et_other_risks = (EditText) findViewById(R.id.et_other_risk_symptoms);
        et_bp_sys = (EditText) findViewById(R.id.et_sysstolic);
        et_bp_dia = (EditText) findViewById(R.id.et_diastolic);
        et_temp = (EditText) findViewById(R.id.et_temperature);
        et_bloodGlucose = (EditText) findViewById(R.id.et_blood_glucose);
        et_fetal = (EditText) findViewById(R.id.et_fetal_movement);
        tv_risks = (CustomFontTextView) findViewById(R.id.tv_risks);
        bt_poc = (Button) findViewById(R.id.bt_plan_of_care);
        ll_woman_risks = (LinearLayout) findViewById(R.id.ll_risks);
        bt_poc.setOnClickListener(this);
        ib_play_stehoscope.setOnClickListener(this);
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
