package org.ei.telemedicine.view.customControls;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.ECSmartRegisterClient;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClientGplsaView extends RelativeLayout {
    private TextView txtGravida;
    private TextView txtParity;
    private TextView txtNumberOfLivingChildren;
    private TextView txtNumberOfStillBirths;
    private TextView txtNumberOfAbortions;

    @SuppressWarnings("UnusedDeclaration")
    public ClientGplsaView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public ClientGplsaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClientGplsaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize() {
        txtGravida = (TextView) findViewById(R.id.txt_gravida);
        txtParity = (TextView) findViewById(R.id.txt_parity);
        txtNumberOfLivingChildren = (TextView) findViewById(R.id.txt_number_of_living_children);
        txtNumberOfStillBirths = (TextView) findViewById(R.id.txt_number_of_still_births);
        txtNumberOfAbortions = (TextView) findViewById(R.id.txt_number_of_abortions);
    }

    public void bindData(ECSmartRegisterClient client) {
        txtGravida.setText(client.numberOfPregnancies());
        txtParity.setText(client.parity());
        txtNumberOfLivingChildren.setText(client.numberOfLivingChildren());
        txtNumberOfStillBirths.setText(client.numberOfStillbirths());
        txtNumberOfAbortions.setText(client.numberOfAbortions());
    }
}
