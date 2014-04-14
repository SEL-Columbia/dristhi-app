package org.ei.drishti.view.viewHolder;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.ei.drishti.R;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.view.controller.ECSmartRegisterController.*;

public class NativeECSmartRegisterViewHolder {
    private ViewGroup profileInfoLayout;
    private ImageView imgProfileView;
    private TextView txtNameView;
    private TextView txtHusbandNameView;
    private TextView txtVillageNameView;
    private TextView txtAgeView;
    private TextView txtECNumberView;
    private TextView txtGravida;
    private TextView txtParity;
    private TextView txtNumberOfLivingChildren;
    private TextView txtNumberOfStillBirths;
    private TextView txtNumberOfAbortions;
    private ImageView badgeHPView;
    private ImageView badgeBPLView;
    private ImageView badgeSCView;
    private ImageView badgeSTView;
    private TextView fpMethodView;
    private TextView fpMethodDateView;
    private TextView fpMethodQuantityLabelView;
    private TextView fpMethodQuantityView;
    private TextView iudPlaceView;
    private TextView iudPersonView;
    private TextView maleChildrenView;
    private TextView femaleChildrenView;
    private ImageButton editButton;
    private Map<String, ViewStubInflater> statusLayouts;

    public NativeECSmartRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (ViewGroup) itemView.findViewById(R.id.profile_info_layout);
        this.imgProfileView = (ImageView) profileInfoLayout.findViewById(R.id.img_profile);
        this.txtNameView = (TextView) profileInfoLayout.findViewById(R.id.txt_wife_name);
        this.txtHusbandNameView = (TextView) profileInfoLayout.findViewById(R.id.txt_husband_name);
        this.txtVillageNameView = (TextView) profileInfoLayout.findViewById(R.id.txt_village_name);
        this.txtAgeView = (TextView) profileInfoLayout.findViewById(R.id.txt_age);

        this.txtECNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);
        this.txtGravida = (TextView) itemView.findViewById(R.id.txt_gravida);
        this.txtParity = (TextView) itemView.findViewById(R.id.txt_parity);
        this.txtNumberOfLivingChildren = (TextView) itemView.findViewById(R.id.txt_number_of_living_children);
        this.txtNumberOfStillBirths = (TextView) itemView.findViewById(R.id.txt_number_of_still_births);
        this.txtNumberOfAbortions = (TextView) itemView.findViewById(R.id.txt_number_of_abortions);
        this.badgeHPView = (ImageView) itemView.findViewById(R.id.img_hp_badge);
        this.badgeBPLView = (ImageView) itemView.findViewById(R.id.img_bpl_badge);
        this.badgeSCView = (ImageView) itemView.findViewById(R.id.img_sc_badge);
        this.badgeSTView = (ImageView) itemView.findViewById(R.id.img_st_badge);
        this.fpMethodView = (TextView) itemView.findViewById(R.id.txt_fp_method);
        this.fpMethodDateView = (TextView) itemView.findViewById(R.id.txt_fp_method_date);
        this.fpMethodQuantityLabelView = (TextView) itemView.findViewById(R.id.txt_fp_method_quantity_label);
        this.fpMethodQuantityView = (TextView) itemView.findViewById(R.id.txt_fp_method_quantity);
        this.iudPlaceView = (TextView) itemView.findViewById(R.id.txt_iud_place);
        this.iudPersonView = (TextView) itemView.findViewById(R.id.txt_iud_person);
        this.maleChildrenView = (TextView) itemView.findViewById(R.id.txt_male_children);
        this.femaleChildrenView = (TextView) itemView.findViewById(R.id.txt_female_children);
        this.editButton = (ImageButton) itemView.findViewById(R.id.btn_edit);

        this.statusLayouts = new HashMap<String, ViewStubInflater>();
        ViewStubInflater commonECAndFPLayout = new ViewStubInflater((ViewStub) itemView.findViewById(R.id.ec_and_fp_status_layout));

        this.statusLayouts
                .put(EC_STATUS, commonECAndFPLayout);
        this.statusLayouts
                .put(FP_STATUS, commonECAndFPLayout);
        this.statusLayouts
                .put(ANC_STATUS, new ViewStubInflater((ViewStub) itemView.findViewById(R.id.anc_status_layout)));
        this.statusLayouts
                .put(PNC_STATUS, new ViewStubInflater((ViewStub) itemView.findViewById(R.id.pnc_status_layout)));
        this.statusLayouts
                .put(PNC_FP_STATUS, new ViewStubInflater((ViewStub) itemView.findViewById(R.id.pnc_and_fp_status_layout)));
    }

    public ViewGroup profileInfoLayout() {
        return profileInfoLayout;
    }

    public ImageView imgProfileView() {
        return imgProfileView;
    }

    public TextView txtNameView() {
        return txtNameView;
    }

    public TextView txtHusbandNameView() {
        return txtHusbandNameView;
    }

    public TextView txtVillageNameView() {
        return txtVillageNameView;
    }

    public TextView txtAgeView() {
        return txtAgeView;
    }

    public TextView txtECNumberView() {
        return txtECNumberView;
    }

    public TextView txtGravida() {
        return txtGravida;
    }

    public TextView txtParity() {
        return txtParity;
    }

    public TextView txtNumberOfLivingChildren() {
        return txtNumberOfLivingChildren;
    }

    public TextView txtNumberOfStillBirths() {
        return txtNumberOfStillBirths;
    }

    public TextView txtNumberOfAbortions() {
        return txtNumberOfAbortions;
    }

    public ImageView badgeHPView() {
        return badgeHPView;
    }

    public ImageView badgeBPLView() {
        return badgeBPLView;
    }

    public ImageView badgeSCView() {
        return badgeSCView;
    }

    public ImageView badgeSTView() {
        return badgeSTView;
    }

    public TextView fpMethodView() {
        return fpMethodView;
    }

    public TextView fpMethodDateView() {
        return fpMethodDateView;
    }

    public TextView fpMethodQuantityLabelView() {
        return fpMethodQuantityLabelView;
    }

    public TextView fpMethodQuantityView() {
        return fpMethodQuantityView;
    }

    public TextView iudPlaceView() {
        return iudPlaceView;
    }

    public TextView iudPersonView() {
        return iudPersonView;
    }

    public TextView maleChildrenView() {
        return maleChildrenView;
    }

    public TextView femaleChildrenView() {
        return femaleChildrenView;
    }

    public ImageButton editButton() {
        return editButton;
    }

    public ViewGroup statusLayout(String statusType) {
        return statusLayouts.get(statusType).get();
    }

    public void hideAllStatusLayouts() {
        for (String statusLayout : statusLayouts.keySet()) {
            statusLayouts.get(statusLayout).setVisibility(View.GONE);
        }
    }

    public TextView getStatusDateView(ViewGroup statusViewGroup) {
        return ((TextView) statusViewGroup.findViewById(R.id.txt_status_date));
    }

    public TextView getStatusTypeView(ViewGroup statusViewGroup) {
        return ((TextView) statusViewGroup.findViewById(R.id.txt_status_type));
    }

    public TextView getANCStatusEDDDateView(ViewGroup statusViewGroup) {
        return ((TextView) statusViewGroup.findViewById(R.id.txt_anc_status_edd_date));
    }

    public TextView getFPStatusDateView(ViewGroup statusViewGroup) {
        return ((TextView) statusViewGroup.findViewById(R.id.txt_fp_status_date));
    }

    public void refreshAllFPMethodDetailViews(int fpMethodTextColor) {
        fpMethodView.setTextColor(fpMethodTextColor);
        fpMethodDateView.setVisibility(View.GONE);
        fpMethodQuantityLabelView.setVisibility(View.GONE);
        fpMethodQuantityView.setVisibility(View.GONE);
        iudPersonView.setVisibility(View.GONE);
        iudPlaceView.setVisibility(View.GONE);
    }
}