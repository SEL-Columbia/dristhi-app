package org.ei.telemedicine.doctor;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.customControls.CustomFontTextView;

/**
 * Created by naveen on 6/20/15.
 */
public class DoctorChildScreenActivity extends DoctorPatientDetailSuperActivity implements View.OnClickListener {
    EditText et_mother_name, et_child_name, et_reporting_date;
    CustomFontTextView tv_child_disease_report, tv_breathing_problems, tv_child_dob, tv_referral, tv_referral_reason;
    Button bt_poc;
    String documentId = null;

    @Override
    protected String setDatatoViews(String formInfo) {
        documentId = getDatafromJson(formInfo, DoctorFormDataConstants.documentId);
        et_mother_name.setText(getDatafromJson(formInfo, DoctorFormDataConstants.wife_name));
        et_child_name.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_name));
        et_reporting_date.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_date));
        tv_child_disease_report.setText(childSigns(formInfo));
        tv_child_dob.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_dob));

        tv_referral.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_immediateReferral));
        tv_referral_reason.setText(getDatafromJson(formInfo, DoctorFormDataConstants.child_immediateReferral_reason));

        return documentId;
    }

    private String childSigns(String formInfo) {
        if (getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_date).equals("")) {
            return getDatafromJson(formInfo, DoctorFormDataConstants.child_signs) + "\nOther:" + getDatafromJson(formInfo, DoctorFormDataConstants.child_signs_other);
        } else
            return getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease) + "\nOther:" + getDatafromJson(formInfo, DoctorFormDataConstants.child_report_child_disease_other);
    }

    @Override
    protected void setupViews() {
        setContentView(R.layout.doctor_child_info);

        et_mother_name = (EditText) findViewById(R.id.et_mother_name);
        et_reporting_date = (EditText) findViewById(R.id.et_reporting_date);
        et_child_name = (EditText) findViewById(R.id.et_child_name);
        tv_child_disease_report = (CustomFontTextView) findViewById(R.id.tv_child_disease_report);
        tv_child_dob = (CustomFontTextView) findViewById(R.id.tv_child_dob);
        bt_poc = (Button) findViewById(R.id.bt_plan_of_care);
        bt_poc.setOnClickListener(this);
        tv_referral = (CustomFontTextView) findViewById(R.id.tv_referral);
        tv_referral_reason = (CustomFontTextView) findViewById(R.id.tv_referral_reason);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_plan_of_care:
                getDrugData();
                break;
        }
    }
}
