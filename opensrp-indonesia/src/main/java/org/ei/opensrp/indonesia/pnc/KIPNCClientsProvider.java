package org.ei.opensrp.indonesia.pnc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.text.BreakIterator;
import java.util.List;

import static org.ei.opensrp.util.StringUtil.humanize;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanizeAndDoUPPERCASE;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class KIPNCClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {
    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private Drawable iconPencilDrawable;
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    AlertService alertService;
    public KIPNCClientsProvider(Context context,
                                View.OnClickListener onClickListener,
                                AlertService alertService) {
        this.onClickListener = onClickListener;
//        this.controller = controller;
        this.context = context;
        this.alertService = alertService;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);

    }

    @Override
    public void getView(SmartRegisterClient smartRegisterClient, View convertView) {

        ViewHolder viewHolder;
        if(convertView.getTag() == null || !(convertView.getTag() instanceof  ViewHolder)) {
            viewHolder = new ViewHolder();
            viewHolder.profilelayout = (LinearLayout) convertView.findViewById(R.id.profile_info_layout);

            viewHolder.wife_name = (TextView) convertView.findViewById(R.id.wife_name);
            viewHolder.husband_name = (TextView) convertView.findViewById(R.id.txt_husband_name);
            viewHolder.village_name = (TextView) convertView.findViewById(R.id.txt_village_name);
            viewHolder.wife_age = (TextView) convertView.findViewById(R.id.wife_age);
            viewHolder.pnc_id = (TextView) convertView.findViewById(R.id.pnc_id);
            // viewHolder.unique_id = (TextView)convertView.findViewById(R.id.unique_id);

            viewHolder.hr_badge = (ImageView) convertView.findViewById(R.id.img_hr_badge);
            viewHolder.img_hrl_badge = (ImageView) convertView.findViewById(R.id.img_hrl_badge);
            viewHolder.bpl_badge = (ImageView) convertView.findViewById(R.id.img_bpl_badge);
            viewHolder.hrp_badge = (ImageView) convertView.findViewById(R.id.img_hrp_badge);
            viewHolder.hrpp_badge = (ImageView) convertView.findViewById(R.id.img_hrpp_badge);
            // ViewHolder.img_hp_badge = ImageView.Set;   img_hrl_badge img_bpl_badge img_hrp_badge img_hrpp_badge

            viewHolder.tanggal_bersalin = (TextView) convertView.findViewById(R.id.dok_tanggal_bersalin);
            viewHolder.tempat_persalinan = (TextView) convertView.findViewById(R.id.txt_tempat_persalinan);
            viewHolder.dok_tipe = (TextView) convertView.findViewById(R.id.txt_tipe);

            viewHolder.komplikasi = (TextView) convertView.findViewById(R.id.txt_komplikasi);

            viewHolder.tanggal_kunjungan = (TextView) convertView.findViewById(R.id.txt_tanggal_kunjungan_pnc);
            viewHolder.KF = (TextView) convertView.findViewById(R.id.txt_kf);
            viewHolder.vit_a = (TextView) convertView.findViewById(R.id.txt_vit_a);

            viewHolder.td_sistolik = (TextView) convertView.findViewById(R.id.txt_td_sistolik);
            viewHolder.td_diastolik = (TextView) convertView.findViewById(R.id.txt_td_diastolik);
            viewHolder.td_suhu = (TextView) convertView.findViewById(R.id.txt_td_suhu);

            //  txt_kondisi_ibu txt_KF txt_vit_a

            viewHolder.profilepic = (ImageView) convertView.findViewById(R.id.img_profile);
            viewHolder.follow_up = (ImageButton) convertView.findViewById(R.id.btn_edit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.woman_placeholder));

        viewHolder.follow_up.setOnClickListener(onClickListener);
        viewHolder.follow_up.setTag(smartRegisterClient);
        viewHolder.profilelayout.setOnClickListener(onClickListener);
        viewHolder.profilelayout.setTag(smartRegisterClient);
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;
        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewHolder.follow_up.setImageDrawable(iconPencilDrawable);
        viewHolder.follow_up.setOnClickListener(onClickListener);
        //set image

        viewHolder.tanggal_bersalin.setText(humanize(pc.getDetails().get("tanggalKalaIAktif")!=null?pc.getDetails().get("tanggalKalaIAktif"):""));
        String tempat =pc.getDetails().get("tempatBersalin")!=null?pc.getDetails().get("tempatBersalin"):"";
        viewHolder.tempat_persalinan.setText(tempat.equals("podok_bersalin_desa")?"POLINDES":tempat.equals("pusat_kesehatan_masyarakat_pembantu")?"Puskesmas pembantu":tempat.equals("pusat_kesehatan_masyarakat")?"Puskesmas":humanize(tempat));
        viewHolder.dok_tipe.setText(humanize(pc.getDetails().get("caraPersalinanIbu")!=null?pc.getDetails().get("caraPersalinanIbu"):""));
        viewHolder.komplikasi.setText(humanize(pc.getDetails().get("komplikasi")!=null?pc.getDetails().get("komplikasi"):""));


        String date = pc.getDetails().get("referenceDate")!=null?pc.getDetails().get("referenceDate"):"";
        String vit_a = pc.getDetails().get("pelayananfe")!=null?pc.getDetails().get("pelayananfe"):"";
        viewHolder.tanggal_kunjungan.setText(context.getString(R.string.str_pnc_delivery_date) +" "+ date);

        viewHolder.vit_a.setText(context.getString(R.string.fe)+" "+vit_a);

        viewHolder.td_suhu.setText(humanize(pc.getDetails().get("tandaVitalSuhu")!=null?pc.getDetails().get("tandaVitalSuhu"):""));

        AllCommonsRepository kiRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");

        CommonPersonObject kiobject = kiRepository.findByCaseID(pc.entityId());

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");
        final CommonPersonObject ibuparent = iburep.findByCaseID(kiobject.getColumnmaps().get("kartuIbuId"));
        viewHolder.td_sistolik.setText(humanize(ibuparent.getDetails().get("tandaVitalTDSistolik")!=null?ibuparent.getDetails().get("tandaVitalTDSistolik"):""));
        viewHolder.td_diastolik.setText(humanize(ibuparent.getDetails().get("tandaVitalTDDiastolik")!=null?ibuparent.getDetails().get("tandaVitalTDDiastolik"):""));

        viewHolder.hr_badge.setVisibility(View.INVISIBLE);
        viewHolder.hrp_badge.setVisibility(View.INVISIBLE);
        viewHolder.img_hrl_badge.setVisibility(View.INVISIBLE);

        if(ibuparent.getDetails().get("highRiskSTIBBVs")!=null && ibuparent.getDetails().get("highRiskSTIBBVs").equals("yes")
                || ibuparent.getDetails().get("highRiskEctopicPregnancy")!=null && ibuparent.getDetails().get("highRiskEctopicPregnancy").equals("yes")
                || ibuparent.getDetails().get("highRiskCardiovascularDiseaseRecord")!=null && ibuparent.getDetails().get("highRiskDidneyDisorder").equals("yes")
                || ibuparent.getDetails().get("highRiskDidneyDisorder")!=null && ibuparent.getDetails().get("highRiskHeartDisorder").equals("yes")
                || ibuparent.getDetails().get("highRiskHeartDisorder")!=null && ibuparent.getDetails().get("highRiskAsthma").equals("yes")
                || ibuparent.getDetails().get("highRiskAsthma")!=null && ibuparent.getDetails().get("highRiskTuberculosis").equals("yes")
                || ibuparent.getDetails().get("highRiskTuberculosis")!=null && ibuparent.getDetails().get("highRiskMalaria").equals("yes")
                || ibuparent.getDetails().get("highRiskMalaria")!=null && ibuparent.getDetails().get("highRiskMalaria").equals("yes") )
        {
            viewHolder.hr_badge.setVisibility(View.VISIBLE);
        }
        if (ibuparent.getDetails().get("highRiskPregnancyPIH") != null && ibuparent.getDetails().get("highRiskPregnancyPIH").equals("yes")
                || pc.getDetails().get("highRiskPregnancyPIH") != null && pc.getDetails().get("highRiskPregnancyPIH").equals("yes")
                || ibuparent.getDetails().get("highRiskPregnancyProteinEnergyMalnutrition") != null && ibuparent.getDetails().get("highRiskPregnancyProteinEnergyMalnutrition").equals("yes")
                || pc.getDetails().get("HighRiskPregnancyTooManyChildren") != null && pc.getDetails().get("HighRiskPregnancyTooManyChildren").equals("yes")
                || ibuparent.getDetails().get("highRiskPregnancyDiabetes") != null && ibuparent.getDetails().get("highRiskPregnancyDiabetes").equals("yes")
                || ibuparent.getDetails().get("highRiskPregnancyAnemia") != null && ibuparent.getDetails().get("highRiskPregnancyAnemia").equals("yes")) {
            viewHolder.hrp_badge.setVisibility(View.VISIBLE);
        }
        if (ibuparent.getDetails().get("highRiskLabourFetusMalpresentation") != null && ibuparent.getDetails().get("highRiskLabourFetusMalpresentation").equals("yes")
                || ibuparent.getDetails().get("highRiskLabourFetusSize") != null && ibuparent.getDetails().get("highRiskLabourFetusSize").equals("yes")
                || ibuparent.getDetails().get("highRisklabourFetusNumber") != null && ibuparent.getDetails().get("highRisklabourFetusNumber").equals("yes")
                || pc.getDetails().get("HighRiskLabourSectionCesareaRecord") != null && pc.getDetails().get("HighRiskLabourSectionCesareaRecord").equals("yes")
                || ibuparent.getDetails().get("highRiskLabourTBRisk") != null && ibuparent.getDetails().get("highRiskLabourTBRisk").equals("yes")) {
            viewHolder.img_hrl_badge.setVisibility(View.VISIBLE);
        }

        String kf_ke = kiobject.getColumnmaps().get("hariKeKF")!=null?kiobject.getColumnmaps().get("hariKeKF"):"";
        viewHolder.KF.setText(context.getString(R.string.hari_ke_kf)+" "+ humanizeAndDoUPPERCASE(kf_ke));
        viewHolder.wife_name.setText(ibuparent.getColumnmaps().get("namalengkap")!=null?ibuparent.getColumnmaps().get("namalengkap"):"");
        viewHolder.husband_name.setText(ibuparent.getColumnmaps().get("namaSuami")!=null?ibuparent.getColumnmaps().get("namaSuami"):"");
        viewHolder.village_name.setText(ibuparent.getDetails().get("desa")!=null?ibuparent.getDetails().get("desa"):"");
        viewHolder.wife_age.setText(ibuparent.getColumnmaps().get("umur")!=null?ibuparent.getColumnmaps().get("umur"):"");
        viewHolder.pnc_id.setText(ibuparent.getColumnmaps().get("noIbu")!=null?ibuparent.getColumnmaps().get("noIbu"):"");
     //   viewHolder.unique_id.setText(ibuparent.getDetails().get("unique_id")!=null?ibuparent.getDetails().get("unique_id"):"");


        //  AllCommonsRepository anakrep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("anak");
        //    final CommonPersonObject anakparent = anakrep.findByCaseID(kiobject.getColumnmaps().get("id"));

      //  viewHolder.KF.setText(anakparent.getDetails().get("saatLahirsd5JamKondisibayi")!=null?anakparent.getDetails().get("saatLahirsd5JamKondisibayi"):"");
      //  viewHolder.KF.setText(anakparent.getDetails().get("saatLahirsd5JamKondisibayi")!=null?anakparent.getDetails().get("saatLahirsd5JamKondisibayi")+","+anakparent.getDetails().get("beratLahir"):"-");






        convertView.setLayoutParams(clientViewLayoutParams);
     //   return convertView;
    }
   // CommonPersonObjectController householdelcocontroller;


    //    @Override
    public SmartRegisterClients getClients() {
        return controller.getClients();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        // do nothing.
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public View inflatelayoutForCursorAdapter() {
        View View = (ViewGroup) inflater().inflate(R.layout.smart_register_ki_pnc_client, null);
        return View;
    }

    class ViewHolder {

        TextView wife_name ;
        TextView husband_name ;
        TextView village_name;
        TextView wife_age;
        LinearLayout profilelayout;
        ImageView profilepic;
        TextView gravida;
        Button warnbutton;
        ImageButton follow_up;
        TextView parity;
        TextView number_of_abortus;
        TextView number_of_alive;
        TextView no_ibu;
        TextView unique_id;
        TextView tanggal_bersalin;
        TextView tempat_persalinan;
        TextView dok_tipe;
        TextView tanggal_kunjungan;
        TextView beratbadan_tb;
        TextView anc_penyakit_kronis;
        TextView status_type;
        TextView status_date;
        TextView alert_status;
        RelativeLayout status_layout;
         TextView tanggal_kunjungan_anc;
         TextView anc_number;
         TextView kunjugan_ke;
         ImageView hr_badge  ;
         ImageView hp_badge;
         ImageView hrpp_badge;
         ImageView bpl_badge;
         ImageView hrp_badge;
        ImageView img_hrl_badge;


         TextView komplikasi;
         TextView kondisi_ibu;
         TextView KF;
         TextView vit_a;
        TextView pnc_id;
         TextView td_sistolik;
         TextView td_diastolik;
         TextView td_suhu;
    }


}