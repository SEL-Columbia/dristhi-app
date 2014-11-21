package org.ei.drishti.view.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.ClientProfileView;

public class NativeANCSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;

    private final TextView btnAncVisitView;
    private final View layoutANCVisitAlert;
    private final TextView txtANCVisitDoneOn;
    private final TextView txtANCVisitDueType;
    private final TextView txtANCVisitAlertDueOn;
    private final ViewGroup serviceModeViewsHolder;
    private final ViewGroup serviceModeOverviewView;

    public NativeANCSmartRegisterViewHolder(ViewGroup itemView) {
        profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        profileInfoLayout.initialize();

        serviceModeViewsHolder = (ViewGroup) itemView.findViewById(R.id.anc_register_service_mode_options_view);
        serviceModeOverviewView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.overview_service_mode_views);

        btnAncVisitView = (TextView) serviceModeOverviewView.findViewById(R.id.btn_anc_visit);
        layoutANCVisitAlert = serviceModeOverviewView.findViewById(R.id.layout_anc_visit_alert);
        txtANCVisitDoneOn = (TextView) serviceModeOverviewView.findViewById(R.id.txt_anc_visit_on);
        txtANCVisitDueType = (TextView) serviceModeOverviewView.findViewById(R.id.txt_anc_visit_due_type);
        txtANCVisitAlertDueOn = (TextView) serviceModeOverviewView.findViewById(R.id.txt_anc_visit_due_on);
    }

    public ClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

    public TextView btnAncVisitView() {
        return btnAncVisitView;
    }

    public View layoutANCVisitAlert() {
        return layoutANCVisitAlert;
    }

    public TextView txtANCVisitDoneOn() {
        return txtANCVisitDoneOn;
    }

    public TextView txtANCVisitDueType() {
        return txtANCVisitDueType;
    }

    public TextView txtANCVisitAlertDueOn() {
        return txtANCVisitAlertDueOn;
    }
}