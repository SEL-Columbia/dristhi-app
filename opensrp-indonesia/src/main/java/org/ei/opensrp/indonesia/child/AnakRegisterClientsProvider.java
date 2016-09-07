package org.ei.opensrp.indonesia.child;

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
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class AnakRegisterClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {
    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private Drawable iconPencilDrawable;
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    AlertService alertService;
    public AnakRegisterClientsProvider(Context context,
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
    //    if (convertView == null){
    //        convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_kb_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);

            viewHolder.childs_name = (TextView)convertView.findViewById(R.id.child_name);
            viewHolder.mother_name = (TextView)convertView.findViewById(R.id.mother_name);
            viewHolder.village_name = (TextView)convertView.findViewById(R.id.txt_village_name);
            viewHolder.childs_age = (TextView)convertView.findViewById(R.id.child_age);
            viewHolder.no_ibu = (TextView)convertView.findViewById(R.id.   txt_ibu_ki_no);
           // viewHolder.unique_id = (TextView)convertView.findViewById(R.id.unique_id);

        viewHolder.hp_badge =(ImageView)convertView.findViewById(R.id.img_hr_badge);

        //delivery documentation
        viewHolder.anak_register_dob = (TextView)convertView.findViewById(R.id.anak_register_dob);
        viewHolder.tempat_lahir = (TextView)convertView.findViewById(R.id.tempat_lahir);
        viewHolder.berat_lahir = (TextView)convertView.findViewById(R.id.berat_lahir);
        viewHolder.tipe_lahir = (TextView)convertView.findViewById(R.id.tipe_lahir);


        viewHolder.hb0_no =(ImageView)convertView.findViewById(R.id.icon_hb0_no);
        viewHolder.hb0_yes =(ImageView)convertView.findViewById(R.id.icon_hb0_yes);
        viewHolder.pol1_no =(ImageView)convertView.findViewById(R.id.icon_pol1_no);
        viewHolder.pol1_yes =(ImageView)convertView.findViewById(R.id.icon_pol1_yes);
        viewHolder.pol2_no =(ImageView)convertView.findViewById(R.id.icon_pol2_no);
        viewHolder.pol2_yes =(ImageView)convertView.findViewById(R.id.icon_pol2_yes);
        viewHolder.pol3_no =(ImageView)convertView.findViewById(R.id.icon_pol3_no);
        viewHolder.pol3_yes =(ImageView)convertView.findViewById(R.id.icon_pol3_yes);

        viewHolder.berat_badan = (TextView)convertView.findViewById(R.id.txt_current_weight);
        viewHolder.tanggal_kunjungan_anc = (TextView)convertView.findViewById(R.id.txt_visit_date);
        viewHolder.tinggi = (TextView)convertView.findViewById(R.id.txt_current_height);

            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.img_profile);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.child_boy));
            convertView.setTag(viewHolder);

        

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

        viewHolder.childs_name.setText(pc.getColumnmaps().get("namaBayi")!=null?pc.getColumnmaps().get("namaBayi"):"Bayi");

        String date = pc.getDetails().get("tanggalLahirAnak")!=null?pc.getDetails().get("tanggalLahirAnak"):"-";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(pc.getDetails().get("tanggalLahirAnak")!=null) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(format.parse(date));
                c.add(Calendar.DATE, 0);  // number of days to add
                date = format.format(c.getTime());  // dt is now the new date
                Date dates = format.parse(date);
                Date currentDateandTime = new Date();
                long diff = Math.abs(dates.getTime() - currentDateandTime.getTime());
                long diffDays = diff / (24 * 60 * 60 * 1000);
                if(diffDays <1){
                    viewHolder.childs_age.setText("");

                }
                viewHolder.childs_age.setText(diffDays+" Hari");

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                viewHolder.childs_age.setText("NaN Hari");
            }
        }
        else{
            viewHolder.childs_age.setText("-");
        }


        //delivery documentation
        viewHolder.anak_register_dob.setText(pc.getDetails().get("tanggalLahirAnak")!=null?pc.getDetails().get("tanggalLahirAnak"):"");
        viewHolder.tempat_lahir.setText(pc.getDetails().get("tempatBersalin")!=null?pc.getDetails().get("tempatBersalin"):"");
        viewHolder.berat_lahir.setText(pc.getDetails().get("beratLahir")!=null?pc.getDetails().get("beratLahir"):"");
       // viewHolder.tipe_lahir.setText(pc.getDetails().get("tanggalLahirAnak")!=null?pc.getDetails().get("tanggalLahirAnak"):"");


        //immunization
        if(pc.getDetails().get("tanggalpemberianimunisasiHb07")!=null){
            viewHolder.hb0_no.setVisibility(View.GONE);
            viewHolder.hb0_yes.setVisibility(View.VISIBLE);
        }
        if(pc.getDetails().get("tanggalpemberianimunisasiBCGdanPolio1")!=null){
            viewHolder.pol1_no.setVisibility(View.GONE);
            viewHolder.pol1_yes.setVisibility(View.VISIBLE);
        }
        if(pc.getDetails().get("tanggalpemberianimunisasiDPTHB1Polio2")!=null){
            viewHolder.pol2_no.setVisibility(View.GONE);
            viewHolder.pol2_yes.setVisibility(View.VISIBLE);
        }
        if(pc.getDetails().get("tanggalpemberianimunisasiDPTHB2Polio3")!=null){
            viewHolder.pol3_no.setVisibility(View.GONE);
            viewHolder.pol3_yes.setVisibility(View.VISIBLE);
        }


        viewHolder.berat_badan.setText(pc.getDetails().get("beratBadanBayiSetiapKunjunganBayiPerbulan")!=null?pc.getDetails().get("beratBadanBayiSetiapKunjunganBayiPerbulan"):"");
        viewHolder.tanggal_kunjungan_anc.setText(pc.getDetails().get("tanggalKunjunganBayiPerbulan")!=null?pc.getDetails().get("tanggalKunjunganBayiPerbulan"):"");
        viewHolder.tinggi.setText(pc.getDetails().get("hasilPengukuranTinggiBayi")!=null?pc.getDetails().get("hasilPengukuranTinggiBayi"):"");


        AllCommonsRepository childRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("anak");

        CommonPersonObject childobject = childRepository.findByCaseID(pc.entityId());
    //    String id = childobject.getColumnmaps().get("ibuCaseId");

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");
        final CommonPersonObject ibuparent = iburep.findByCaseID(childobject.getColumnmaps().get("ibuCaseId"));

        viewHolder.tempat_lahir.setText(ibuparent.getDetails().get("tempatBersalin")!=null?ibuparent.getDetails().get("tempatBersalin"):"");

        AllCommonsRepository kirep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");
        final CommonPersonObject kiparent = kirep.findByCaseID(ibuparent.getColumnmaps().get("kartuIbuId"));

        String namaayah = kiparent.getDetails().get("namaSuami")!=null?kiparent.getDetails().get("namaSuami"):"";
        String namaibu = kiparent.getColumnmaps().get("namalengkap")!=null?kiparent.getColumnmaps().get("namalengkap"):"";

          viewHolder.mother_name.setText(namaibu +","+ namaayah);
           viewHolder.village_name.setText(kiparent.getDetails().get("desa")!=null?kiparent.getDetails().get("desa"):"");
            viewHolder.no_ibu.setText(kiparent.getDetails().get("noIbu")!=null?kiparent.getDetails().get("noIbu"):"");




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
        View View = (ViewGroup) inflater().inflate(R.layout.smart_register_anak_client, null);
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
        TextView ki_lila_bb;
        TextView beratbadan_tb;
        TextView anc_penyakit_kronis;
        TextView status_type;
        TextView status_date;
        TextView alert_status;
        RelativeLayout status_layout;
        public TextView tanggal_kunjungan_anc;
        public TextView anc_number;
        public TextView kunjugan_ke;
        public ImageView hr_badge  ;
        public ImageView hp_badge;
         ImageView hrpp_badge;
        public ImageView bpl_badge;
        public ImageView hrp_badge;
        ImageView img_hrl_badge;


        public TextView komplikasi;
        public TextView kondisi_ibu;
        public TextView kondisi_anak_1;
        public TextView kondisi_anak_2;
        TextView pnc_id;
        public TextView td_sistolik;
        public TextView td_diastolik;
        public TextView td_suhu;
        public TextView childs_age;
        public TextView mother_name;
        TextView childs_name;
        public TextView anak_register_dob;
        public TextView tempat_lahir;
        public TextView berat_lahir;
        public TextView tipe_lahir;
        public ImageView hb0_no;
        public ImageView hb0_yes;
        public ImageView pol1_no;
        public ImageView pol1_yes;
        public ImageView pol2_no;
        public ImageView pol2_yes;
        public ImageView pol3_no;
        public ImageView pol3_yes;

        public TextView berat_badan;
        public TextView tinggi;
    }


}