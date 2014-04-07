package org.ei.drishti.view.viewModel;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.ei.drishti.R;

public class NativeECSmartRegisterViewModel {
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
    private TextView FPMethodView;
    private TextView maleChildrenView;
    private TextView femaleChildrenView;
    private TextView statusView;

    public NativeECSmartRegisterViewModel(ViewGroup itemView) {
        this.txtNameView = (TextView) itemView.findViewById(R.id.txt_wife_name);
        this.txtHusbandNameView = (TextView) itemView.findViewById(R.id.txt_husband_name);
        this.txtVillageNameView = (TextView) itemView.findViewById(R.id.txt_village_name);
        this.txtAgeView = (TextView) itemView.findViewById(R.id.txt_age);
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
        this.FPMethodView = (TextView) itemView.findViewById(R.id.txt_fp);
        this.maleChildrenView = (TextView) itemView.findViewById(R.id.txt_male_children);
        this.femaleChildrenView = (TextView) itemView.findViewById(R.id.txt_female_children);
        this.statusView = (TextView) itemView.findViewById(R.id.txt_status);
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
        return FPMethodView;
    }

    public TextView maleChildrenView() {
        return maleChildrenView;
    }

    public TextView femaleChildrenView() {
        return femaleChildrenView;
    }

    public TextView statusView() {
        return statusView;
    }
}