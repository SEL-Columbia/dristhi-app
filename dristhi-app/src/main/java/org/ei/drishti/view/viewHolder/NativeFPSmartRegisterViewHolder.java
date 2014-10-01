package org.ei.drishti.view.viewHolder;

import android.view.ViewGroup;
import android.widget.TextView;
import org.ei.drishti.R;
import org.ei.drishti.view.customControls.ClientFpMethodAndUpdateView;
import org.ei.drishti.view.customControls.ClientGplsaChildView;
import org.ei.drishti.view.customControls.ClientProfileView;

public class NativeFPSmartRegisterViewHolder {
    private final ClientProfileView profileInfoLayout;
    private final TextView txtECNumberView;
    private final ClientGplsaChildView gplsaChildLayout;
    private final ClientFpMethodAndUpdateView fpMethodview;


    public NativeFPSmartRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (ClientProfileView) itemView.findViewById(R.id.profile_info_layout);
        this.profileInfoLayout.initialize();

        this.txtECNumberView = (TextView) itemView.findViewById(R.id.txt_ec_number);

        this.gplsaChildLayout = (ClientGplsaChildView) itemView.findViewById(R.id.gplsa_child_layout);
        this.gplsaChildLayout.initialize();

        fpMethodview = (ClientFpMethodAndUpdateView) itemView.findViewById(R.id.fp_method_and_update_layout);
        fpMethodview.initialize();

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

    public ClientFpMethodAndUpdateView fpMethodView() {
        return fpMethodview;
    }

}