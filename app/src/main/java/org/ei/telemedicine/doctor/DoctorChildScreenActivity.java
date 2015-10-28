package org.ei.telemedicine.doctor;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.customControls.CustomFontTextView;

/**
 * Created by naveen on 6/20/15.
 */
public class DoctorChildScreenActivity extends DoctorPatientDetailSuperActivity implements View.OnClickListener {
    EditText et_mother_name, et_child_name, et_reporting_date, et_temperature, et_days_of_cough, et_breaths_per_minute, et_days_of_diarrhea, et_blood_in_stool, et_vommit_every_thing, et_number_of_days_fever;
    CustomFontTextView tv_child_disease_report, tv_breathing_problems, tv_child_dob, tv_days_of_cough;
    Button bt_poc, bt_doc_refer;
    String documentId = null, phoneNumber = null, visitId = null, entityId = null, wifeName = null, tempFormat = "", tempVal = "";
    TextView tv_temp_format;
    ImageButton ib_temp_graph;

    @Override
    protected String[] setDatatoViews(String formInfo) {
        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        phoneNumber = getDatafromJson(formInfo, DoctorFormDataConstants.phoneNumber);

        visitId = getDatafromJson(formInfo, DoctorFormDataConstants.pnc_entityId);
        entityId = getDatafromJson(formInfo, DoctorFormDataConstants.entityId);
        wifeName = getDatafromJson(formInfo, DoctorFormDataConstants.wife_name);

        et_mother_name.setText(getDatafromJson(formInfo, DoctorFormDataConstants.wife_name));
        et_child_name.setText(isBlank(getDatafromJson(formInfo, DoctorFormDataConstants.child_name)) ? "B/o " + getDatafromJson(formInfo, DoctorFormDataConstants.wife_name) : getDatafromJson(formInfo, DoctorFormDataConstants.child_name));
        et_reporting_date.setText(isBlank(getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_date)) ? getDatafromJson(formInfo, DoctorFormDataConstants.child_sick_visit_date) : getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_date));
        tv_child_disease_report.setText(childSigns(formInfo));
        tv_child_dob.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_dob));

        tv_days_of_cough.setVisibility(getDatafromJson(formInfo, DoctorFormDataConstants.child_days_of_cough).length() != 0 ? View.VISIBLE : View.GONE);
        et_days_of_cough.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_days_of_cough));

        et_breaths_per_minute.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_breaths_per_minute));
        et_days_of_diarrhea.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_days_of_diarrhea));
        et_blood_in_stool.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_blood_in_stool));
        et_vommit_every_thing.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_vommit_every_thing));
        et_number_of_days_fever.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_number_of_days_fever));

//        tv_referral.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_immediateReferral));
//        tv_referral_reason.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_immediateReferral_reason));

        String tempStr = getDatafromJson(formInfo, DoctorFormDataConstants.temp_data);
        Log.e("Teme", tempStr);
        if (!tempStr.equals("") && tempStr.contains("-")) {
            String[] temp = tempStr.split("-");
            tempFormat = temp[temp.length - 1];
            tempVal = temp[0];
        } else {
            tempVal = "Not Captured";
        }
        tv_temp_format.setText(tempFormat);
        et_temperature.setText(tempVal);

        return new String[]{documentId, phoneNumber, visitId};
    }

    private boolean isBlank(String str) {
        return str.trim().length() == 0;
    }

    private String childSigns(String formInfo) {
        if (getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_date).equals("")) {
            return getDatafromJson(formInfo, DoctorFormDataConstants.child_signs).trim().replace(" ", ",").replace("_", " ") + (!isBlank(getDatafromJson(formInfo, DoctorFormDataConstants.child_signs_other)) ? "\nOther:" + getDatafromJson(formInfo, DoctorFormDataConstants.child_signs_other) : "");
        } else
            return getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease).trim().replace(" ", ",").replace("_", " ") + (!isBlank(getDatafromJson(formInfo, DoctorFormDataConstants.child_signs_other)) ? "\nOther:" + getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_other) : "");
    }

    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_child_info);

        et_mother_name = (EditText) findViewById(R.id.et_mother_name);
        et_reporting_date = (EditText) findViewById(R.id.et_reporting_date);
        et_child_name = (EditText) findViewById(R.id.et_child_name);
        et_temperature = (EditText) findViewById(R.id.et_temperature);
        tv_temp_format = (TextView) findViewById(R.id.tv_temp_format);
        ib_temp_graph = (ImageButton) findViewById(R.id.ib_temp_graph);

        et_days_of_cough = (EditText) findViewById(R.id.et_days_of_cough);
        tv_days_of_cough = (CustomFontTextView) findViewById(R.id.tv_days_of_cough);
        et_breaths_per_minute = (EditText) findViewById(R.id.et_breaths_per_minute);
        et_days_of_diarrhea = (EditText) findViewById(R.id.et_days_of_diarrhea);
        et_blood_in_stool = (EditText) findViewById(R.id.et_blood_in_stool);
        et_vommit_every_thing = (EditText) findViewById(R.id.et_vommit_every_thing);
        et_number_of_days_fever = (EditText) findViewById(R.id.et_number_of_days_fever);

        tv_child_disease_report = (CustomFontTextView) findViewById(R.id.tv_child_disease_report);
        tv_child_dob = (CustomFontTextView) findViewById(R.id.tv_child_dob);
        bt_poc = (Button) findViewById(R.id.bt_plan_of_care);
        bt_poc.setOnClickListener(this);
        bt_doc_refer = (Button) findViewById(R.id.bt_refer);
        bt_doc_refer.setOnClickListener(this);
//        tv_referral = (CustomFontTextView) findViewById(R.id.tv_referral);
//        tv_referral_reason = (CustomFontTextView) findViewById(R.id.tv_referral_reason);
        ib_temp_graph.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_plan_of_care:
                getDrugData();
                break;
            case R.id.ib_temp_graph:
                getVitalsData(AllConstants.GraphFields.BP, visitId);
                break;
            case R.id.bt_refer:
                referAnotherDoctor(Context.getInstance().allSharedPreferences().fetchRegisteredANM(), visitId, entityId, documentId, "CHILD", wifeName);
                break;
        }
    }
}
