package org.ei.telemedicine.doctor;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.customControls.CustomFontTextView;

public class DoctorANCScreenActivity extends DoctorPatientDetailSuperActivity {
    Button bt_poc, bt_doc_refer;
    ProgressDialog progressDialog;
    static String resultData;
    private String TAG = "DoctorANCSCreenActivity";
    TextView tv_stehoscope_title;
    ImageButton ib_bp_graph, ib_fetal_graph, ib_bgm_graph, ib_temp_graph;
    EditText et_anc_num, et_woman_name, et_anc_visit_date, et_other_risks, et_bp_sys, et_bp_dia, et_temp, et_bloodGlucose, et_fetal;
    LinearLayout ll_woman_risks;
    String documentId = null, visitId = null, phoneNumber = null, entityId = null;
    ImageButton ib_play_stehoscope;
    String formData = null;
    org.ei.telemedicine.view.customControls.CustomFontTextView tv_risks;

    @Override
    protected String[] setDatatoViews(String formInfo) {
        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        phoneNumber = getDatafromJson(formInfo, DoctorFormDataConstants.phoneNumber);
        visitId = getDatafromJson(formInfo, DoctorFormDataConstants.anc_entityId);
        entityId = getDatafromJson(formInfo, DoctorFormDataConstants.entityId);
        et_anc_num.setText("Visit No " + getDatafromJson(formInfo, DoctorFormDataConstants.anc_visit_number));
        et_woman_name.setText(getDatafromJson(formInfo, DoctorFormDataConstants.wife_name));
        et_anc_visit_date.setText(getDatafromJson(formInfo, DoctorFormDataConstants.anc_visit_date));
        et_bp_sys.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_sys));
        et_bp_dia.setText(getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.bp_dia));
        et_temp.setText(getDatafromJson(formInfo, DoctorFormDataConstants.temp_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.temp_data));
        et_bloodGlucose.setText(getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose));
        et_fetal.setText(getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data));
        ib_play_stehoscope.setVisibility(!getDatafromJson(formInfo, DoctorFormDataConstants.stethoscope_data).equals("") ? View.VISIBLE : View.GONE);
        tv_stehoscope_title.setVisibility(!getDatafromJson(formInfo, DoctorFormDataConstants.stethoscope_data).equals("") ? View.VISIBLE : View.GONE);
        
        String risks = getDatafromJson(formInfo, DoctorFormDataConstants.risk_symptoms);
        tv_risks.setText(risks.replace(" ", ", "));
        return new String[]{documentId, phoneNumber};

    }

    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_anc_info);
        ib_bp_graph = (ImageButton) findViewById(R.id.ib_bp_graph);
        ib_temp_graph = (ImageButton) findViewById(R.id.ib_temp_graph);
        ib_bgm_graph = (ImageButton) findViewById(R.id.ib_bgm_graph);
        ib_fetal_graph = (ImageButton) findViewById(R.id.ib_fetal_graph);

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
        bt_doc_refer = (Button) findViewById(R.id.bt_refer);
        tv_stehoscope_title = (TextView) findViewById(R.id.tv_stehoscope_title);
        bt_doc_refer.setOnClickListener(this);
        bt_poc.setOnClickListener(this);

        ib_play_stehoscope.setOnClickListener(this);

        ib_bp_graph.setOnClickListener(this);
        ib_bgm_graph.setOnClickListener(this);
        ib_temp_graph.setOnClickListener(this);
        ib_fetal_graph.setOnClickListener(this);
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
            case R.id.ib_bp_graph:
                getVitalsData(AllConstants.GraphFields.BP, visitId);
                break;
            case R.id.ib_temp_graph:
                getVitalsData(AllConstants.GraphFields.TEMPERATURE, visitId);
                break;
            case R.id.ib_bgm_graph:
                getVitalsData(AllConstants.GraphFields.BLOODGLUCOSEDATA, visitId);
                break;
            case R.id.ib_fetal_graph:
                getVitalsData(AllConstants.GraphFields.FETALDATA, visitId);
                break;
            case R.id.bt_refer:
                referAnotherDoctor(Context.getInstance().allSharedPreferences().fetchRegisteredANM(), visitId, entityId);
                break;
        }
    }


}
