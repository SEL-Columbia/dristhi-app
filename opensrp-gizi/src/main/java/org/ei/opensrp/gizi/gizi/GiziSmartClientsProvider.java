package org.ei.opensrp.gizi.gizi;

import android.app.Activity;
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
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import util.KMS.KmsCalc;
import util.KMS.KmsPerson;
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
    static String bindobject = "anak";
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

            viewHolder.absentAlert = (TextView)convertView.findViewById(R.id.absen);
            viewHolder.weightLogo = (ImageView)convertView.findViewById(R.id.weightSymbol);
            viewHolder.heightLogo = (ImageView)convertView.findViewById(R.id.heightSymbol);
            viewHolder.vitALogo = (ImageView)convertView.findViewById(R.id.vitASymbol);
            viewHolder.vitAText = (TextView)convertView.findViewById(R.id.vitASchedule);
            viewHolder.antihelminticLogo = (ImageView)convertView.findViewById(R.id.antihelminticSymbol);
            viewHolder.antihelminticText = (TextView)convertView.findViewById(R.id.antihelminticText);

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
//        System.out.println(pc.getDetails().toString());
        if (iconPencilDrawable == null) {
            iconPencilDrawable = context.getResources().getDrawable(R.drawable.ic_pencil);
        }
        viewHolder.follow_up.setImageDrawable(iconPencilDrawable);
        viewHolder.follow_up.setOnClickListener(onClickListener);
        //set image
        final ImageView childview = (ImageView)convertView.findViewById(R.id.profilepic);

        if (pc.getDetails().get("profilepic") != null) {
                       ChildDetailActivity.setImagetoHolderFromUri((Activity) context, pc.getDetails().get("profilepic"), childview, R.mipmap.child_boy_infant);
                       childview.setTag(smartRegisterClient);
        }
        else {
            if (pc.getDetails().get("jenisKelamin").equalsIgnoreCase("male") || pc.getDetails().get("jenisKelamin").equalsIgnoreCase("laki-laki" )|| pc.getDetails().get("jenisKelamin").equalsIgnoreCase("laki")){
                viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.child_boy_infant));
            } else {
                viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.child_girl_infant));
            }
        }

        viewHolder.name.setText(pc.getDetails().get("namaBayi")!=null?pc.getDetails().get("namaBayi"):"");
        viewHolder.parentname.setText(pc.getDetails().get("namaOrtu")!=null?pc.getDetails().get("namaOrtu"):"");
        viewHolder.age.setText(pc.getDetails().get("tanggalLahir")!=null?pc.getDetails().get("tanggalLahir"):pc.getDetails().get("tanggalLahirAnak")!=null?pc.getDetails().get("tanggalLahirAnak"):"");
        viewHolder.gender.setText(pc.getDetails().get("jenisKelamin").contains("em")? "Perempuan" : "Laki-laki");
        viewHolder.visitDate.setText(context.getString(R.string.tanggal) +  " "+(pc.getDetails().get("tanggalPenimbangan")!=null?pc.getDetails().get("tanggalPenimbangan"):"-"));
        viewHolder.height.setText(context.getString(R.string.height) + " " + (pc.getDetails().get("tinggiBadan") != null ? pc.getDetails().get("tinggiBadan") : "-") + " Cm");
        viewHolder.weight.setText(context.getString(R.string.weight) + " " + (pc.getDetails().get("beratBadan") != null ? pc.getDetails().get("beratBadan") : "-") + " Kg");

//------VISIBLE AND INVISIBLE COMPONENT
        viewHolder.absentAlert.setVisibility(isLate(pc.getDetails().get("tanggalPenimbangan"), 1) ? View.VISIBLE : View.INVISIBLE);
        viewHolder.setVitAVisibility();
        viewHolder.setAntihelminticVisibility(
                dayRangeBetween(pc.getDetails().get("tanggalLahir").split("-")
                        ,new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()).split("-")
                 ) >= 365 ? View.VISIBLE : View.INVISIBLE
        );

//------CHILD DATA HAS BEEN SUBMITTED OR NOT
        viewHolder.weightLogo.setImageDrawable(context.getResources().getDrawable(isLate(pc.getDetails().get("tanggalPenimbangan"),0)?R.drawable.ic_remove:R.drawable.ic_yes_large));
        viewHolder.heightLogo.setImageDrawable(context.getResources().getDrawable(pc.getDetails().get("tinggiBadan") != null
            ? R.drawable.ic_yes_large
            : isLate(pc.getDetails().get("tanggalPenimbangan"), 0)
                ? R.drawable.ic_remove
                : isDue(pc.getDetails().get("tanggalPenimbangan"))
                    ? R.drawable.nutr_due : R.drawable.ic_yes_large)
        );
        viewHolder.vitALogo.setImageDrawable(context.getResources().getDrawable(isGiven(pc,"vitA") ? R.drawable.ic_yes_large:R.drawable.ic_remove));
        viewHolder.antihelminticLogo.setImageDrawable(context.getResources().getDrawable(isGiven(pc,"obatcacing")? R.drawable.ic_yes_large:R.drawable.ic_remove));

        if(pc.getDetails().get("tanggalPenimbangan") != null)
        {
            viewHolder.stunting_status.setText(context.getString(R.string.stunting) +  " "+(hasValue(pc.getDetails().get("stunting"))?pc.getDetails().get("stunting"):"-"));
            viewHolder.underweight.setText(context.getString(R.string.underweight) +  " "+(hasValue(pc.getDetails().get("underweight"))?pc.getDetails().get("underweight"):"-"));
            viewHolder.wasting_status.setText(context.getString(R.string.wasting) +   " "+(hasValue(pc.getDetails().get("wasting"))?pc.getDetails().get("wasting"):"-"));
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

    private boolean isLate(String lastVisitDate,int threshold){
        if (lastVisitDate==null || lastVisitDate.length()<6)
            return true;
        String currentDate[] = new SimpleDateFormat("yyyy-MM").format(new java.util.Date()).substring(0,7).split("-");
        return  ((Integer.parseInt(currentDate[0]) - Integer.parseInt(lastVisitDate.substring(0,4)))*12 +
                (Integer.parseInt(currentDate[1]) - Integer.parseInt(lastVisitDate.substring(5,7)))) > threshold;
    }

    private boolean isDue(String lastVisitDate){
        if(lastVisitDate==null || lastVisitDate.length()<6)
            return true;

        return dayRangeBetween(lastVisitDate.split("-"),new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()).split("-")) <= 30;
    }

    private boolean isGiven(CommonPersonObjectClient pc, String details){
        if(pc.getDetails().get(details) != null)
            return pc.getDetails().get(details).equalsIgnoreCase("ya");
        return false;
    }

    private boolean hasValue(String data){
        if(data == null)
            return false;
        return data.length() > 2;
    }


    /**
     *  The part of method that using to check is the last visit date was in the same region as the current vitamin A period,
     *  a.e. when the last visit was held on july and current month was august,
    **/
    private boolean inTheSameRegion(String date){
        if(date==null || date.length()<6)
            return true;
        int currentDate = Integer.parseInt(new SimpleDateFormat("MM").format(new java.util.Date()));
        int visitDate = Integer.parseInt(date.substring(5, 7));

        boolean date1 = currentDate < 2 || currentDate >=8;
        boolean date2 = visitDate < 2 || visitDate >=8;

        return !((!date1 && date2) || (date1 && !date2));
    }

    private int dayRangeBetween(String[]startDate, String[]endDate){
        return (Integer.parseInt(endDate[0]) - Integer.parseInt(startDate[0]))*360 +
               (Integer.parseInt(endDate[1]) - Integer.parseInt(startDate[1]))*30 +
               (Integer.parseInt(endDate[2]) - Integer.parseInt(startDate[2]));
    }

    @Override
    public SmartRegisterClients getClients() {
        return controller.getClients("form_ditutup","yes");
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
         TextView absentAlert;
         ImageView weightLogo;
         ImageView heightLogo;
         ImageView vitALogo;
         TextView vitAText;
         ImageView antihelminticLogo;
         TextView antihelminticText;

         public void setVitAVisibility(){
             int month = Integer.parseInt(new SimpleDateFormat("MM").format(new java.util.Date()));
             int visibility = month == 2 || month == 8 ? View.VISIBLE : View.INVISIBLE;
//             vitALogo.setVisibility(visibility);
//             vitAText.setVisibility(visibility);
         }

         public void setAntihelminticVisibility(int visibility){
             antihelminticLogo.setVisibility(visibility);
             antihelminticText.setVisibility(visibility);
         }
     }


}

