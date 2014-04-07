package org.ei.drishti.view.viewModel;

import android.view.ViewGroup;
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
}