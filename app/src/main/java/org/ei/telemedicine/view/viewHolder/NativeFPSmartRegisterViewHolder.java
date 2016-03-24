package org.ei.telemedicine.view.viewHolder;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.customControls.ClientFpMethodView;
import org.ei.telemedicine.view.customControls.ClientGplsaChildView;
import org.ei.telemedicine.view.customControls.ClientProfileView;
import org.ei.telemedicine.view.customControls.ClientSideEffectsView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NativeFPSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;
    private final TextView txtECNumberView;
    private final ClientGplsaChildView gplsaChildLayout;
    private final ViewGroup serviceModeFPMethodView;
    private final ViewGroup serviceModeFPPrioritizationView;
    private final ClientFpMethodView fpMethodview;
    private final Button btnUpdateView;
    private final ClientSideEffectsView clientSideEffectsView;
    private final Button btnSideEffectsView;
    private final LinearLayout fpAlertLayout;
    private final TextView txtAlertTypeView;
    private final TextView txtAlertDateView;
    private final TextView txtPrioritizationRisksView;
    private final LinearLayout lytAddFPView;
//    private final LinearLayout lytFPVideosView;


    public NativeFPSmartRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        this.profileInfoLayout.initialize();

        this.txtECNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);

        this.gplsaChildLayout = (ClientGplsaChildView) itemView.findViewById(R.id.gplsa_child_layout);
        this.gplsaChildLayout.initialize();

        this.serviceModeFPMethodView = ((ViewGroup) itemView.findViewById(R.id.fp_method_service_mode_views));
        this.serviceModeFPPrioritizationView = ((ViewGroup) itemView.findViewById(R.id.fp_prioritization_service_mode_views));

        fpMethodview = (ClientFpMethodView) serviceModeFPMethodView.findViewById(R.id.fp_method_layout);
        fpMethodview.initialize();
        fpMethodview.setLayoutParams();

        btnUpdateView = (Button) serviceModeFPMethodView.findViewById(R.id.btn_fp_method_update);

        clientSideEffectsView = (ClientSideEffectsView) serviceModeFPMethodView.findViewById(R.id.side_effects_layout);

        clientSideEffectsView.initialize();

        btnSideEffectsView = (Button) serviceModeFPMethodView.findViewById(R.id.btn_side_effects);

        fpAlertLayout = (LinearLayout) serviceModeFPMethodView.findViewById(R.id.fp_alert_layout);

        txtAlertTypeView = (TextView) serviceModeFPMethodView.findViewById(R.id.txt_fp_alert_type);

        txtAlertDateView = (TextView) serviceModeFPMethodView.findViewById(R.id.txt_fp_alert_date);

        txtPrioritizationRisksView = (TextView) serviceModeFPPrioritizationView.findViewById(R.id.txt_fp_prioritization_risks);

        lytAddFPView = (LinearLayout) serviceModeFPPrioritizationView.findViewById(R.id.lyt_fp_add);

//        lytFPVideosView = (LinearLayout) serviceModeFPPrioritizationView.findViewById(R.id.lyt_fp_videos);
    }

    public void hideAllServiceModeOptions() {
        serviceModeFPMethod().setVisibility(View.GONE);
        serviceModeFPPrioritization().setVisibility(View.GONE);
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

    public ViewGroup serviceModeFPMethod() {
        return serviceModeFPMethodView;
    }

    public ViewGroup serviceModeFPPrioritization() {
        return serviceModeFPPrioritizationView;
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

    public TextView txtPrioritizationRiskView() {
        return txtPrioritizationRisksView;
    }

    public LinearLayout lytAddFPView() {
        return lytAddFPView;
    }


}