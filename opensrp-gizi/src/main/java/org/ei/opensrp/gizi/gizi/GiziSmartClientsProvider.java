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
            viewHolder.vitALogo = (ImageView)convertView.findViewById(R.id.vitASymbol);
            viewHolder.vitAText = (TextView)convertView.findViewById(R.id.vitASchedule);

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
        viewHolder.gender.setText(pc.getDetails().get("jenisKelamin")!=null?pc.getDetails().get("jenisKelamin"):"");
        viewHolder.visitDate.setText(context.getString(R.string.tanggal) +  " "+(pc.getDetails().get("tanggalPenimbangan")!=null?pc.getDetails().get("tanggalPenimbangan"):"-"));
        viewHolder.height.setText(context.getString(R.string.height) +  " "+(pc.getDetails().get("tinggiBadan")!=null?pc.getDetails().get("tinggiBadan"):"-")+" Cm");
        viewHolder.weight.setText(context.getString(R.string.weight) +  " "+(pc.getDetails().get("beratBadan")!=null?pc.getDetails().get("beratBadan"):"-")+" Kg");

        /** 
         * Z-SCORE calculation
         * NOTE - Need a better way to handle z-score data to sqllite
         */
        if(pc.getDetails().get("tanggalPenimbangan") != null)
        {
            String gender = pc.getDetails().get("jenisKelamin") != null ? pc.getDetails().get("jenisKelamin") : "-";
            String dateOfBirth = pc.getDetails().get("tanggalLahir") != null ? pc.getDetails().get("tanggalLahir") : "-";
            String lastVisitDate = pc.getDetails().get("tanggalPenimbangan") != null ? pc.getDetails().get("tanggalPenimbangan") : "-";
            double weight=Double.parseDouble(pc.getDetails().get("beratBadan")!=null?pc.getDetails().get("beratBadan"):"0");
            double length=Double.parseDouble(pc.getDetails().get("tinggiBadan")!=null?pc.getDetails().get("tinggiBadan"):"0");
            ZScoreSystemCalculation zScore = new ZScoreSystemCalculation();

            double weight_for_age = zScore.countWFA(gender, dateOfBirth, lastVisitDate, weight);
            String wfaStatus = zScore.getWFAZScoreClassification(weight_for_age);
            if(length != 0) {
                double heigh_for_age = zScore.countHFA(gender, dateOfBirth, lastVisitDate, length);
                String hfaStatus = zScore.getHFAZScoreClassification(heigh_for_age);

                double weight_for_lenght = 0.0;
                String wflStatus = "";
                if (zScore.dailyUnitCalculationOf(dateOfBirth, lastVisitDate) < 730) {
                    weight_for_lenght = zScore.countWFL(gender, weight, length);
                } else {
                    weight_for_lenght = zScore.countWFH(gender, weight, length);
                }
                wflStatus = zScore.getWFLZScoreClassification(weight_for_lenght);
                HashMap <String,String> z_score = new HashMap<String,String>();
                z_score.put("underweight",wfaStatus);
                z_score.put("stunting",hfaStatus);
                z_score.put("wasting",wflStatus);
                org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(pc.entityId(),z_score);
            }
            else {
             //   String hfaStatus = "-";
             //   String wflStatus ="-";
                HashMap<String, String> z_score = new HashMap<String, String>();
                z_score.put("underweight", wfaStatus);
                z_score.put("stunting", "-");
                z_score.put("wasting", "-");
                org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(pc.entityId(), z_score);
            }

            viewHolder.stunting_status.setText(context.getString(R.string.stunting) +  " "+(pc.getDetails().get("stunting")!=null?pc.getDetails().get("stunting"):"-"));
            viewHolder.underweight.setText(context.getString(R.string.underweight) +  " "+(pc.getDetails().get("underweight")!=null?pc.getDetails().get("underweight"):"-"));
            viewHolder.wasting_status.setText(context.getString(R.string.wasting) +   " "+(pc.getDetails().get("wasting")!=null?pc.getDetails().get("wasting"):"-"));
        }
        else{

            viewHolder.underweight.setText(context.getString(R.string.underweight) + " ");
            viewHolder.stunting_status.setText(context.getString(R.string.stunting) +  " ");
            viewHolder.wasting_status.setText(context.getString(R.string.wasting) +  " ");

        }
        //================ END OF Z-SCORE==============================//

        /**
         * kms calculation
         * NOTE - Need a better way to handle z-score data to sqllite
         */
        String berats = pc.getDetails().get("history_berat")!= null ? pc.getDetails().get("history_berat") :"0";
        String[] history_berat = berats.split(",");
        double berat_sebelum = Double.parseDouble((history_berat.length) >=3 ? (history_berat[(history_berat.length)-3]) : "0");
        String umurs = pc.getDetails().get("history_umur")!= null ? pc.getDetails().get("history_umur") :"0";
        String[] history_umur = umurs.split(",");

        boolean jenisKelamin = pc.getDetails().get("jenisKelamin").equalsIgnoreCase("laki-laki")? true:false;
        String tanggal_lahir = pc.getDetails().get("tanggalLahir") != null ? pc.getDetails().get("tanggalLahir") : "0";
        double berat= Double.parseDouble(pc.getDetails().get("beratBadan") != null ? pc.getDetails().get("beratBadan") : "0");
        double beraSebelum = Double.parseDouble((history_berat.length) >=2 ? (history_berat[(history_berat.length)-2]) : "0");
        String tanggal = (pc.getDetails().get("tanggalPenimbangan") != null ? pc.getDetails().get("tanggalPenimbangan") : "0");

        String tanggal_sebelumnya = (pc.getDetails().get("kunjunganSebelumnya") != null ? pc.getDetails().get("kunjunganSebelumnya") : "0");


//------VISIBLE AND INVISIBLE COMPONENT
        viewHolder.absentAlert.setVisibility(isLate(tanggal) ? View.VISIBLE:View.INVISIBLE);
        viewHolder.setVitAVisibility();

        if(pc.getDetails().get("tanggalPenimbangan") != null) {
            //KMS calculation
            KmsPerson data = new KmsPerson(jenisKelamin, tanggal_lahir, berat, beraSebelum, tanggal, berat_sebelum, tanggal_sebelumnya);
            KmsCalc calculator = new KmsCalc();
            int satu = Integer.parseInt(history_umur[history_umur.length-2]);
            int dua = Integer.parseInt(history_umur[history_umur.length-1]);
            String duat = history_berat.length <= 2  ? "-" : dua - satu >=2 ? "-" :calculator.cek2T(data);
            String status = history_berat.length <= 2 ? "Baru" : calculator.cekWeightStatus(data);
            HashMap <String,String> kms = new HashMap<String,String>();
            kms.put("bgm",calculator.cekBGM(data));
            kms.put("dua_t",duat);
            kms.put("garis_kuning",calculator.cekBawahKuning(data));
            kms.put("nutrition_status",status);
            org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(pc.entityId(),kms);
        }
        convertView.setLayoutParams(clientViewLayoutParams);
        return convertView;
    }
    CommonPersonObjectController householdelcocontroller;

    private boolean isLate(String lastVisitDate){
        if (lastVisitDate.length()<6) {
            return true;
        }

        String currentDate[] = new SimpleDateFormat("yyyy-MM").format(new java.util.Date()).substring(0,7).split("-");
        return  ((Integer.parseInt(currentDate[0]) - Integer.parseInt(lastVisitDate.substring(0,4)))*12 +
                (Integer.parseInt(currentDate[1]) - Integer.parseInt(lastVisitDate.substring(5,7)))) > 1;
    }

    private void setVitAVisibility(ViewHolder viewHolder){
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(new java.util.Date()));
        System.out.println(month);
        viewHolder.vitALogo.setVisibility(month == 2 || month == 7 ? View.VISIBLE : View.INVISIBLE);
        viewHolder.vitALogo.setVisibility(month == 2 || month == 7 ? View.VISIBLE : View.INVISIBLE);
    }

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
         TextView absentAlert;
         ImageView vitALogo;
         TextView vitAText;

         public void setVitAVisibility(){
             int month = Integer.parseInt(new SimpleDateFormat("MM").format(new java.util.Date()));
             int visibility = month == 2 || month == 8 ? View.VISIBLE : View.INVISIBLE;
             vitALogo.setVisibility(visibility);
             vitAText.setVisibility(visibility);
         }
     }


}

