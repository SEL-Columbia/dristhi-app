package org.ei.telemedicine.view.customControls;

import org.apache.commons.lang3.StringUtils;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.ANCSmartRegisterClient;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ANCClientIdDetailsView extends RelativeLayout {
    private TextView txtThayiNumber;
    private TextView txtMotherEcNumber;
    private TextView lblThayiNumber;
    private TextView lblMotherANCNumber;

    @SuppressWarnings("UnusedDeclaration")
    public ANCClientIdDetailsView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public ANCClientIdDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ANCClientIdDetailsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize() {
        txtThayiNumber = (TextView) findViewById(R.id.txt_thayi_no);
        txtMotherEcNumber = (TextView) findViewById(R.id.txt_mother_ec_no);
        lblThayiNumber = (TextView) findViewById(R.id.lbl_thayi);
        lblMotherANCNumber = (TextView) findViewById(R.id.lbl_mother_ec);
    }

    public void bindData(ANCSmartRegisterClient client) {
        String thayiNumber = client.thayiCardNumber();
        String motherANCNumber = client.ancNumber();

        setupTextView(lblThayiNumber, txtThayiNumber, thayiNumber);
        setupTextView(lblMotherANCNumber, txtMotherEcNumber, motherANCNumber);
    }

    private void setupTextView(TextView lblView, TextView txtView, String thayiNumber) {
        if (StringUtils.isBlank(thayiNumber)) {
            lblView.setVisibility(GONE);
            txtView.setVisibility(GONE);
        } else {
            lblView.setVisibility(VISIBLE);
            txtView.setVisibility(VISIBLE);
            txtView.setText(thayiNumber);
        }
    }
}
