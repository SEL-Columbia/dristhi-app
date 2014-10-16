package org.ei.drishti.view.viewHolder;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.ClientFpMethodView;
import org.ei.drishti.view.customControls.ClientGplsaChildView;
import org.ei.drishti.view.customControls.ClientProfileView;
import org.ei.drishti.view.customControls.ClientSideEffectsView;

public class NativeFPSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;
    private final TextView txtECNumberView;
    private final ClientGplsaChildView gplsaChildLayout;
    private final ClientFpMethodView fpMethodview;
    private final Button btnUpdateView;
    private final ClientSideEffectsView clientSideEffectsView;
    private final Button btnSideEffectsView;
    private final LinearLayout fpAlertLayout;
    private final TextView txtAlertTypeView;
    private final TextView txtAlertDateView;


    public NativeFPSmartRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        this.profileInfoLayout.initialize();

        this.txtECNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);

        this.gplsaChildLayout = (ClientGplsaChildView) itemView.findViewById(R.id.gplsa_child_layout);
        this.gplsaChildLayout.initialize();

        fpMethodview = (ClientFpMethodView) itemView.findViewById(R.id.fp_method_layout);
        fpMethodview.initialize();
        fpMethodview.setLayoutParams();

        btnUpdateView = (Button) itemView.findViewById(R.id.btn_fp_method_update);

        clientSideEffectsView = (ClientSideEffectsView) itemView.findViewById(R.id.side_effects_layout);

        clientSideEffectsView.initialize();

        btnSideEffectsView = (Button) itemView.findViewById(R.id.btn_side_effects);

        fpAlertLayout = (LinearLayout) itemView.findViewById(R.id.fp_alert_layout);

        txtAlertTypeView = (TextView) itemView.findViewById(R.id.txt_fp_alert_type);

        txtAlertDateView = (TextView) itemView.findViewById(R.id.txt_fp_alert_date);
    }

    public ClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

    public TextView txtECNumberView() {
        return txtECNumberView;
    }

    public ClientGplsaChildView gplsaAndChildLayout() {
        return gplsaChildLayout;
    }

    public ClientFpMethodView fpMethodView() {
        return fpMethodview;
    }

    public Button btnUpdateView() {
        return btnUpdateView;
    }

    public ClientSideEffectsView clientSideEffectsView() {
        return clientSideEffectsView;
    }

    public Button btnSideEffectsView() {
        return btnSideEffectsView;
    }

    public LinearLayout fpAlertLayout() {
        return fpAlertLayout;
    }

    public TextView txtAlertTypeView() {
        return txtAlertTypeView;
    }

    public TextView txtAlertDateView() {
        return txtAlertDateView;
    }

}