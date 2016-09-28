package org.ei.opensrp.indonesia.anc;

import android.app.Activity;
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

import org.apache.commons.lang3.StringUtils;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.indonesia.R;
import org.ei.opensrp.indonesia.kartu_ibu.KIDetailActivity;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.util.DateUtil;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.joda.time.LocalDateTime.parse;

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
        if(convertView.getTag() == null || !(convertView.getTag() instanceof  ViewHolder)) {
            viewHolder = new ViewHolder();
            viewHolder.profilelayout = (LinearLayout) convertView.findViewById(R.id.profile_info_layout);

            viewHolder.wife_name = (TextView) convertView.findViewById(R.id.wife_name);
            viewHolder.husband_name = (TextView) convertView.findViewById(R.id.txt_husband_name);
            viewHolder.village_name = (TextView) convertView.findViewById(R.id.txt_village_name);
            viewHolder.wife_age = (TextView) convertView.findViewById(R.id.wife_age);
            viewHolder.no_ibu = (TextView) convertView.findViewById(R.id.no_ibu);
            viewHolder.unique_id = (TextView) convertView.findViewById(R.id.unique_id);

            viewHolder.hr_badge = (ImageView) convertView.findViewById(R.id.img_hr_badge);
            viewHolder.img_hrl_badge = (ImageView) convertView.findViewById(R.id.img_hrl_badge);
            viewHolder.bpl_badge = (ImageView) convertView.findViewById(R.id.img_bpl_badge);
            viewHolder.hrp_badge = (ImageView) convertView.findViewById(R.id.img_hrp_badge);
            viewHolder.hrpp_badge = (ImageView) convertView.findViewById(R.id.img_hrpp_badge);
            // ViewHolder.img_hp_badge = ImageView.Set;   img_hrl_badge img_bpl_badge img_hrp_badge img_hrpp_badge
            viewHolder.usia_klinis = (TextView) convertView.findViewById(R.id.txt_usia_klinis);
            viewHolder.htpt = (TextView) convertView.findViewById(R.id.txt_htpt);
            viewHolder.edd_due = (TextView) convertView.findViewById(R.id.txt_edd_due);
            viewHolder.ki_lila_bb = (TextView) convertView.findViewById(R.id.txt_ki_lila_bb);
            viewHolder.beratbadan_tb = (TextView) convertView.findViewById(R.id.txt_ki_beratbadan_tb);
            viewHolder.tanggal_kunjungan_anc = (TextView) convertView.findViewById(R.id.txt_tanggal_kunjungan_anc);
            viewHolder.anc_number = (TextView) convertView.findViewById(R.id.txt_anc_number);
            viewHolder.kunjugan_ke = (TextView) convertView.findViewById(R.id.txt_kunjugan_ke);

            viewHolder.status_layout = (RelativeLayout) convertView.findViewById(R.id.anc_status_layout);
            viewHolder.status_type = (TextView) convertView.findViewById(R.id.txt_status_type);
            viewHolder.status_date = (TextView) convertView.findViewById(R.id.txt_status_date_anc);
            viewHolder.alert_status = (TextView) convertView.findViewById(R.id.txt_alert_status);

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



        String KunjunganKe = pc.getDetails().get("kunjunganKe")!=null?pc.getDetails().get("kunjunganKe"):"-";

        AllCommonsRepository kiRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("ibu");

        CommonPersonObject kiobject = kiRepository.findByCaseID(pc.entityId());

        AllCommonsRepository iburep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("kartu_ibu");
        final CommonPersonObject ibuparent = iburep.findByCaseID(kiobject.getColumnmaps().get("kartuIbuId"));

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
        if(kiobject.getDetails().get("highRiskPregnancyPIH")!=null && kiobject.getDetails().get("highRiskPregnancyPIH").equals("yes")
                || kiobject.getDetails().get("highRiskPregnancyProteinEnergyMalnutrition")!=null && kiobject.getDetails().get("highRiskPregnancyProteinEnergyMalnutrition").equals("yes")
                || kiobject.getDetails().get("highRiskPregnancyPIH")!=null && kiobject.getDetails().get("highRiskPregnancyPIH").equals("yes")
                || kiobject.getDetails().get("highRiskPregnancyDiabetes")!=null && kiobject.getDetails().get("highRiskPregnancyDiabetes").equals("yes")
                || kiobject.getDetails().get("highRiskPregnancyAnemia")!=null && kiobject.getDetails().get("highRiskPregnancyAnemia").equals("yes") )
        {
            viewHolder.hrp_badge.setVisibility(View.VISIBLE);
        }
        if(kiobject.getDetails().get("highRiskLabourFetusMalpresentation")!=null && kiobject.getDetails().get("highRiskLabourFetusMalpresentation").equals("yes")
                || kiobject.getDetails().get("highRiskLabourFetusSize")!=null && kiobject.getDetails().get("highRiskLabourFetusSize").equals("yes")
                || kiobject.getDetails().get("highRisklabourFetusNumber")!=null && kiobject.getDetails().get("highRisklabourFetusNumber").equals("yes")
                || kiobject.getDetails().get("HighRiskLabourSectionCesareaRecord")!=null && kiobject.getDetails().get("HighRiskLabourSectionCesareaRecord").equals("yes")
                || ibuparent.getDetails().get("highRiskLabourTBRisk") != null && ibuparent.getDetails().get("highRiskLabourTBRisk").equals("yes") )
        {
            viewHolder.img_hrl_badge.setVisibility(View.VISIBLE);
        }

        final ImageView kiview = (ImageView)convertView.findViewById(R.id.img_profile);
        if (ibuparent.getDetails().get("profilepic") != null) {
            ANCDetailActivity.setImagetoHolderFromUri((Activity) context, ibuparent.getDetails().get("profilepic"), kiview, R.mipmap.woman_placeholder);
            kiview.setTag(smartRegisterClient);
        }
        else {

            viewHolder.profilepic.setImageDrawable(context.getResources().getDrawable(R.drawable.woman_placeholder));

        }

        viewHolder.wife_name.setText(ibuparent.getColumnmaps().get("namalengkap")!=null?ibuparent.getColumnmaps().get("namalengkap"):"");
        viewHolder.husband_name.setText(ibuparent.getColumnmaps().get("namaSuami")!=null?ibuparent.getColumnmaps().get("namaSuami"):"");
        viewHolder.village_name.setText(ibuparent.getDetails().get("desa")!=null?ibuparent.getDetails().get("desa"):"");
        viewHolder.wife_age.setText(ibuparent.getColumnmaps().get("umur")!=null?ibuparent.getColumnmaps().get("umur"):"");
        viewHolder.no_ibu.setText(ibuparent.getColumnmaps().get("noIbu")!=null?ibuparent.getColumnmaps().get("noIbu"):"");
        viewHolder.unique_id.setText(ibuparent.getDetails().get("unique_id")!=null?ibuparent.getDetails().get("unique_id"):"");

        viewHolder.usia_klinis.setText(pc.getDetails().get("usiaKlinis")!=null?context.getString(R.string.usia)+pc.getDetails().get("usiaKlinis")+context.getString(R.string.str_weeks):"-");
        viewHolder.htpt.setText(ibuparent.getColumnmaps().get("htp")!=null?ibuparent.getColumnmaps().get("htp"):"-");

        String edd = ibuparent.getColumnmaps().get("htp");
        if(StringUtils.isNotBlank(ibuparent.getColumnmaps().get("htp"))) {
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
            else if(months < 0) {
                viewHolder.edd_due.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                _dueEdd = context.getString(R.string.edd_passed);
            }

            viewHolder.edd_due.setText(_dueEdd);

        }
        else{
            viewHolder.edd_due.setText("-");
        }


        viewHolder.ki_lila_bb.setText(pc.getDetails().get("hasilPemeriksaanLILA")!=null?pc.getDetails().get("hasilPemeriksaanLILA"):"-");

        viewHolder.beratbadan_tb.setText(pc.getDetails().get("bbKg")!=null?pc.getDetails().get("bbKg"):"-");

        String AncDate = kiobject.getColumnmaps().get("ancDate")!=null?kiobject.getColumnmaps().get("ancDate"):"-";
        String AncKe = kiobject.getColumnmaps().get("ancKe")!=null?kiobject.getColumnmaps().get("ancKe"):"-";

        viewHolder.tanggal_kunjungan_anc.setText(context.getString(R.string.last_visit_date)+ AncDate);
        viewHolder.anc_number.setText(context.getString(R.string.anc_ke) + AncKe);
        viewHolder.kunjugan_ke.setText(context.getString(R.string.visit_number) +KunjunganKe);

        viewHolder.status_type.setText("");
        viewHolder.status_date.setText("");
        viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
        viewHolder.alert_status.setText("");

        if(AncKe.equals("-") || AncKe.equals("")){
            viewHolder.status_type.setText("ANC");
        }
        if(AncKe.equals("1")){
            viewHolder.status_type.setText("ANC 2");
            List<Alert> alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "ANC 2");
            //alertlist_for_client.get(i).
            if(alertlist_for_client.size() == 0 ){
                //  viewHolder.due_visit_date.setText("Not Synced to Server");
                viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
            }
            for(int i = 0;i<alertlist_for_client.size();i++){

                //  viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());

                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());

                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
                if(alertlist_for_client.get(i).isComplete()){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
            }
        }

        if(AncKe.equals("2")){
            viewHolder.status_type.setText("ANC 3");
            List<Alert> alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "ANC 3");
            //alertlist_for_client.get(i).
            if(alertlist_for_client.size() == 0 ){
                //  viewHolder.due_visit_date.setText("Not Synced to Server");
                viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
            }
            for(int i = 0;i<alertlist_for_client.size();i++){

                // viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());

                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());

                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
                if(alertlist_for_client.get(i).isComplete()){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
            }
        }
        if(AncKe.equals("3")){
            viewHolder.status_type.setText("ANC 4");
            List<Alert> alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "ANC 4");
            //alertlist_for_client.get(i).
            if(alertlist_for_client.size() == 0 ){
                //  viewHolder.due_visit_date.setText("Not Synced to Server");
                viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
            }
            for(int i = 0;i<alertlist_for_client.size();i++){

                //   viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());

                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());

                }
                if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
                if(alertlist_for_client.get(i).isComplete()){
                    viewHolder.status_date.setText(alertlist_for_client.get(i).startDate());
                    viewHolder.status_layout.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
                    viewHolder.alert_status.setText(alertlist_for_client.get(i).status().value());
                }
            }
        }


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
        TextView edd_due;
    }


}