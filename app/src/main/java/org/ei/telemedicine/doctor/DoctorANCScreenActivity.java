package org.ei.telemedicine.doctor;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
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

import static org.ei.telemedicine.doctor.DoctorFormDataConstants.anmPoc;

public class DoctorANCScreenActivity extends DoctorPatientDetailSuperActivity {
    Button bt_poc, bt_doc_refer;
    ProgressDialog progressDialog;
    static String resultData;
    private String TAG = "DoctorANCSCreenActivity";
    TextView tv_stehoscope_title, tv_temp_format, tv_anm_poc;
    ImageButton ib_bp_graph, ib_fetal_graph, ib_bgm_graph, ib_temp_graph;
    EditText et_anc_num, et_woman_name, et_anc_visit_date, et_other_risks, et_bp_sys, et_bp_dia, et_temp, et_bloodGlucose, et_fetal, et_bp_pulse;
    ImageButton ib_play_stehoscope, ib_pause_stehoscope;
    LinearLayout ll_woman_risks;
    String documentId = null, visitId = null, phoneNumber = null, entityId = null, wifeName = null, tempFormat = "", tempVal = "";
    String formData = null, pstechoscopeData = "";
    org.ei.telemedicine.view.customControls.CustomFontTextView tv_risks;

    @Override
    protected String[] setDatatoViews(String formInfo) {
        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        phoneNumber = getDatafromJson(formInfo, DoctorFormDataConstants.phoneNumber);
        visitId = getDatafromJson(formInfo, DoctorFormDataConstants.anc_entityId);
        entityId = getDatafromJson(formInfo, DoctorFormDataConstants.entityId);
        wifeName = getDatafromJson(formInfo, DoctorFormDataConstants.wife_name);
        pstechoscopeData = getDatafromJson(formInfo, DoctorFormDataConstants.stethoscope_data);
        et_woman_name.setText(wifeName);
        et_anc_num.setText("Visit No " + getDatafromJson(formInfo, DoctorFormDataConstants.anc_visit_number));
        et_anc_visit_date.setText(getDatafromJson(formInfo, DoctorFormDataConstants.anc_visit_date));
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
        et_bloodGlucose.setText(getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.blood_glucose));
        et_fetal.setText(getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data).equals("") ? "Not captured" : getDatafromJson(formInfo, DoctorFormDataConstants.fetal_data));

        ib_play_stehoscope.setVisibility(!pstechoscopeData.equals("") ? View.VISIBLE : View.GONE);
        tv_stehoscope_title.setVisibility(!pstechoscopeData.equals("") ? View.VISIBLE : View.GONE);

        String risks = getDatafromJson(formInfo, DoctorFormDataConstants.risk_symptoms);
        tv_risks.setText(risks.replace(" ", ", "));
        tv_anm_poc.setText(getDatafromJsonArray(getDatafromJson(formInfo, anmPoc)));
        return new String[]{documentId, phoneNumber, visitId};

    }

    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_anc_info);
        ib_bp_graph = (ImageButton) findViewById(R.id.ib_bp_graph);
        ib_temp_graph = (ImageButton) findViewById(R.id.ib_temp_graph);
        ib_bgm_graph = (ImageButton) findViewById(R.id.ib_bgm_graph);
        ib_fetal_graph = (ImageButton) findViewById(R.id.ib_fetal_graph);

        ib_play_stehoscope = (ImageButton) findViewById(R.id.ib_play_stehoscope);
        ib_pause_stehoscope = (ImageButton) findViewById(R.id.ib_pause_stehoscope);

        et_anc_num = (EditText) findViewById(R.id.et_anc_num);
        tv_anm_poc = (CustomFontTextView) findViewById(R.id.tv_anm_poc);
        et_woman_name = (EditText) findViewById(R.id.et_woman_name);
        et_anc_visit_date = (EditText) findViewById(R.id.et_anc_visit_date);
        et_other_risks = (EditText) findViewById(R.id.et_other_risk_symptoms);
        et_bp_sys = (EditText) findViewById(R.id.et_sysstolic);
        et_bp_dia = (EditText) findViewById(R.id.et_diastolic);
        et_bp_pulse = (EditText) findViewById(R.id.et_pulse);
        et_temp = (EditText) findViewById(R.id.et_temperature);
        et_bloodGlucose = (EditText) findViewById(R.id.et_blood_glucose);
        et_fetal = (EditText) findViewById(R.id.et_fetal_movement);
        tv_risks = (CustomFontTextView) findViewById(R.id.tv_risks);
        bt_poc = (Button) findViewById(R.id.bt_plan_of_care);
        ll_woman_risks = (LinearLayout) findViewById(R.id.ll_risks);
        bt_doc_refer = (Button) findViewById(R.id.bt_refer);
        tv_stehoscope_title = (TextView) findViewById(R.id.tv_stehoscope_title);
        tv_temp_format = (TextView) findViewById(R.id.tv_temp_format);
        bt_doc_refer.setOnClickListener(this);
        bt_poc.setOnClickListener(this);

        ib_play_stehoscope.setOnClickListener(this);
        ib_pause_stehoscope.setOnClickListener(this);

        ib_bp_graph.setOnClickListener(this);
        ib_bgm_graph.setOnClickListener(this);
        ib_temp_graph.setOnClickListener(this);
        ib_fetal_graph.setOnClickListener(this);

    }
//
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
                if (pstechoscopeData.trim().length() != 0) {
                    String audioUrl = Context.getInstance().configuration().drishtiAudioURL() + pstechoscopeData.replace("\"", "");
                    Log.e("Psteg", audioUrl);
                    playData(audioUrl, ib_play_stehoscope, ib_pause_stehoscope);
                    ib_pause_stehoscope.setVisibility(View.VISIBLE);
                    ib_play_stehoscope.setVisibility(View.INVISIBLE);

                }
                break;
            case R.id.ib_pause_stehoscope:
                ib_pause_stehoscope.setVisibility(View.INVISIBLE);
                ib_play_stehoscope.setVisibility(View.VISIBLE);
                pausePlay();
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
                referAnotherDoctor(Context.getInstance().allSharedPreferences().fetchRegisteredANM(), visitId, entityId, documentId, "ANC", wifeName);
                break;
        }
    }


}
