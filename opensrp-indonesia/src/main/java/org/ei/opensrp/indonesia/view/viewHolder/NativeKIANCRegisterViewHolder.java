package org.ei.opensrp.indonesia.view.viewHolder;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.customControls.BidanClientProfileView;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class NativeKIANCRegisterViewHolder {

    private final BidanClientProfileView profileInfoLayout;
    private final TextView ancStatusUsiaKlinis;
    private final TextView ancStatusHtpt;
    private final TextView pemeriksaanLILA;
    private final TextView pemeriksaanBB;
    private final TextView penyakitKronis;
    // private final TextView lblPenyakitKronis;
    // private final TextView alergi;
    // private final TextView lblAlergi;
    private final ViewGroup layoutResikoANC;
    private final ImageButton editButton;
    private final TextView edd;

    private final RelativeLayout detailIdLayout;
    private TextView txtNoIbu;
    private TextView txtUniqueId;

    public NativeKIANCRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (BidanClientProfileView) itemView.findViewById(R.id.profile_info_layout_ki);
        this.profileInfoLayout.initialize();

        this.detailIdLayout = (RelativeLayout) itemView.findViewById(R.id.id_detail_layout);
        this.txtNoIbu = (TextView) this.detailIdLayout.findViewById(R.id.no_ibu);
        this.txtUniqueId = (TextView) this.detailIdLayout.findViewById(R.id.unique_id);


        this.ancStatusUsiaKlinis = (TextView) itemView.findViewById(R.id.txt_usia_klinis);
        this.ancStatusHtpt = (TextView) itemView.findViewById(R.id.txt_htpt);
        this.pemeriksaanLILA = (TextView) itemView.findViewById(R.id.txt_ki_lila_bb);
        this.pemeriksaanBB = (TextView) itemView.findViewById(R.id.txt_ki_beratbadan_tb);
        this.penyakitKronis = (TextView) itemView.findViewById(R.id.txt_ki_anc_penyakit_kronis);
        this.layoutResikoANC = (ViewGroup) itemView.findViewById(R.id.layout_resiko_anc);
        this.editButton = (ImageButton) itemView.findViewById(R.id.btn_edit);
        this.edd = (TextView) itemView.findViewById(R.id.txt_edd);
    }

    public ImageButton editButton() { return editButton; }
    public TextView ancStatusUsiaKlinis() { return ancStatusUsiaKlinis; }
    public TextView ancStatusHtpt() { return ancStatusHtpt; }

    public TextView edd() { return edd; }
    public BidanClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }
    public TextView getPemeriksaanLILA() {
        return pemeriksaanLILA;
    }
    public TextView getPemeriksaanBB() {
        return pemeriksaanBB;
    }

    public ViewGroup getLayoutResikoANC() {
        return layoutResikoANC;
    }
    public TextView txtNoIbu() { return txtNoIbu; }
    public TextView txtUniqueId() { return txtUniqueId; }

    public TextView getPenyakitKronis() {
        return penyakitKronis;
    }
}
