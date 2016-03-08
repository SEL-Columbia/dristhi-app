package org.ei.opensrp.indonesia.view.viewHolder;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.view.customControls.BidanClientProfileView;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class NativeKIPNCRegisterViewHolder {

    private final BidanClientProfileView profileInfoLayout;
    private final TextView pncId;
    private final TextView dokTempat;
    private final TextView dokTipe;
    private final TextView dokTglBersalin;
    private final TextView komplikasi;
    private final ViewGroup tandaVitalLayout;
    private final TextView tdSistolik;
    private final TextView tdDiastolik;
    private final TextView tdSuhu;
    private final ImageButton editButton;
    private final TextView kondisiIbu;
    private final TextView kondisiAnak1;
    private final TextView kondisiAnak2;

    public NativeKIPNCRegisterViewHolder(ViewGroup itemView) {
        this.profileInfoLayout = (BidanClientProfileView) itemView.findViewById(R.id.profile_info_layout_ki);
        this.profileInfoLayout.initialize();

        this.pncId = (TextView) itemView.findViewById(R.id.pnc_id);
        this.dokTempat = (TextView) itemView.findViewById(R.id.txt_tempat_persalinan);
        this.dokTglBersalin = (TextView) itemView.findViewById(R.id.dok_tanggal_bersalin);
        this.dokTipe = (TextView) itemView.findViewById(R.id.txt_tipe);
        this.komplikasi = (TextView) itemView.findViewById(R.id.txt_komplikasi);
        this.tandaVitalLayout = (ViewGroup) itemView.findViewById(R.id.tanda_vital_layout);
        this.tdSistolik = (TextView) tandaVitalLayout.findViewById(R.id.txt_td_sistolik);
        this.tdDiastolik = (TextView) tandaVitalLayout.findViewById(R.id.txt_td_diastolik);
        this.tdSuhu = (TextView) tandaVitalLayout.findViewById(R.id.txt_td_suhu);
        this.editButton = (ImageButton) itemView.findViewById(R.id.btn_edit);

        this.kondisiIbu = (TextView) itemView.findViewById(R.id.txt_kondisi_ibu);
        this.kondisiAnak1 = (TextView) itemView.findViewById(R.id.txt_kondisi_anak_1);
        this.kondisiAnak2 = (TextView) itemView.findViewById(R.id.txt_kondisi_anak_2);
    }

    public TextView kondisiIbu() { return kondisiIbu; }
    public TextView kondisiAnak1() { return kondisiAnak1; }
    public TextView kondisiAnak2() { return kondisiAnak2; }
    public TextView dokTempat() { return dokTempat; }
    public TextView dokTanggalBersalin() { return dokTglBersalin; }
    public TextView dokTipe() { return dokTipe; }
    public TextView pncId() { return pncId; }
    public BidanClientProfileView profileInfoLayout() {
        return profileInfoLayout;
    }

    public TextView getKomplikasi() {
        return komplikasi;
    }

    public ViewGroup getTandaVitalLayout() {
        return tandaVitalLayout;
    }

    public TextView getTdSistolik() {
        return tdSistolik;
    }

    public TextView getTdDiastolik() {
        return tdDiastolik;
    }

    public TextView getTdSuhu() {
        return tdSuhu;
    }

    public ImageButton editButton() {
        return editButton;
    }
}
