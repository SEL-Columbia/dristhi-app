package org.ei.drishti.view.customControls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.view.contract.AlertDTO;
import org.ei.drishti.view.contract.FPSmartRegisterClient;

import java.util.List;

public class ClientSideEffectsView extends LinearLayout {
    private TextView complicationsDateView;
    private TextView sideEffectsView;

    @SuppressWarnings("UnusedDeclaration")
    public ClientSideEffectsView(Context context) {
        this(context, null, 0);
    }

    @SuppressWarnings("UnusedDeclaration")
    public ClientSideEffectsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClientSideEffectsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize() {
        complicationsDateView = (TextView) findViewById(R.id.txt_complication_date);
        sideEffectsView = (TextView) findViewById(R.id.txt_side_effects);
    }

    public void bindData(FPSmartRegisterClient client) {

        FPMethod fpMethod = client.fpMethod();

        if (client.complicationDate() == null) {
            complicationsDateView.setVisibility(View.GONE);
        }

        complicationsDateView.setText(client.complicationDate());

        if (fpMethod == FPMethod.NONE) {
            sideEffectsView.setVisibility(View.GONE);
        } else if (fpMethod == FPMethod.OCP) {
            sideEffectsView.setVisibility(View.VISIBLE);
            sideEffectsView.setText(client.ocpSideEffect());
        } else if (fpMethod == FPMethod.CONDOM) {
            sideEffectsView.setVisibility(View.VISIBLE);
            sideEffectsView.setText(client.condomSideEffect());
        } else if (fpMethod == FPMethod.IUD) {
            sideEffectsView.setText(client.iudSidEffect());
        } else if (fpMethod == FPMethod.FEMALE_STERILIZATION || fpMethod == FPMethod.MALE_STERILIZATION) {
            sideEffectsView.setText(client.sterilizationSideEffect());
        } else if (fpMethod == FPMethod.DMPA) {
            sideEffectsView.setText(client.injectableSideEffect());
        } else {
            sideEffectsView.setText(client.otherSideEffect());
        }

        List<AlertDTO> alerts = client.alerts();
        for (AlertDTO alert : alerts) {
            if (alert.name().equals("FP Referral Followup"))
                sideEffectsView.setText("Referred");
        }


    }

}
