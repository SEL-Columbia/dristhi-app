package org.ei.opensrp.gizi.gizi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.gizi.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class GiziSmartClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private Drawable iconPencilDrawable;
    protected CommonPersonObjectController controller;

    AlertService alertService;

    public GiziSmartClientsProvider(Context context,
                                    View.OnClickListener onClickListener,
                                    CommonPersonObjectController controller, AlertService alertService) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.alertService = alertService;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);

    }

    @Override
    public View getView(SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (convertView == null){
           convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_gizi_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);
            viewHolder.name = (TextView)convertView.findViewById(R.id.txt_child_name);
            viewHolder.parentname = (TextView)convertView.findViewById(R.id.ParentName);
            viewHolder.age = (TextView)convertView.findViewById(R.id.txt_child_age);
            viewHolder.gender = (TextView)convertView.findViewById(R.id.txt_child_gender);
            viewHolder.visitDate = (TextView)convertView.findViewById(R.id.txt_child_visit_date);
            viewHolder.height = (TextView)convertView.findViewById(R.id.txt_child_height);
            viewHolder.weight = (TextView)convertView.findViewById(R.id.txt_child_weight);
            viewHolder.underweight = (TextView)convertView.findViewById(R.id.txt_child_underweight);
            viewHolder.stunting_status = (TextView)convertView.findViewById(R.id.txt_child_stunting);
            viewHolder.wasting_status = (TextView)convertView.findViewById(R.id.txt_child_wasting);
            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.profilepic);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);

         //

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.child_boy_infant));
        }

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
        if(pc.getDetails().get("jenisKelamin").equalsIgnoreCase("laki-laki")){
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.child_boy_infant));
        }
        else{
            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.mipmap.child_girl_infant));
        }


        viewHolder.name.setText(pc.getDetails().get("namaBayi")!=null?pc.getDetails().get("namaBayi"):"");
        viewHolder.parentname.setText(pc.getDetails().get("namaOrtu")!=null?pc.getDetails().get("namaOrtu"):"");
        viewHolder.age.setText(pc.getDetails().get("tanggalLahir")!=null?pc.getDetails().get("tanggalLahir"):"");
        viewHolder.gender.setText(pc.getDetails().get("jenisKelamin")!=null?pc.getDetails().get("jenisKelamin"):"");

            viewHolder.visitDate.setText(context.getString(R.string.tanggal) +  " "+(pc.getDetails().get("tanggalPenimbangan")!=null?pc.getDetails().get("tanggalPenimbangan"):"-"));


        double data_tinggi[] = new double [13];
        data_tinggi[0] = 0;
        data_tinggi[1] = Double.parseDouble(pc.getDetails().get("tinggiBadan1") != null ? pc.getDetails().get("tinggiBadan1") : "0");
        data_tinggi[2] = Double.parseDouble(pc.getDetails().get("tinggiBadan2") != null ? pc.getDetails().get("tinggiBadan2") : "0");
        data_tinggi[3] = Double.parseDouble(pc.getDetails().get("tinggiBadan3") != null ? pc.getDetails().get("tinggiBadan3") : "0");
        data_tinggi[4] = Double.parseDouble(pc.getDetails().get("tinggiBadan4") != null ? pc.getDetails().get("tinggiBadan4") : "0");
        data_tinggi[5] = Double.parseDouble(pc.getDetails().get("tinggiBadan5") != null ? pc.getDetails().get("tinggiBadan5") : "0");
        data_tinggi[6] = Double.parseDouble(pc.getDetails().get("tinggiBadan6") != null ? pc.getDetails().get("tinggiBadan6") : "0");
        data_tinggi[7] = Double.parseDouble(pc.getDetails().get("tinggiBadan7") != null ? pc.getDetails().get("tinggiBadan7") : "0");
        data_tinggi[8] = Double.parseDouble(pc.getDetails().get("tinggiBadan8") != null ? pc.getDetails().get("tinggiBadan8") : "0");
        data_tinggi[9] = Double.parseDouble(pc.getDetails().get("tinggiBadan9") != null ? pc.getDetails().get("tinggiBadan9") : "0");
        data_tinggi[10] = Double.parseDouble(pc.getDetails().get("tinggiBadan10") != null ? pc.getDetails().get("tinggiBadan10") : "0");
        data_tinggi[11] = Double.parseDouble(pc.getDetails().get("tinggiBadan11") != null ? pc.getDetails().get("tinggiBadan11") : "0");
        data_tinggi[12] = Double.parseDouble(pc.getDetails().get("tinggiBadan12") != null ? pc.getDetails().get("tinggiBadan12") : "0");

        //data for graph
        double datas[] = new double [13];
        datas[0] = 0;
        datas[1] = Double.parseDouble(pc.getDetails().get("beratBadan1") != null ? pc.getDetails().get("beratBadan1") : "0");
        datas[2] = Double.parseDouble(pc.getDetails().get("beratBadan2") != null ? pc.getDetails().get("beratBadan2") : "0");
        datas[3] = Double.parseDouble(pc.getDetails().get("beratBadan3") != null ? pc.getDetails().get("beratBadan3") : "0");
        datas[4] = Double.parseDouble(pc.getDetails().get("beratBadan4") != null ? pc.getDetails().get("beratBadan4") : "0");
        datas[5] = Double.parseDouble(pc.getDetails().get("beratBadan5") != null ? pc.getDetails().get("beratBadan5") : "0");
        datas[6] = Double.parseDouble(pc.getDetails().get("beratBadan6") != null ? pc.getDetails().get("beratBadan6") : "0");
        datas[7] = Double.parseDouble(pc.getDetails().get("beratBadan7") != null ? pc.getDetails().get("beratBadan7") : "0");
        datas[8] = Double.parseDouble(pc.getDetails().get("beratBadan8") != null ? pc.getDetails().get("beratBadan8") : "0");
        datas[9] = Double.parseDouble(pc.getDetails().get("beratBadan9") != null ? pc.getDetails().get("beratBadan9") : "0");
        datas[10] = Double.parseDouble(pc.getDetails().get("beratBadan10") != null ? pc.getDetails().get("beratBadan10") : "0");
        datas[11] = Double.parseDouble(pc.getDetails().get("beratBadan11") != null ? pc.getDetails().get("beratBadan11") : "0");
        datas[12] = Double.parseDouble(pc.getDetails().get("beratBadan12") != null ? pc.getDetails().get("beratBadan12") : "0");


        //fungtion break if found 0 data.
        int counter=0;
        for(int i=0;i<datas.length;i++){
            if(datas[i+1]==0)
                break;
            counter++;
        }
            viewHolder.height.setText(context.getString(R.string.weight)+ " "+datas[counter]+" Cm");
            viewHolder.weight.setText(context.getString(R.string.height) +  " "+data_tinggi[counter]+" Kg");

    /*
        if(pc.getDetails().get("status_gizi")!=null) {
            if(pc.getDetails().get("status_gizi").equalsIgnoreCase("N")) {
                viewHolder.underweight.setText("Status Gizi : Naik");
            }
            if(pc.getDetails().get("status_gizi").equalsIgnoreCase("T")) {
                viewHolder.underweight.setText("Status Gizi : Tidak Naik");
            }
            if(pc.getDetails().get("status_gizi").equalsIgnoreCase("B")) {
                viewHolder.underweight.setText("Status Gizi : Baru");
            }
            if(pc.getDetails().get("status_gizi").equalsIgnoreCase("O")) {
                viewHolder.underweight.setText("Status Gizi : Tidak Hadir bulan lalu");
            }
        }
        else{
            viewHolder.underweight.setText("Status Gizi : ");
        }
        */
        viewHolder.underweight.setText(context.getString(R.string.underweight) + " "+(pc.getDetails().get("underweight_status")!=null?pc.getDetails().get("underweight_status"):"-"));

        viewHolder.stunting_status.setText(context.getString(R.string.stunting) +  " "+(pc.getDetails().get("Stunting_status")!=null?pc.getDetails().get("Stunting_status"):"-"));
        viewHolder.wasting_status.setText(context.getString(R.string.wasting) +  " "+(pc.getDetails().get("wasting_status")!=null?pc.getDetails().get("wasting_status"):"-"));




        convertView.setLayoutParams(clientViewLayoutParams);
        return convertView;
    }
    CommonPersonObjectController householdelcocontroller;





    @Override
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

     class ViewHolder {

         TextView name ;
         TextView age ;
         TextView village;
         TextView husbandname;
         LinearLayout profilelayout;
         ImageView profilepic;
         FrameLayout due_date_holder;
         Button warnbutton;
         ImageButton follow_up;
         TextView parentname;
         TextView gender;
         TextView visitDate;
         TextView height;
         TextView weight;
         TextView underweight;
         TextView stunting_status;
         TextView wasting_status;
     }


}

