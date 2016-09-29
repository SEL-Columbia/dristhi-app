package org.ei.opensrp.indonesia.kartu_ibu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

import com.google.common.base.Strings;

import org.apache.commons.lang3.StringUtils;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.indonesia.R;

import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.util.DateUtil;
import org.ei.opensrp.view.contract.AlertDTO;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.ECProfilePhotoLoader;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;
import org.ei.opensrp.view.viewHolder.ProfilePhotoLoader;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;
import static org.ei.opensrp.view.controller.ECSmartRegisterController.STATUS_DATE_FIELD;
import static org.ei.opensrp.view.controller.ECSmartRegisterController.STATUS_TYPE_FIELD;
import static org.joda.time.LocalDateTime.parse;

/**
 * Created by Dimas Ciputra on 2/16/15.
 */
public class KIClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {
    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    private Drawable iconPencilDrawable;
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    AlertService alertService;
    public KIClientsProvider(Context context,
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

        if(convertView.getTag() == null || !(convertView.getTag() instanceof  ViewHolder)){
            viewHolder = new ViewHolder();
            viewHolder.profilelayout =  (LinearLayout)convertView.findViewById(R.id.profile_info_layout);
            viewHolder.wife_name = (TextView)convertView.findViewById(R.id.wife_name);
            viewHolder.husband_name = (TextView)convertView.findViewById(R.id.txt_husband_name);
            viewHolder.village_name = (TextView)convertView.findViewById(R.id.txt_village_name);
            viewHolder.wife_age = (TextView)convertView.findViewById(R.id.wife_age);
            viewHolder.no_ibu = (TextView)convertView.findViewById(R.id.no_ibu);
            viewHolder.unique_id = (TextView)convertView.findViewById(R.id.unique_id);

            viewHolder.gravida = (TextView)convertView.findViewById(R.id.txt_gravida);
            viewHolder.parity = (TextView)convertView.findViewById(R.id.txt_parity);
            viewHolder.number_of_abortus = (TextView)convertView.findViewById(R.id.txt_number_of_abortus);
            viewHolder.number_of_alive = (TextView)convertView.findViewById(R.id.txt_number_of_alive);
            viewHolder.hr_badge =(ImageView)convertView.findViewById(R.id.img_hr_badge);
            viewHolder.img_hrl_badge =(ImageView)convertView.findViewById(R.id.img_hrl_badge);
            viewHolder.bpl_badge =(ImageView)convertView.findViewById(R.id.img_bpl_badge);
            viewHolder.hrp_badge =(ImageView)convertView.findViewById(R.id.img_hrp_badge);
            viewHolder.hrpp_badge =(ImageView)convertView.findViewById(R.id.img_hrpp_badge);
            viewHolder.edd = (TextView)convertView.findViewById(R.id.txt_edd);
            viewHolder.edd_due = (TextView)convertView.findViewById(R.id.txt_edd_due);
            viewHolder.children_age_left = (TextView)convertView.findViewById(R.id.txt_children_age_left);
            viewHolder.children_age_right = (TextView)convertView.findViewById(R.id.txt_children_age_right);

            viewHolder.anc_status_layout = (TextView)convertView.findViewById(R.id.mother_status);
            viewHolder.date_status = (TextView)convertView.findViewById(R.id.last_visit_status);
            viewHolder.visit_status = (TextView)convertView.findViewById(R.id.visit_status);
            viewHolder.profilepic =(ImageView)convertView.findViewById(R.id.img_profile);
            viewHolder.follow_up = (ImageButton)convertView.findViewById(R.id.btn_edit);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
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

        // set flag High Risk
        viewHolder.hr_badge.setVisibility(View.INVISIBLE);

        if(pc.getDetails().get("highRiskSTIBBVs")!=null && pc.getDetails().get("highRiskSTIBBVs").equals("yes")
                || pc.getDetails().get("highRiskEctopicPregnancy")!=null && pc.getDetails().get("highRiskEctopicPregnancy").equals("yes")
                || pc.getDetails().get("highRiskCardiovascularDiseaseRecord")!=null && pc.getDetails().get("highRiskDidneyDisorder").equals("yes")
                || pc.getDetails().get("highRiskDidneyDisorder")!=null && pc.getDetails().get("highRiskHeartDisorder").equals("yes")
                || pc.getDetails().get("highRiskHeartDisorder")!=null && pc.getDetails().get("highRiskAsthma").equals("yes")
                || pc.getDetails().get("highRiskAsthma")!=null && pc.getDetails().get("highRiskTuberculosis").equals("yes")
                || pc.getDetails().get("highRiskTuberculosis")!=null && pc.getDetails().get("highRiskMalaria").equals("yes")
                || pc.getDetails().get("highRiskMalaria")!=null && pc.getDetails().get("highRiskMalaria").equals("yes")
                || pc.getDetails().get("highRiskPregnancyYoungMaternalAge")!=null && pc.getDetails().get("highRiskPregnancyYoungMaternalAge").equals("yes")
                || pc.getDetails().get("highRiskPregnancyOldMaternalAge")!=null && pc.getDetails().get("highRiskPregnancyOldMaternalAge").equals("yes") )
        {
            viewHolder.hr_badge.setVisibility(View.VISIBLE);
        }


        //set image
        final ImageView kiview = (ImageView)convertView.findViewById(R.id.img_profile);
        if (pc.getDetails().get("profilepic") != null) {
            KIDetailActivity.setImagetoHolderFromUri((Activity) context, pc.getDetails().get("profilepic"), kiview, R.mipmap.woman_placeholder);
            kiview.setTag(smartRegisterClient);
        }
        else {

                viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.woman_placeholder));
        }
        viewHolder.wife_name.setText(pc.getColumnmaps().get("namalengkap")!=null?pc.getColumnmaps().get("namalengkap"):"");
        viewHolder.husband_name.setText(pc.getColumnmaps().get("namaSuami")!=null?pc.getColumnmaps().get("namaSuami"):"");
        viewHolder.village_name.setText(pc.getDetails().get("desa")!=null?pc.getDetails().get("desa"):"");
        viewHolder.wife_age.setText(pc.getColumnmaps().get("umur")!=null?pc.getColumnmaps().get("umur"):"");
        viewHolder.no_ibu.setText(pc.getColumnmaps().get("noIbu")!=null?pc.getDetails().get("noIbu"):"");
        viewHolder.unique_id.setText(pc.getDetails().get("unique_id")!=null?pc.getDetails().get("unique_id"):"");

        viewHolder.gravida.setText(pc.getDetails().get("gravida")!=null?pc.getDetails().get("gravida"):"-");
        viewHolder.parity.setText(pc.getDetails().get("partus")!=null?pc.getDetails().get("partus"):"-");
        viewHolder.number_of_abortus.setText(pc.getDetails().get("abortus")!=null?pc.getDetails().get("abortus"):"-");
        viewHolder.number_of_alive.setText(pc.getDetails().get("hidup")!=null?pc.getDetails().get("hidup"):"-");

        viewHolder.edd.setText(pc.getColumnmaps().get("htp")!=null?pc.getColumnmaps().get("htp"):"");

        viewHolder.edd_due.setText("");

        String edd = pc.getColumnmaps().get("htp");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotBlank(pc.getColumnmaps().get("htp"))) {
            String _edd = edd;
            String _dueEdd = "";
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            LocalDate date = parse(_edd, formatter).toLocalDate();
            LocalDate dateNow = LocalDate.now();
            date = date.withDayOfMonth(1);
            dateNow = dateNow.withDayOfMonth(1);
            int months = Months.monthsBetween(dateNow, date).getMonths();
            if(months >= 1) {
                viewHolder.edd_due.setTextColor(context.getResources().getColor(R.color.alert_in_progress_blue));
                _dueEdd = "" + months + " " + context.getString(R.string.months_away);
            } else if(months == 0){
                viewHolder.edd_due.setTextColor(context.getResources().getColor(R.color.light_blue));
                _dueEdd =  context.getString(R.string.this_month);
            }
            else if(months < 0 ) {
                if(pc.getColumnmaps().get("ibu.type").equals("anc")){
                    viewHolder.edd_due.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    _dueEdd = context.getString(R.string.edd_passed);
                }
                else if(pc.getColumnmaps().get("ibu.type").equals("pnc")){
                    viewHolder.edd_due.setTextColor(context.getResources().getColor(R.color.alert_complete_green));
                    _dueEdd = context.getString(R.string.delivered);
                }
                else{
                    viewHolder.edd_due.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    _dueEdd = context.getString(R.string.edd_passed);
                }
            }
            viewHolder.edd_due.setText(_dueEdd);

        }
        else{
            viewHolder.edd_due.setText("-");
        }

        viewHolder.children_age_left.setText(pc.getColumnmaps().get("anak.namaBayi")!=null?"Name : "+pc.getColumnmaps().get("anak.namaBayi"):"");
        viewHolder.children_age_right.setText(pc.getColumnmaps().get("anak.tanggalLahirAnak")!=null?"DOB : "+pc.getColumnmaps().get("anak.tanggalLahirAnak"):"");

        viewHolder.anc_status_layout.setText("");
        viewHolder.date_status.setText("");
        viewHolder.visit_status.setText("");

        if(pc.getColumnmaps().get("ibu.type")!=null){
            if(pc.getColumnmaps().get("ibu.type").equals("anc")){
                viewHolder.anc_status_layout.setText(context.getString(R.string.service_anc));
                String visit_date = pc.getColumnmaps().get("ibu.ancDate")!=null?context.getString(R.string.date_visit_title) +" " +pc.getColumnmaps().get("ibu.ancDate"):"";
                String visit_stat = pc.getColumnmaps().get("ibu.ancKe")!=null?context.getString(R.string.anc_ke) +" " + pc.getColumnmaps().get("ibu.ancKe"):"";
                viewHolder.date_status.setText( visit_date);
                viewHolder.visit_status.setText(visit_stat);

            }
            if(pc.getColumnmaps().get("ibu.type").equals("pnc")){
                viewHolder.anc_status_layout.setText(context.getString(R.string.service_pnc));
                String visit_date = pc.getColumnmaps().get("anak.tanggalLahirAnak")!=null?context.getString(R.string.str_pnc_delivery) +" " +pc.getColumnmaps().get("anak.tanggalLahirAnak"):"";
                String hariKeKF = pc.getColumnmaps().get("ibu.hariKeKF")!=null?context.getString(R.string.hari_ke_kf)+" " +pc.getColumnmaps().get("ibu.hariKeKF"):"";

                viewHolder.date_status.setText( visit_date);
                viewHolder.visit_status.setText( hariKeKF);
            }
            if(!pc.getDetails().get("jenisKontrasepsi").equals("")){
                viewHolder.anc_status_layout.setText(context.getString(R.string.service_fp));
                String visit_date = pc.getDetails().get("tanggalkunjungan")!=null?context.getString(R.string.date_visit_title) +" " +pc.getDetails().get("tanggalkunjungan"):"";
                String visit_stat = pc.getDetails().get("jenisKontrasepsi")!=null?context.getString(R.string.fp_methods) +" " + pc.getDetails().get("jenisKontrasepsi"):"";
                viewHolder.date_status.setText( visit_date);
                viewHolder.visit_status.setText(visit_stat);
            }
        }


        viewHolder.hrp_badge.setVisibility(View.INVISIBLE);
        viewHolder.img_hrl_badge.setVisibility(View.INVISIBLE);

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");
        if(pc.getColumnmaps().get("ibu.id") != null) {
            final CommonPersonObject ibuparent = iburep.findByCaseID(pc.getColumnmaps().get("ibu.id"));

            //Risk flag
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
        }
        convertView.setLayoutParams(clientViewLayoutParams);
      //  return convertView;
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
        View View = inflater().inflate(R.layout.smart_register_ki_client, null);
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
        TextView edd;
        TextView edd_due;
        TextView children_age_left;
        TextView anc_status_layout;
         TextView visit_status;
         TextView date_status;
         TextView children_age_right;
        ImageView hr_badge;
        ImageView hrpp_badge;
        ImageView bpl_badge;
        ImageView hrp_badge;
        ImageView img_hrl_badge;
    }


}