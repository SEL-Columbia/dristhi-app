package org.ei.opensrp.indonesia.anc;

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

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Dimas Ciputra on 3/4/15.
 */
public class KIANCClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {
    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private Drawable iconPencilDrawable;
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    AlertService alertService;
    public KIANCClientsProvider(Context context,
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

            viewHolder.wife_name = (TextView)convertView.findViewById(R.id.wife_name);
            viewHolder.husband_name = (TextView)convertView.findViewById(R.id.txt_husband_name);
            viewHolder.village_name = (TextView)convertView.findViewById(R.id.txt_village_name);
            viewHolder.wife_age = (TextView)convertView.findViewById(R.id.wife_age);
            viewHolder.no_ibu = (TextView)convertView.findViewById(R.id.no_ibu);
            viewHolder.unique_id = (TextView)convertView.findViewById(R.id.unique_id);

       //     viewHolder.gravida = (TextView)convertView.findViewById(R.id.txt_gravida);
      //      viewHolder.parity = (TextView)convertView.findViewById(R.id.txt_parity);
      //      viewHolder.number_of_abortus = (TextView)convertView.findViewById(R.id.txt_number_of_abortus);
      //      viewHolder.number_of_alive = (TextView)convertView.findViewById(R.id.txt_number_of_alive);
       // txt_ki_lila_bb
             viewHolder.usia_klinis = (TextView)convertView.findViewById(R.id.txt_usia_klinis);
            viewHolder.htpt = (TextView)convertView.findViewById(R.id.txt_htpt);
        viewHolder.ki_lila_bb = (TextView)convertView.findViewById(R.id.txt_ki_lila_bb);
        viewHolder.beratbadan_tb = (TextView)convertView.findViewById(R.id.txt_ki_beratbadan_tb);
        viewHolder.anc_penyakit_kronis = (TextView)convertView.findViewById(R.id.txt_ki_anc_penyakit_kronis);
       // txt_ki_anc_penyakit_kronis
            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.img_profile);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.woman_placeholder));
            convertView.setTag(viewHolder);
    //    }else{
    //        viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.woman_placeholder));
    //    }
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
        //  final ImageView childview = (ImageView)convertView.findViewById(R.id.profilepic);
        AllCommonsRepository kiRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");

        CommonPersonObject kiobject = kiRepository.findByCaseID(pc.entityId());

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");
        final CommonPersonObject ibuparent = iburep.findByCaseID(kiobject.getColumnmaps().get("kartuIbuId"));

        viewHolder.wife_name.setText(ibuparent.getDetails().get("namalengkap")!=null?ibuparent.getDetails().get("namalengkap"):"");
        viewHolder.husband_name.setText(ibuparent.getDetails().get("namaSuami")!=null?ibuparent.getDetails().get("namaSuami"):"");
        viewHolder.village_name.setText(ibuparent.getDetails().get("desa")!=null?ibuparent.getDetails().get("desa"):"");
        viewHolder.wife_age.setText(ibuparent.getDetails().get("umur")!=null?ibuparent.getDetails().get("umur"):"");
        viewHolder.no_ibu.setText(ibuparent.getDetails().get("noIbu")!=null?ibuparent.getDetails().get("noIbu"):"");
        viewHolder.unique_id.setText(ibuparent.getDetails().get("unique_id")!=null?ibuparent.getDetails().get("unique_id"):"");


        viewHolder.usia_klinis.setText(pc.getDetails().get("usiaKlinis")!=null?pc.getDetails().get("usiaKlinis")+" Minggu":"--");
        viewHolder.htpt.setText(pc.getDetails().get("tanggalHPHT")!=null?pc.getDetails().get("tanggalHPHT"):"--");
        viewHolder.ki_lila_bb.setText(pc.getDetails().get("hasilPemeriksaanLILA")!=null?pc.getDetails().get("hasilPemeriksaanLILA"):"--");

        viewHolder.beratbadan_tb.setText(pc.getDetails().get("hasilPemeriksaanLILA")!=null?pc.getDetails().get("hasilPemeriksaanLILA"):"--");
        viewHolder.anc_penyakit_kronis.setText(pc.getDetails().get("penyakitKronis")!=null?pc.getDetails().get("penyakitKronis"):"--");

        convertView.setLayoutParams(clientViewLayoutParams);
     //   return convertView;
    }
    CommonPersonObjectController householdelcocontroller;


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
        View View = (ViewGroup) inflater().inflate(R.layout.smart_register_ki_anc_client, null);
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
        TextView kb_method;
        TextView usia_klinis;
        TextView htpt;
        TextView ki_lila_bb;
        TextView beratbadan_tb;
        TextView anc_penyakit_kronis;
        TextView kb_mulai;
    }


}