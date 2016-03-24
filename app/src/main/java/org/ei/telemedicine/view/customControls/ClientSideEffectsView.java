package org.ei.telemedicine.view.customControls;

import static org.ei.telemedicine.Context.getInstance;

import org.ei.telemedicine.R;
import org.ei.telemedicine.domain.FPMethod;
import org.ei.telemedicine.view.contract.FPSmartRegisterClient;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        sideEffectsView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        complicationsDateView.setText(client.complicationDate());
        if (client.refillFollowUps() != null &&
                getInstance().getStringResource(R.string.str_referral).equalsIgnoreCase(client.refillFollowUps().type())) {
            sideEffectsView.setVisibility(View.VISIBLE);
            sideEffectsView.setText(getInstance().getStringResource(R.string.str_referred));
            sideEffectsView.setTextColor(getInstance().getColorResource(R.color.alert_urgent_red));
            sideEffectsView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_referral_warning), null, null, null);

        } else if (fpMethod == FPMethod.NONE) {
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
        } else if (fpMethod == FPMethod.DMPA_INJECTABLE) {
            sideEffectsView.setText(client.injectableSideEffect());
        } else {
            sideEffectsView.setText(client.otherSideEffect());
        }


    }

}
