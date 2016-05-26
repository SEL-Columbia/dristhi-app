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

import util.ZScore.ZScoreSystemCalculation;

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
        viewHolder.height.setText(context.getString(R.string.height) +  " "+(pc.getDetails().get("tinggiBadan")!=null?pc.getDetails().get("tinggiBadan"):"-")+" Cm");
        viewHolder.weight.setText(context.getString(R.string.weight) +  " "+(pc.getDetails().get("beratBadan1")!=null?pc.getDetails().get("beratBadan1"):"-")+" Kg");

      //==========================================Z-SCORE===============================================//
        if(pc.getDetails().get("tanggalPenimbangan") != null)
        {
            String gender = pc.getDetails().get("jenisKelamin") != null ? pc.getDetails().get("jenisKelamin") : "-";
            String dateOfBirth = pc.getDetails().get("tanggalLahir") != null ? pc.getDetails().get("tanggalLahir") : "-";
            String lastVisitDate = pc.getDetails().get("tanggalPenimbangan") != null ? pc.getDetails().get("tanggalPenimbangan") : "-";
            double weight=Double.parseDouble(pc.getDetails().get("beratBadan1")!=null?pc.getDetails().get("beratBadan1"):"0");
            double length=Double.parseDouble(pc.getDetails().get("tinggiBadan")!=null?pc.getDetails().get("tinggiBadan"):"0");
            ZScoreSystemCalculation zScore = new ZScoreSystemCalculation();

            double weight_for_age = zScore.countWFA(gender, dateOfBirth, lastVisitDate, weight);
            String wfaStatus = zScore.getWFAZScoreClassification(weight_for_age);

            double heigh_for_age = zScore.countHFA(gender, dateOfBirth, lastVisitDate, length);
            String hfaStatus = zScore.getHFAZScoreClassification(heigh_for_age);

            double wight_for_lenght=0.0;
            String wflStatus="";
            if(zScore.dailyUnitCalculationOf(dateOfBirth, lastVisitDate)<730){
                wight_for_lenght = zScore.countWFL(gender, weight, length);
            }
            else{
                wight_for_lenght = zScore.countWFH(gender, weight, length);
            }
            wflStatus = zScore.getWFLZScoreClassification(wight_for_lenght);

            //set to view
            viewHolder.underweight.setText(context.getString(R.string.underweight) + " "+wfaStatus);
            viewHolder.stunting_status.setText(context.getString(R.string.stunting) +  " "+hfaStatus);
            viewHolder.wasting_status.setText(context.getString(R.string.wasting) +  " "+wflStatus);

        }
        else{

            viewHolder.underweight.setText(context.getString(R.string.underweight) + " ");
            viewHolder.stunting_status.setText(context.getString(R.string.stunting) +  " ");
            viewHolder.wasting_status.setText(context.getString(R.string.wasting) +  " ");

        }
        //================ END OF Z-SCORE==============================//

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

