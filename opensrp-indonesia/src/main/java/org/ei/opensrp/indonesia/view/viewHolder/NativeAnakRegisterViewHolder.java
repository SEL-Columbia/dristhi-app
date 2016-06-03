package org.ei.opensrp.indonesia.view.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.customControls.BidanClientProfileView;
import org.ei.opensrp.indonesia.view.customControls.ClientAnakBirthStatusView;

/**
 * Created by Dimas Ciputra on 4/10/15.
 */
public class NativeAnakRegisterViewHolder {

    private final BidanClientProfileView profileViewLayout;
    private final ClientAnakBirthStatusView clientAnakBirthStatusView;
    private final ImageButton editButton;
    private final TextView txtIbuKiNo;

    private final ViewGroup serviceModeViewsHolder;
    private final ViewGroup serviceModeOverviewView;
    private final ViewGroup serviceModeImmunizationView;

    private final TextView txtLastServiceDate;
    private final TextView txtLastServiceName;

    // Overview Service Mode
    private final TextView txtVisitDate;
    private final TextView txtCurrentWeight;
    private final TextView birthPlace;
    private final TextView txtDobView;
    private final TextView txtBirthWeight;
    private final TextView txtBirthCondition;
    private final ImageView imdTrueIcon;
    private final ImageView immuniHBTrueIcon;
    private final ImageView vitKTrueIcon;
    private final ImageView slepMataTrueIcon;
    private final ImageView imdFalseIcon;
    private final ImageView immuniHBFalseIcon;
    private final ImageView vitKFalseIcon;
    private final ImageView slepMataFalseIcon;

    // Immunization
    private final ViewGroup layoutHb07;
    private final TextView txtHb07PendingView;
    private final View layoutHb07On;
    private final TextView txtHb07DoneOn;
    private final TextView txtHb07Done;

    private final ViewGroup layoutBcg;
    private final TextView txtBcgPendingView;
    private final View layoutBcgOn;
    private final TextView txtBcgDoneOn;
    private final TextView txtBcgDone;

    private final ViewGroup layoutDpt1;
    private final TextView txtDpt1PendingView;
    private final View layoutDpt1On;
    private final TextView txtDpt1DoneOn;
    private final TextView txtDpt1Done;

    private final ViewGroup layoutDpt2;
    private final TextView txtDpt2PendingView;
    private final View layoutDpt2On;
    private final TextView txtDpt2DoneOn;
    private final TextView txtDpt2Done;

    private final ViewGroup layoutDpt3;
    private final TextView txtDpt3PendingView;
    private final View layoutDpt3On;
    private final TextView txtDpt3DoneOn;
    private final TextView txtDpt3Done;

    private final ViewGroup layoutCampak;
    private final TextView txtCampakPendingView;
    private final View layoutCampakOn;
    private final TextView txtCampakDoneOn;
    private final TextView txtCampakDone;

    public NativeAnakRegisterViewHolder(ViewGroup itemView) {
        profileViewLayout = (BidanClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        profileViewLayout.initialize();

        txtLastServiceDate = (TextView) itemView.findViewById(R.id.anak_last_service_date);
        txtLastServiceName = (TextView) itemView.findViewById(R.id.anak_last_service_name);

        serviceModeViewsHolder = (ViewGroup) itemView.findViewById(R.id.anak_register_service_mode_options_view);
        serviceModeOverviewView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.overview_service_mode_views);
        serviceModeImmunizationView = (ViewGroup) serviceModeViewsHolder.findViewById(R.id.immunization_service_mode_views);

        clientAnakBirthStatusView = (ClientAnakBirthStatusView)
                serviceModeOverviewView.findViewById(R.id.client_anak_birth_status_layout);
        clientAnakBirthStatusView.initialize();

        editButton = (ImageButton) serviceModeOverviewView.findViewById(R.id.btn_edit);
        txtDobView = (TextView) serviceModeOverviewView.findViewById(R.id.anak_register_dob);
        txtIbuKiNo = (TextView) serviceModeOverviewView.findViewById(R.id.txt_ibu_ki_no);

        txtBirthWeight = (TextView) serviceModeOverviewView.findViewById(R.id.berat_lahir);
        txtBirthCondition = (TextView) serviceModeOverviewView.findViewById(R.id.tipe_lahir);

        birthPlace = (TextView) serviceModeOverviewView.findViewById(R.id.tempat_lahir);

        imdTrueIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_imd_yes);
        immuniHBTrueIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_immuni_hb_yes);
        vitKTrueIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_vit_k_yes);
        slepMataTrueIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_salep_mata_yes);

        imdFalseIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_imd_no);
        immuniHBFalseIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_immuni_hb_no);
        vitKFalseIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_vit_k_no);
        slepMataFalseIcon = (ImageView) serviceModeOverviewView.findViewById(R.id.icon_salep_mata_no);

        txtCurrentWeight = (TextView) serviceModeOverviewView.findViewById(R.id.txt_current_weight);
        txtVisitDate = (TextView) serviceModeOverviewView.findViewById(R.id.txt_visit_date);

        layoutHb07 = (ViewGroup) itemView.findViewById(R.id.layout_hb07);
        txtHb07PendingView = (TextView) layoutHb07.findViewById(R.id.txt_immunization_pending);
        layoutHb07On = layoutHb07.findViewById(R.id.layout_immunization_on);
        txtHb07Done = (TextView) layoutHb07On.findViewById(R.id.txt_immunization_done);
        txtHb07DoneOn = (TextView) layoutHb07On.findViewById(R.id.txt_immunization_on);

        layoutBcg = (ViewGroup) itemView.findViewById(R.id.layout_bcg);
        txtBcgPendingView = (TextView) layoutBcg.findViewById(R.id.txt_immunization_pending);
        layoutBcgOn = layoutBcg.findViewById(R.id.layout_immunization_on);
        txtBcgDone = (TextView) layoutBcg.findViewById(R.id.txt_immunization_done);
        txtBcgDoneOn = (TextView) layoutBcg.findViewById(R.id.txt_immunization_on);

        layoutDpt1 = (ViewGroup) itemView.findViewById(R.id.layout_dpt_hb1_polio2);
        txtDpt1PendingView = (TextView) layoutDpt1.findViewById(R.id.txt_immunization_pending);
        layoutDpt1On = layoutDpt1.findViewById(R.id.layout_immunization_on);
        txtDpt1Done = (TextView) layoutDpt1.findViewById(R.id.txt_immunization_done);
        txtDpt1DoneOn = (TextView) layoutDpt1.findViewById(R.id.txt_immunization_on);

        layoutDpt2 = (ViewGroup) itemView.findViewById(R.id.layout_dpt_hb2_polio3);
        txtDpt2PendingView = (TextView) layoutDpt2.findViewById(R.id.txt_immunization_pending);
        layoutDpt2On = layoutDpt2.findViewById(R.id.layout_immunization_on);
        txtDpt2Done = (TextView) layoutDpt2.findViewById(R.id.txt_immunization_done);
        txtDpt2DoneOn = (TextView) layoutDpt2.findViewById(R.id.txt_immunization_on);

        layoutDpt3 = (ViewGroup) itemView.findViewById(R.id.layout_dpt_hb3_polio4);
        txtDpt3PendingView = (TextView) layoutDpt3.findViewById(R.id.txt_immunization_pending);
        layoutDpt3On = layoutDpt3.findViewById(R.id.layout_immunization_on);
        txtDpt3Done = (TextView) layoutDpt3.findViewById(R.id.txt_immunization_done);
        txtDpt3DoneOn = (TextView) layoutDpt3.findViewById(R.id.txt_immunization_on);

        layoutCampak = (ViewGroup) itemView.findViewById(R.id.layout_campak);
        txtCampakPendingView = (TextView) layoutCampak.findViewById(R.id.txt_immunization_pending);
        layoutCampakOn = layoutCampak.findViewById(R.id.layout_immunization_on);
        txtCampakDone = (TextView) layoutCampak.findViewById(R.id.txt_immunization_done);
        txtCampakDoneOn = (TextView) layoutCampak.findViewById(R.id.txt_immunization_on);
    }

    public void hideAllServiceModeOptions() {
        getServiceModeOverviewView().setVisibility(View.GONE);
        getServiceModeImmunizationView().setVisibility(View.GONE);
    }

    public BidanClientProfileView getProfileViewLayout() {
        return profileViewLayout;
    }

    public ClientAnakBirthStatusView getClientAnakBirthStatusView() {
        return clientAnakBirthStatusView;
    }

    public ImageButton getEditButton() {
        return editButton;
    }

    public TextView getTxtDobView() {
        return txtDobView;
    }

    public TextView getTxtIbuKiNo() {
        return txtIbuKiNo;
    }

    public TextView getTxtBirthWeight() {
        return txtBirthWeight;
    }

    public TextView getTxtBirthCondition() {
        return txtBirthCondition;
    }

    public ViewGroup getServiceModeViewsHolder() {
        return serviceModeViewsHolder;
    }

    public ViewGroup getServiceModeOverviewView() {
        return serviceModeOverviewView;
    }

    public ViewGroup getServiceModeImmunizationView() {
        return serviceModeImmunizationView;
    }

    public TextView getTxtLastServiceDate() {
        return txtLastServiceDate;
    }

    public TextView getTxtLastServiceName() {
        return txtLastServiceName;
    }

    public TextView getTxtVisitDate() {
        return txtVisitDate;
    }

    public TextView getTxtCurrentWeight() {
        return txtCurrentWeight;
    }

    public TextView getBirthPlace() {
        return birthPlace;
    }

    public ImageView getImdTrueIcon() {
        return imdTrueIcon;
    }

    public ImageView getImmuniHBTrueIcon() {
        return immuniHBTrueIcon;
    }

    public ImageView getVitKTrueIcon() {
        return vitKTrueIcon;
    }

    public ImageView getSlepMataTrueIcon() {
        return slepMataTrueIcon;
    }

    public ImageView getImdFalseIcon() {
        return imdFalseIcon;
    }

    public ImageView getImmuniHBFalseIcon() {
        return immuniHBFalseIcon;
    }

    public ImageView getVitKFalseIcon() {
        return vitKFalseIcon;
    }

    public ImageView getSlepMataFalseIcon() {
        return slepMataFalseIcon;
    }

    public TextView getTxtHb07PendingView() { return txtHb07PendingView; }

    public View getLayoutHb07On() { return layoutHb07On; }

    public TextView getTxtHb07DoneOn() { return txtHb07DoneOn; }

    public TextView getTxtHb07Done() {
        return txtHb07Done;
    }

    public ViewGroup getLayoutHb07() {
        return layoutHb07;
    }

    public ViewGroup getLayoutBcg() {
        return layoutBcg;
    }

    public TextView getTxtBcgPendingView() {
        return txtBcgPendingView;
    }

    public View getLayoutBcgOn() {
        return layoutBcgOn;
    }

    public TextView getTxtBcgDoneOn() {
        return txtBcgDoneOn;
    }

    public TextView getTxtBcgDone() {
        return txtBcgDone;
    }

    public ViewGroup getLayoutDpt1() {
        return layoutDpt1;
    }

    public TextView getTxtDpt1PendingView() {
        return txtDpt1PendingView;
    }

    public View getLayoutDpt1On() {
        return layoutDpt1On;
    }

    public TextView getTxtDpt1DoneOn() {
        return txtDpt1DoneOn;
    }

    public TextView getTxtDpt1Done() {
        return txtDpt1Done;
    }

    public ViewGroup getLayoutDpt2() {
        return layoutDpt2;
    }

    public TextView getTxtDpt2PendingView() {
        return txtDpt2PendingView;
    }

    public View getLayoutDpt2On() {
        return layoutDpt2On;
    }

    public TextView getTxtDpt2DoneOn() {
        return txtDpt2DoneOn;
    }

    public TextView getTxtDpt2Done() {
        return txtDpt2Done;
    }

    public ViewGroup getLayoutDpt3() {
        return layoutDpt3;
    }

    public TextView getTxtDpt3PendingView() {
        return txtDpt3PendingView;
    }

    public View getLayoutDpt3On() {
        return layoutDpt3On;
    }

    public TextView getTxtDpt3DoneOn() {
        return txtDpt3DoneOn;
    }

    public TextView getTxtDpt3Done() {
        return txtDpt3Done;
    }

    public ViewGroup getLayoutCampak() {
        return layoutCampak;
    }

    public TextView getTxtCampakPendingView() {
        return txtCampakPendingView;
    }

    public View getLayoutCampakOn() {
        return layoutCampakOn;
    }

    public TextView getTxtCampakDoneOn() {
        return txtCampakDoneOn;
    }

    public TextView getTxtCampakDone() {
        return txtCampakDone;
    }
}