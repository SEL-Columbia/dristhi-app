package org.ei.opensrp.indonesia.view.viewHolder;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.customControls.BidanClientProfileView;
import org.ei.opensrp.indonesia.view.customControls.BidanClientStatusView;
import org.ei.opensrp.indonesia.view.customControls.BidanObsetriView;
import org.ei.opensrp.indonesia.view.customControls.ClientChildrenViewKI;

/**
 * Created by Dimas Ciputra on 2/18/15.
 */
public class NativeKIRegisterViewHolder {
    private final BidanClientProfileView profileInfoLayout;
    private final BidanObsetriView bidanObsetriView;
    private final ClientChildrenViewKI childrenView;
    private TextView txtKBMethod;
    private TextView txtKBStart;
    private TextView txtEdd;
    private TextView txtEddDue;
    private TextView txtNoIbu;
    private TextView txtUniqueId;
    private final BidanClientStatusView statusView;
    private final ImageButton editButton;
    private final RelativeLayout detailIdLayout;

    public NativeKIRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (BidanClientProfileView) itemView.findViewById(R.id.profile_info_layout_ki);
        this.profileInfoLayout.initialize();

        this.bidanObsetriView = (BidanObsetriView) itemView.findViewById(R.id.obsetri_layout);
        this.bidanObsetriView.initialize();

        this.detailIdLayout = (RelativeLayout) itemView.findViewById(R.id.id_detail_layout);
        this.txtNoIbu = (TextView) this.detailIdLayout.findViewById(R.id.no_ibu);
        this.txtUniqueId = (TextView) this.detailIdLayout.findViewById(R.id.unique_id);

        statusView = (BidanClientStatusView) itemView.findViewById(R.id.status_layout);
        statusView.initialize();

        childrenView = (ClientChildrenViewKI) itemView.findViewById(R.id.children_layout);
        childrenView.initialize();

        this.txtEdd = (TextView) itemView.findViewById(R.id.txt_edd);

        this.txtEddDue = (TextView) itemView.findViewById(R.id.txt_edd_due);

        this.editButton = (ImageButton) itemView.findViewById(R.id.btn_edit);
    }

    public BidanClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }
    public BidanClientStatusView statusView() { return statusView; }
    public BidanObsetriView bidanObsetriView() { return bidanObsetriView; }
    public ClientChildrenViewKI childrenView() {
        return childrenView;
    }
    public TextView txtNoIbu() { return txtNoIbu; }
    public TextView txtEdd() { return txtEdd; }
    public TextView txtEddDue() { return txtEddDue; }
    public TextView txtUniqueId() { return txtUniqueId; }
    public ImageButton editButton() { return editButton; }
}
