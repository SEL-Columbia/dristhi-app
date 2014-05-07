package org.ei.drishti.view.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.ClientIdDetailsView;
import org.ei.drishti.view.customControls.ClientProfileView;

public class NativeChildSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;
    private final ClientIdDetailsView idDetailsView;
    private final ImageButton editButton;
    private final TextView txtDobView;

    private final ViewGroup serviceModeViewsHolder;
    private final ViewGroup serviceModeOverviewView;
    private final ViewGroup serviceModeImmunization0to9View;
    private final ViewGroup serviceModeImmunization9PlusView;
    private final TextView txtLastServiceDate;
    private final TextView txtLastServiceName;
    private final TextView txtSickVisit;
    private final TextView lblIllness;
    private final TextView lblIllnessDate;
    private final TextView txtIllness;
    private final TextView txtIllnessDate;
    private final View sicknessDeailLayout;
    private final TextView txtBcgPendingView;
    private final View layoutBcgOn;
    private final TextView txtBcgDoneOn;
    private final TextView txtHepBDoneOn;
    private final TextView txtPentavDoneOn;
    private final TextView txtOpvDoneOn;
    private final TextView txtMeaslesDoneOn;
    private final TextView txtOpvBoosterDoneOn;
    private final TextView txtDptBoosterDoneOn;
    private final TextView txtVitaminADoneOn;

    public NativeChildSmartRegisterViewHolder(ViewGroup itemView) {
        profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        profileInfoLayout.initialize();

        idDetailsView = (ClientIdDetailsView) itemView.findViewById(R.id.client_id_details_layout);
        idDetailsView.initialize();

        serviceModeViewsHolder = (ViewGroup) itemView.findViewById(R.id.child_register_service_mode_options_view);
        serviceModeOverviewView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.overview_service_mode_views);
        serviceModeImmunization0to9View = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.immunization0to9_service_mode_views);
        serviceModeImmunization9PlusView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.immunization9plus_service_mode_views);

        txtDobView = (TextView) serviceModeOverviewView.findViewById(R.id.child_register_dob);
        editButton = (ImageButton) serviceModeOverviewView.findViewById(R.id.btn_edit);
        txtLastServiceDate = (TextView) serviceModeOverviewView.findViewById(R.id.last_service_date);
        txtLastServiceName = (TextView) serviceModeOverviewView.findViewById(R.id.last_service_name);
        txtSickVisit = (TextView) serviceModeOverviewView.findViewById(R.id.btn_sick_visit);
        sicknessDeailLayout = serviceModeOverviewView.findViewById(R.id.sick_details_layout);
        lblIllness = (TextView) sicknessDeailLayout.findViewById(R.id.lbl_illness);
        lblIllnessDate = (TextView) sicknessDeailLayout.findViewById(R.id.lbl_illness_date);
        txtIllness = (TextView) sicknessDeailLayout.findViewById(R.id.txt_illness);
        txtIllnessDate = (TextView) sicknessDeailLayout.findViewById(R.id.txt_illness_date);

        txtBcgPendingView = (TextView) serviceModeImmunization0to9View.findViewById(R.id.txt_bcg_pending);
        layoutBcgOn = serviceModeImmunization0to9View.findViewById(R.id.layout_bcg_on);
        txtBcgDoneOn = (TextView) layoutBcgOn.findViewById(R.id.txt_bcg_on);
        txtOpvDoneOn = (TextView) serviceModeImmunization0to9View.findViewById(R.id.txt_opv_on);
        txtHepBDoneOn = (TextView) serviceModeImmunization0to9View.findViewById(R.id.txt_hep_b_on);
        txtPentavDoneOn = (TextView) serviceModeImmunization0to9View.findViewById(R.id.txt_pentav_on);

        txtMeaslesDoneOn = (TextView) serviceModeImmunization9PlusView.findViewById(R.id.txt_measles_on);
        txtOpvBoosterDoneOn = (TextView) serviceModeImmunization9PlusView.findViewById(R.id.txt_opv_booster_on);
        txtDptBoosterDoneOn = (TextView) serviceModeImmunization9PlusView.findViewById(R.id.txt_dpt_booster_on);
        txtVitaminADoneOn = (TextView) serviceModeImmunization9PlusView.findViewById(R.id.txt_vitamin_a_on);
    }

    public ClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

    public ClientIdDetailsView idDetailsView() {
        return idDetailsView;
    }

    public TextView dobView() {
        return txtDobView;
    }

    public ImageButton editButton() {
        return editButton;
    }

    public ViewGroup serviceModeViewsHolder() {
        return serviceModeViewsHolder;
    }

    public ViewGroup serviceModeImmunization9PlusView() {
        return serviceModeImmunization9PlusView;
    }

    public ViewGroup serviceModeImmunization0to9View() {
        return serviceModeImmunization0to9View;
    }

    public ViewGroup serviceModeOverviewView() {
        return serviceModeOverviewView;
    }

    public void hideAllServiceModeOptions() {
        serviceModeOverviewView().setVisibility(View.GONE);
        serviceModeImmunization0to9View().setVisibility(View.GONE);
        serviceModeImmunization9PlusView().setVisibility(View.GONE);
    }

    public TextView lastServiceDateView() {
        return txtLastServiceDate;
    }

    public TextView lastServiceNameView() {
        return txtLastServiceName;
    }

    public TextView sickVisitView() {
        return txtSickVisit;
    }

    public TextView illnessLableView() {
        return lblIllness;
    }

    public TextView illnessDateLabelView() {
        return lblIllnessDate;
    }

    public TextView illnessView() {
        return txtIllness;
    }

    public TextView illnessDateView() {
        return txtIllnessDate;
    }

    public View sicknessDetailLayout() {
        return sicknessDeailLayout;
    }

    public TextView bcgPendingView() {
        return txtBcgPendingView;
    }

    public View bcgDoneLayout() {
        return layoutBcgOn;
    }

    public TextView bcgDoneOnView() {
        return txtBcgDoneOn;
    }

    public TextView hepBDoneOnView() {
        return txtHepBDoneOn;
    }

    public TextView pentavDoneOnView() {
        return txtPentavDoneOn;
    }

    public TextView vitaminADoneOnView() {
        return txtVitaminADoneOn;
    }

    public TextView dptBoosterDoneOnView() {
        return txtDptBoosterDoneOn;
    }

    public TextView opvBoosterDoneOnView() {
        return txtOpvBoosterDoneOn;
    }

    public TextView measlesDoneOnView() {
        return txtMeaslesDoneOn;
    }

    public TextView opvDoneOnView() {
        return txtOpvDoneOn;
    }
}