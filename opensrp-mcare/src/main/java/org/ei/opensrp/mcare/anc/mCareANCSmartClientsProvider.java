package org.ei.opensrp.mcare.anc;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.mcare.household.HouseHoldDetailActivity;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.util.DateUtil;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.customControls.CustomFontTextView;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class mCareANCSmartClientsProvider implements SmartRegisterClientsProvider {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    public mCareANCSmartClientsProvider(Context context,
                                        View.OnClickListener onClickListener,
                                        CommonPersonObjectController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);
    }

    @Override
    public View getView(final SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;

        itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_mcare_anc_client, null);
//        LinearLayout elcodetails = (LinearLayout)itemView.findViewById(R.id.profile_info_layout);
//        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView name = (TextView)itemView.findViewById(R.id.name);
        TextView spousename = (TextView)itemView.findViewById(R.id.spousename);
        TextView gobhhid = (TextView)itemView.findViewById(R.id.gobhhid);
        TextView jivitahhid = (TextView)itemView.findViewById(R.id.jivitahhid);
        TextView village = (TextView)itemView.findViewById(R.id.village);
        TextView age = (TextView)itemView.findViewById(R.id.age);
        TextView nid = (TextView)itemView.findViewById(R.id.nid);
        TextView brid = (TextView)itemView.findViewById(R.id.brid);
        TextView edd = (TextView)itemView.findViewById(R.id.edd);
//        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
//        elcodetails.setOnClickListener(onClickListener);
//        elcodetails.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

//        if(pc.getDetails().get("profilepic")!=null){
//            HouseHoldDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"), profilepic, R.mipmap.woman_placeholder);
//        }
//
//        id.setText(pc.getDetails().get("case_id")!=null?pc.getCaseId():"");
        name.setText(pc.getDetails().get("FWWOMFNAME")!=null?pc.getDetails().get("FWWOMFNAME"):"");
        spousename.setText(pc.getDetails().get("FWHUSNAME")!=null?pc.getDetails().get("FWHUSNAME"):"");
        gobhhid.setText(pc.getDetails().get("GOBHHID")!=null?pc.getDetails().get("GOBHHID"):"");
        jivitahhid.setText(pc.getDetails().get("JiVitAHHID")!=null?pc.getDetails().get("JiVitAHHID"):"");
        village.setText(humanize((pc.getDetails().get("mauza") != null ? pc.getDetails().get("mauza") : "").replace("+", "_")));
//
//
//
        age.setText(pc.getDetails().get("FWWOMAGE")!=null?pc.getDetails().get("FWWOMAGE"):"");
        nid.setText("NID :" +(pc.getDetails().get("FWWOMNID")!=null?pc.getDetails().get("FWWOMNID"):""));
        brid.setText("BRID :" +(pc.getDetails().get("FWWOMBID")!=null?pc.getDetails().get("FWWOMBID"):""));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date edd_date = format.parse(pc.getDetails().get("FWPSRLMP")!=null?pc.getDetails().get("FWPSRLMP"):"");
            GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(edd_date);
                calendar.add(Calendar.DATE, 259);
                edd_date.setTime(calendar.getTime().getTime());
            edd.setText("EDD :" + format.format(edd_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        constructRiskFlagView(pc,itemView);
        constructANCReminderDueBlock(pc,itemView);
        constructNBNFDueBlock(pc, itemView);




        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
    }

    private void constructNBNFDueBlock(CommonPersonObjectClient pc, ViewGroup itemView) {
        alertTextandStatus alerttextstatus = null;
        List <Alert>alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(),"BirthNotificationPregnancyStatusFollowUp");
        if(alertlist.size() != 0){
            alerttextstatus = setAlertStatus("",alertlist);
        }else{
            alerttextstatus = new alertTextandStatus("Not synced","not synced");
        }
        CustomFontTextView NBNFDueDate = (CustomFontTextView)itemView.findViewById(R.id.nbnf_due_date);
        setalerttextandColorInView(NBNFDueDate, alerttextstatus, pc);

    }

    private void constructANCReminderDueBlock(CommonPersonObjectClient pc, ViewGroup itemView) {
        alertTextandStatus alerttextstatus = null;
        List <Alert>alertlist4 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(),"ancrv_4");
        if(alertlist4.size() != 0){
            alerttextstatus = setAlertStatus("ANC4",alertlist4);
        }else {
            List<Alert> alertlist3 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "ancrv_3");
            if(alertlist3.size() != 0){
                alerttextstatus = setAlertStatus("ANC3",alertlist3);
            }else{
                List<Alert> alertlist2 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "ancrv_2");
                if(alertlist2.size()!=0){
                    alerttextstatus = setAlertStatus("ANC2",alertlist2);
                }else{
                    List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "ancrv_1");
                    if(alertlist.size()!=0){
                        alerttextstatus = setAlertStatus("ANC1",alertlist);
                    }else{
                        alerttextstatus = new alertTextandStatus("Not synced","not synced");
                    }
                }
            }
        }
        CustomFontTextView ancreminderDueDate = (CustomFontTextView)itemView.findViewById(R.id.anc_reminder_due_date);
        setalerttextandColorInView(ancreminderDueDate, alerttextstatus,pc);


    }

    private void setalerttextandColorInView(CustomFontTextView customFontTextView, alertTextandStatus alerttextstatus, CommonPersonObjectClient pc) {
        customFontTextView.setText(alerttextstatus.getAlertText() != null ? alerttextstatus.getAlertText() : "");
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("normal")){
            customFontTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("upcoming")){
            customFontTextView.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            customFontTextView.setOnClickListener(onClickListener);
            customFontTextView.setTag(pc);

        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("urgent")){
            customFontTextView.setOnClickListener(onClickListener);
            customFontTextView.setTag(pc);
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("expired")){
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
            customFontTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("complete")){
//               psrfdue.setText("visited");
            customFontTextView.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
            customFontTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("not synced")){
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
//
        }
    }

    private alertTextandStatus setAlertStatus(String anc, List<Alert> alertlist) {
        alertTextandStatus alts = null;
        for(int i = 0;i<alertlist.size();i++){
            alts = new alertTextandStatus(anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value());
            }
        return alts;
    }

    private void constructRiskFlagView(CommonPersonObjectClient pc, ViewGroup itemView) {
        ImageView hrp = (ImageView)itemView.findViewById(R.id.hrp);
        ImageView hp = (ImageView)itemView.findViewById(R.id.hr);
        ImageView vg = (ImageView)itemView.findViewById(R.id.vg);
        hrp.setVisibility(View.GONE);
        hp.setVisibility(View.GONE);
        vg.setVisibility(View.GONE);
//        if(pc.getDetails().get("FWWOMAGE")!=null &&)

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

    class alertTextandStatus{
        String alertText ,alertstatus;

        public alertTextandStatus(String alertText, String alertstatus) {
            this.alertText = alertText;
            this.alertstatus = alertstatus;
        }

        public String getAlertText() {
            return alertText;
        }

        public void setAlertText(String alertText) {
            this.alertText = alertText;
        }

        public String getAlertstatus() {
            return alertstatus;
        }

        public void setAlertstatus(String alertstatus) {
            this.alertstatus = alertstatus;
        }
    }
}
