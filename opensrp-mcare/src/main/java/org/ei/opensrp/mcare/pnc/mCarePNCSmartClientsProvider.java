package org.ei.opensrp.mcare.pnc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.customControls.CustomFontTextView;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

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
public class mCarePNCSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private final AlertService alertService;


    public mCarePNCSmartClientsProvider(Context context,
                                        View.OnClickListener onClickListener,
                                        AlertService alertService) {
        this.onClickListener = onClickListener;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.alertService = alertService;
        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);
    }

    @Override
    public void getView(final SmartRegisterClient smartRegisterClient, View convertView) {
        View itemView;

        itemView = convertView;

//        itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_mcare_pnc_client, null);
        LinearLayout profileinfolayout = (LinearLayout)itemView.findViewById(R.id.profile_info_layout);

//        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView name = (TextView)itemView.findViewById(R.id.name);
        TextView spousename = (TextView)itemView.findViewById(R.id.spousename);
        TextView gobhhid = (TextView)itemView.findViewById(R.id.gobhhid);
        TextView jivitahhid = (TextView)itemView.findViewById(R.id.jivitahhid);
        TextView village = (TextView)itemView.findViewById(R.id.village);
        TextView age = (TextView)itemView.findViewById(R.id.age);
        TextView nid = (TextView)itemView.findViewById(R.id.nid);
        TextView brid = (TextView)itemView.findViewById(R.id.brid);
        TextView delivery_outcome = (TextView)itemView.findViewById(R.id.deliveryoutcome);
        TextView dateofdelivery = (TextView)itemView.findViewById(R.id.date_of_outcome);

//        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        profileinfolayout.setOnClickListener(onClickListener);
        profileinfolayout.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

//        if(pc.getDetails().get("profilepic")!=null){
//            HouseHoldDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"), profilepic, R.mipmap.woman_placeholder);
//        }
//
//        id.setText(pc.getDetails().get("case_id")!=null?pc.getCaseId():"");
        name.setText(pc.getColumnmaps().get("FWWOMFNAME")!=null?pc.getColumnmaps().get("FWWOMFNAME"):"");
        spousename.setText(pc.getDetails().get("FWHUSNAME")!=null?pc.getDetails().get("FWHUSNAME"):"");
        gobhhid.setText(pc.getColumnmaps().get("GOBHHID")!=null?pc.getColumnmaps().get("GOBHHID"):"");
        jivitahhid.setText(pc.getColumnmaps().get("JiVitAHHID")!=null?pc.getColumnmaps().get("JiVitAHHID"):"");
        village.setText(humanize((pc.getDetails().get("mauza") != null ? pc.getDetails().get("mauza") : "").replace("+", "_")));
//
//
//
        age.setText(pc.getDetails().get("FWWOMAGE")!=null?pc.getDetails().get("FWWOMAGE"):"");
        dateofdelivery.setText(pc.getColumnmaps().get("FWBNFDTOO")!=null?pc.getColumnmaps().get("FWBNFDTOO"):"");
        String outcomevalue = pc.getColumnmaps().get("FWBNFSTS")!=null?pc.getColumnmaps().get("FWBNFSTS"):"";

        if(outcomevalue.equalsIgnoreCase("3")){
            delivery_outcome.setText(context.getString(R.string.mcare_pnc_liveBirth));
        }else if (outcomevalue.equalsIgnoreCase("4")){
            delivery_outcome.setText(context.getString(R.string.mcare_pnc_Stillbirth));
        }


//        delivery_outcome.setText(pc.getDetails().get("FWBNFSTS")!=null?pc.getDetails().get("FWBNFSTS"):"");


        nid.setText("NID :" +(pc.getDetails().get("FWWOMNID")!=null?pc.getDetails().get("FWWOMNID"):""));
        brid.setText("BRID :" +(pc.getDetails().get("FWWOMBID")!=null?pc.getDetails().get("FWWOMBID"):""));

          try {
                    dateofdelivery.setText("DOO :" + (pc.getColumnmaps().get("FWBNFDTOO") != null ? pc.getColumnmaps().get("FWBNFDTOO") : "").split("T")[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        constructRiskFlagView(pc,itemView);
        constructPNCReminderDueBlock((pc.getColumnmaps().get("FWBNFDTOO") != null ? pc.getColumnmaps().get("FWBNFDTOO") : "").split("T")[0],pc, itemView);
//        constructNBNFDueBlock(pc, itemView);s
        constructPncVisitStatusBlock(pc,itemView);




        itemView.setLayoutParams(clientViewLayoutParams);
//        return itemView;
    }

    private void constructPncVisitStatusBlock(CommonPersonObjectClient pc, View itemview) {
        TextView anc1tick = (TextView)itemview.findViewById(R.id.pnc1tick);
        TextView anc1text = (TextView)itemview.findViewById(R.id.pnc1text);
        TextView anc2tick = (TextView)itemview.findViewById(R.id.pnc2tick);
        TextView anc2text = (TextView)itemview.findViewById(R.id.pnc2text);
        TextView anc3tick = (TextView)itemview.findViewById(R.id.pnc3tick);
        TextView anc3text = (TextView)itemview.findViewById(R.id.pnc3text);
//        TextView anc4tick = (TextView)itemview.findViewById(R.id.pnc4tick);
//        TextView anc4text = (TextView)itemview.findViewById(R.id.pnc4text);
        checkPnc1StatusAndform(anc1tick,anc1text,pc);
        checkPnc2StatusAndform(anc2tick, anc2text, pc);
        checkPnc3StatusAndform(anc3tick, anc3text, pc);


    }



    private void checkPnc1StatusAndform(TextView anc1tick, TextView anc1text, CommonPersonObjectClient pc) {
        if(pc.getDetails().get("FWPNC1DATE")!=null){
            anc1text.setText("PNC1-"+pc.getDetails().get("FWPNC1DATE"));
            if(pc.getDetails().get("pnc1_current_formStatus")!=null){
                if(pc.getDetails().get("pnc1_current_formStatus").equalsIgnoreCase("upcoming")){

                }else if(pc.getDetails().get("pnc1_current_formStatus").equalsIgnoreCase("urgent")){
                    anc1tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc1text.setText("urgent");
                }
            }
        }else{
            List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "pncrv_1");
            String alertstate = "";
            String alertDate = "";
            if(alertlist.size()!=0){
                for(int i = 0;i<alertlist.size();i++){
                    alertstate = alertlist.get(i).status().value();
                    alertDate = alertlist.get(i).startDate();
                }              ;
            }
            if(alertstate != null && !(alertstate.trim().equalsIgnoreCase(""))){
                if(alertstate.equalsIgnoreCase("expired")){
                    anc1tick.setText("✘");
                    anc1tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc1text.setText( "PNC1-" + alertDate);
//                    (anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value())
                }else {
                    anc1text.setVisibility(View.GONE);
                    anc1tick.setVisibility(View.GONE);
                }
            } else {
                anc1text.setVisibility(View.GONE);
                anc1tick.setVisibility(View.GONE);
            }
        }
    }
    private void checkPnc2StatusAndform(TextView anc2tick, TextView anc2text, CommonPersonObjectClient pc) {
        if(pc.getDetails().get("FWPNC2DATE")!=null){
            anc2text.setText("PNC2-"+pc.getDetails().get("FWPNC2DATE"));
            if(pc.getDetails().get("pnc2_current_formStatus")!=null){
                if(pc.getDetails().get("pnc2_current_formStatus").equalsIgnoreCase("upcoming")){

                }else if(pc.getDetails().get("pnc2_current_formStatus").equalsIgnoreCase("urgent")){
                    anc2tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                }
            }
        }else{
            List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "pncrv_2");
            String alertstate = "";
            String alertDate = "";
            if(alertlist.size()!=0){
                for(int i = 0;i<alertlist.size();i++){
                    alertstate = alertlist.get(i).status().value();
                    alertDate = alertlist.get(i).startDate();
                }              ;
            }
            if(alertstate != null && !(alertstate.trim().equalsIgnoreCase(""))){
                if(alertstate.equalsIgnoreCase("expired")){
                    anc2tick.setText("✘");
                    anc2tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc2text.setText( "PNC2-" + alertDate);
//                    (anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value())
                }else {
                    anc2text.setVisibility(View.GONE);
                    anc2tick.setVisibility(View.GONE);
                }
            } else {
                anc2text.setVisibility(View.GONE);
                anc2tick.setVisibility(View.GONE);
            }
        }
    }
    private void checkPnc3StatusAndform(TextView anc3tick, TextView anc3text, CommonPersonObjectClient pc) {
        if(pc.getDetails().get("FWPNC3DATE")!=null){
            anc3text.setText("PNC3-"+pc.getDetails().get("FWPNC3DATE"));
            if(pc.getDetails().get("pnc3_current_formStatus")!=null){
                if(pc.getDetails().get("pnc3_current_formStatus").equalsIgnoreCase("upcoming")){

                }else if(pc.getDetails().get("pnc3_current_formStatus").equalsIgnoreCase("urgent")){
                    anc3tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                }
            }
        }else{
            List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "pncrv_3");
            String alertstate = "";
            String alertDate = "";
            if(alertlist.size()!=0){
                for(int i = 0;i<alertlist.size();i++){
                    alertstate = alertlist.get(i).status().value();
                    alertDate = alertlist.get(i).startDate();
                }              ;
            }
            if(alertstate != null && !(alertstate.trim().equalsIgnoreCase(""))){
                if(alertstate.equalsIgnoreCase("expired")){
                    anc3tick.setText("✘");
                    anc3tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc3text.setText( "PNC3-" + alertDate);
//                    (anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value())
                }else {
                    anc3text.setVisibility(View.GONE);
                    anc3tick.setVisibility(View.GONE);
                }
            } else {
                anc3text.setVisibility(View.GONE);
                anc3tick.setVisibility(View.GONE);
            }
        }
    }

    private void constructPNCReminderDueBlock(String dateofoutcome,CommonPersonObjectClient pc, View itemView) {
        alertTextandStatus alerttextstatus = null;
            List<Alert> alertlist3 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "pncrv_3");
            if(alertlist3.size() != 0){
                alerttextstatus = setAlertStatus(dateofoutcome,"PNC3",alertlist3);
            }else{
                List<Alert> alertlist2 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "pncrv_2");
                if(alertlist2.size()!=0){
                    alerttextstatus = setAlertStatus(dateofoutcome,"PNC2",alertlist2);
                }else{
                    List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "pncrv_1");
                    if(alertlist.size()!=0){
                        alerttextstatus = setAlertStatus(dateofoutcome,"PNC1",alertlist);

                    }else{
                        alerttextstatus = new alertTextandStatus("Not synced","not synced");
                    }
                }
            }

        CustomFontTextView pncreminderDueDate = (CustomFontTextView)itemView.findViewById(R.id.pnc_reminder_due_date);
        setalerttextandColorInView(pncreminderDueDate, alerttextstatus,pc);


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
            customFontTextView.setTag(R.id.clientobject, pc);
            customFontTextView.setTag(R.id.textforPncRegister, alerttextstatus.getAlertText() != null ? alerttextstatus.getAlertText() : "");
            customFontTextView.setTag(R.id.AlertStatustextforPncRegister,"upcoming");
        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("urgent")){
            customFontTextView.setOnClickListener(onClickListener);
            customFontTextView.setTag(R.id.clientobject, pc);
            customFontTextView.setTag(R.id.textforPncRegister,alerttextstatus.getAlertText() != null ? alerttextstatus.getAlertText() : "");
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
            customFontTextView.setTag(R.id.AlertStatustextforPncRegister, "urgent");

        }
        if(alerttextstatus.getAlertstatus().equalsIgnoreCase("expired")){
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
            customFontTextView.setText("expired");
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

    private alertTextandStatus setAlertStatus(String dateofoutcome,String pnc, List<Alert> alertlist) {
        alertTextandStatus alts = null;
        for(int i = 0;i<alertlist.size();i++){
            if(pnc.equalsIgnoreCase("PNC1")){
                alts = new alertTextandStatus(pnc+ "-"+pncdate(dateofoutcome,0),alertlist.get(i).status().value());
            }else  if(pnc.equalsIgnoreCase("PNC2")){
                alts = new alertTextandStatus(pnc+ "-"+pncdate(dateofoutcome,2),alertlist.get(i).status().value());
            }else  if(pnc.equalsIgnoreCase("PNC3")){
                alts = new alertTextandStatus(pnc+ "-"+pncdate(dateofoutcome,6),alertlist.get(i).status().value());
            }
//            alts = new alertTextandStatus(pnc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value());
            }
        return alts;
    }

    private void constructRiskFlagView(CommonPersonObjectClient pc, View itemView) {


        ImageView hrp = (ImageView)itemView.findViewById(R.id.hrp);
        ImageView hp = (ImageView)itemView.findViewById(R.id.hr);
        ImageView vg = (ImageView)itemView.findViewById(R.id.vg);
        if(pc.getDetails().get("FWVG") != null && pc.getDetails().get("FWVG").equalsIgnoreCase("1")){

        }else{
            vg.setVisibility(View.GONE);
        }
        if(pc.getDetails().get("FWHRP") != null && pc.getDetails().get("FWHRP").equalsIgnoreCase("1")){

        }else{
            hrp.setVisibility(View.GONE);
        }
        if(pc.getDetails().get("FWHR_PSR") != null && pc.getDetails().get("FWHR_PSR").equalsIgnoreCase("1")){

        }else{
            hp.setVisibility(View.GONE);
        }

//        if(pc.getDetails().get("FWWOMAGE")!=null &&)

    }



    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                              FilterOption searchFilter, SortOption sortOption) {
        return null;
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
        return (ViewGroup) inflater().inflate(R.layout.smart_register_mcare_pnc_client, null);
    }
    public String pncdate(String date,int day){
        String pncdate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date pnc_date = format.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(pnc_date);
            calendar.add(Calendar.DATE, day);
            pnc_date.setTime(calendar.getTime().getTime());
            pncdate = format.format(pnc_date);
        } catch (Exception e) {
            e.printStackTrace();
            pncdate = "";
        }
        return pncdate;
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
