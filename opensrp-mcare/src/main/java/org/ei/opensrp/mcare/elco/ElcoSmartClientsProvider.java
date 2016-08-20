package org.ei.opensrp.mcare.elco;

import android.app.Activity;
import android.content.Context;
<<<<<<< HEAD
=======
import android.text.Html;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
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
<<<<<<< HEAD
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.mcare.household.HouseHoldDetailActivity;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
=======
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.mcare.application.McareApplication;
import org.ei.opensrp.mcare.household.HouseHoldDetailActivity;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
import org.ei.opensrp.util.DateUtil;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
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
import java.util.Locale;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
<<<<<<< HEAD
public class ElcoSmartClientsProvider implements SmartRegisterClientsProvider {
=======
public class ElcoSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;
<<<<<<< HEAD

    public ElcoSmartClientsProvider(Context context,
                                    View.OnClickListener onClickListener,
                                    CommonPersonObjectController controller) {
        this.onClickListener = onClickListener;
        this.controller = controller;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

=======
    AlertService alertService;
    public ElcoSmartClientsProvider(Context context,
                                    View.OnClickListener onClickListener,
                                    AlertService alertService) {
        this.onClickListener = onClickListener;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.alertService = alertService;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);
    }

    @Override
<<<<<<< HEAD
    public View getView(final SmartRegisterClient smartRegisterClient, View convertView, ViewGroup viewGroup) {
        ViewGroup itemView;

        itemView = (ViewGroup) inflater().inflate(R.layout.smart_register_elco_client, null);
=======
    public void getView(final SmartRegisterClient smartRegisterClient, View convertView) {
        View itemView;

        itemView = convertView;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
        LinearLayout elcodetails = (LinearLayout)itemView.findViewById(R.id.profile_info_layout);
        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView name = (TextView)itemView.findViewById(R.id.name);
        TextView spousename = (TextView)itemView.findViewById(R.id.spousename);
        TextView gobhhid = (TextView)itemView.findViewById(R.id.gobhhid);
        TextView jivitahhid = (TextView)itemView.findViewById(R.id.jivitahhid);
        TextView village = (TextView)itemView.findViewById(R.id.village);
        TextView age = (TextView)itemView.findViewById(R.id.age);
        TextView nid = (TextView)itemView.findViewById(R.id.nid);
        TextView brid = (TextView)itemView.findViewById(R.id.brid);
        TextView lmp = (TextView)itemView.findViewById(R.id.lmp);
        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
<<<<<<< HEAD
=======
        TextView mis_elco_due = (TextView)itemView.findViewById(R.id.mis_elco);

>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
//        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);

        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        elcodetails.setOnClickListener(onClickListener);
        elcodetails.setTag(smartRegisterClient);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;

        if(pc.getDetails().get("profilepic")!=null){
            HouseHoldDetailActivity.setImagetoHolder((Activity) context, pc.getDetails().get("profilepic"), profilepic, R.mipmap.womanimageload);
        }
//
//        id.setText(pc.getDetails().get("case_id")!=null?pc.getCaseId():"");
<<<<<<< HEAD
        name.setText(pc.getDetails().get("FWWOMFNAME")!=null?pc.getDetails().get("FWWOMFNAME"):"");
        spousename.setText(pc.getDetails().get("FWHUSNAME")!=null?pc.getDetails().get("FWHUSNAME"):"");
        gobhhid.setText(pc.getDetails().get("GOBHHID")!=null?pc.getDetails().get("GOBHHID"):"");
        jivitahhid.setText(pc.getDetails().get("JiVitAHHID")!=null?pc.getDetails().get("JiVitAHHID"):"");
        village.setText((humanize((pc.getDetails().get("FWWOMMAUZA_PARA") != null ? pc.getDetails().get("FWWOMMAUZA_PARA") : "").replace("+", "_"))));



        age.setText(pc.getDetails().get("FWWOMAGE")!=null?pc.getDetails().get("FWWOMAGE"):"");
        nid.setText("NID :" +(pc.getDetails().get("FWWOMNID")!=null?pc.getDetails().get("FWWOMNID"):""));
        brid.setText("BRID :" +(pc.getDetails().get("FWWOMBID")!=null?pc.getDetails().get("FWWOMBID"):""));
=======
        name.setText(humanize(pc.getColumnmaps().get("FWWOMFNAME")!=null?pc.getColumnmaps().get("FWWOMFNAME"):""));
        spousename.setText(humanize(pc.getDetails().get("FWHUSNAME")!=null?pc.getDetails().get("FWHUSNAME"):""));
        gobhhid.setText(" "+(pc.getColumnmaps().get("GOBHHID")!=null?pc.getColumnmaps().get("GOBHHID"):""));
        jivitahhid.setText(pc.getColumnmaps().get("JiVitAHHID")!=null?pc.getColumnmaps().get("JiVitAHHID"):"");
        village.setText((humanize((pc.getDetails().get("FWWOMMAUZA_PARA") != null ? pc.getDetails().get("FWWOMMAUZA_PARA") : "").replace("+", "_"))));
        age.setText("("+(pc.getDetails().get("FWWOMAGE")!=null?pc.getDetails().get("FWWOMAGE"):"")+") ");

        if((pc.getDetails().get("FWWOMNID")!=null?pc.getDetails().get("FWWOMNID"):"").length()>0) {
            String NIDSourcestring = "NID: " +  (pc.getDetails().get("FWWOMNID") != null ? pc.getDetails().get("FWWOMNID") : "") ;
            nid.setText(Html.fromHtml(NIDSourcestring));
            nid.setVisibility(View.VISIBLE);
        }else{
            nid.setVisibility(View.GONE);
        }
        if((pc.getDetails().get("FWWOMBID")!=null?pc.getDetails().get("FWWOMBID"):"").length()>0) {
            String BRIDSourcestring = "BRID: " + (pc.getDetails().get("FWWOMBID") != null ? pc.getDetails().get("FWWOMBID") : "") ;
            brid.setText(Html.fromHtml(BRIDSourcestring));
            brid.setVisibility(View.VISIBLE);
        }else{
            brid.setVisibility(View.GONE);
        }

//        nid.setText("NID :" +(pc.getDetails().get("FWWOMNID")!=null?pc.getDetails().get("FWWOMNID"):""));
//        brid.setText("BRID :" +(pc.getDetails().get("FWWOMBID")!=null?pc.getDetails().get("FWWOMBID"):""));
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
        lmp.setText(pc.getDetails().get("FWPSRLMP")!=null?pc.getDetails().get("FWPSRLMP"):"");


        String location = "";
        /////location////////
        AllCommonsRepository allelcoRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");

        CommonPersonObject elcoobject = allelcoRepository.findByCaseID(pc.entityId());

        AllCommonsRepository householdrep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("household");
        final CommonPersonObject householdparent = householdrep.findByCaseID(elcoobject.getRelationalId());

//            if(householdparent.getDetails().get("existing_Mauzapara") != null) {
//                location = householdparent.getDetails().get("existing_Mauzapara");
//            }
//        village.setText(humanize(location));

        Date lastdate = null;
        if(householdparent.getDetails().get("FWNHREGDATE")!= null && householdparent.getDetails().get("FWCENDATE")!= null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date regdate = format.parse(householdparent.getDetails().get("FWNHREGDATE"));
                Date cendate = format.parse(householdparent.getDetails().get("FWCENDATE"));

                if(regdate.before(cendate)){
                       lastdate = cendate;
                }else{
                      lastdate = regdate;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else  if(householdparent.getDetails().get("FWNHREGDATE")!= null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date regdate = format.parse(householdparent.getDetails().get("FWNHREGDATE"));


                    lastdate = regdate;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        if(pc.getDetails().get("FWPSRDATE")!=null && pc.getDetails().get("FWPSRPREGSTS")!=null){
            if(pc.getDetails().get("FWPSRPREGSTS").equalsIgnoreCase("0") || pc.getDetails().get("FWPSRPREGSTS").equalsIgnoreCase("9")){
                 try {
                Date regdate = format.parse(householdparent.getDetails().get("FWPSRDATE"));

                lastdate = regdate;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }



        }

        //psrf_schedule_logic == 1 || FWPSRSTS ==2
            if(lastdate!= null){
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(lastdate);
                calendar.add(Calendar.DATE, 56);
                lastdate.setTime(calendar.getTime().getTime());
//                String result = String.format(Locale.ENGLISH, format.format(lastdate) );

                psrfdue.setText(format.format(lastdate));
//           psrfdue.append(format.format(lastdate));

            }

//        psrfdue.setOnClickListener(onClickListener);

            //Alert colors/////////////////////////////////////////////

        List<Alert> alertlist_for_client = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "ELCO PSRF");
        if(alertlist_for_client.size() == 0 ){
           psrfdue.setText(format.format(lastdate));
            psrfdue.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
            Log.v("is here", "3");
            try {
                if (pc.getDetails().get("WomanREGDATE") != null) {
                    Log.v("is here", "2");
                    LocalDate regdate = LocalDate.fromDateFields(format.parse(pc.getDetails().get("WomanREGDATE")));
                    if (DateUtil.dayDifference(regdate, DateUtil.today()) == 0) {
                        Log.v("is here", "1");
                        psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
<<<<<<< HEAD
=======
                        psrfdue.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                        psrfdue.setOnClickListener(onClickListener);
                        psrfdue.setTag(smartRegisterClient);
                        psrfdue.setText(pc.getDetails().get("WomanREGDATE"));
                    }
                }
                if(pc.getDetails().get("FWPSRDATE")!=null){
                    psrfdue.setText(pc.getDetails().get("FWPSRDATE"));
                    psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
<<<<<<< HEAD
=======
                    psrfdue.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                    psrfdue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
<<<<<<< HEAD
=======

>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                }catch(ParseException e){
                    e.printStackTrace();
                }

        }
        for(int i = 0;i<alertlist_for_client.size();i++){
//           psrfdue.setText(alertlist_for_client.get(i).expiryDate());
            Log.v("printing alertlist",alertlist_for_client.get(i).status().value());
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")){
               psrfdue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                psrfdue.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
<<<<<<< HEAD
            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
               psrfdue.setOnClickListener(onClickListener);
=======
                psrfdue.setTextColor(context.getResources().getColor(R.color.text_black));
            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
                psrfdue.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
                psrfdue.setOnClickListener(onClickListener);
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
               psrfdue.setTag(smartRegisterClient);

            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")){
               psrfdue.setOnClickListener(onClickListener);
               psrfdue.setTag(smartRegisterClient);
                psrfdue.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
<<<<<<< HEAD
            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
=======
                psrfdue.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));

            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
                psrfdue.setTextColor(context.getResources().getColor(R.color.text_black));
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                psrfdue.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
               psrfdue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            if(alertlist_for_client.get(i).isComplete()){
//               psrfdue.setText("visited");
<<<<<<< HEAD
                psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
=======
                if(pc.getDetails().get("FWPSRDATE")!=null) {
                    psrfdue.setText(pc.getDetails().get("FWPSRDATE"));
                }
                psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
                psrfdue.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                psrfdue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
<<<<<<< HEAD
=======
        if(pc.getDetails().get("FWPSRDATE")==null){
            GregorianCalendar calendar = new GregorianCalendar();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 0);
            date.setTime(calendar.getTime().getTime());
//                String result = String.format(Locale.ENGLISH, format.format(lastdate) );

            psrfdue.setText(format.format(date));
        }
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
//        Log.v("printing psrf schedule",pc.getDetails().get("psrf_schedule_logic")!=null?pc.getDetails().get("psrf_schedule_logic"):"");
        ////location////////
//        current.setText("(10 mo)");
        //check if woman is pregnant and if so then block the button
        if(pc.getDetails().get("FWPSRSTS")!=null && pc.getDetails().get("psrf_schedule_logic")!=null ){
<<<<<<< HEAD
            if( ((pc.getDetails().get("psrf_schedule_logic").equalsIgnoreCase("0")) || pc.getDetails().get("FWPSRSTS").equalsIgnoreCase("01"))){
=======
            if( ((pc.getDetails().get("psrf_schedule_logic").equalsIgnoreCase("0")) && pc.getDetails().get("FWPSRSTS").equalsIgnoreCase("01"))){
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
                Log.v("printing alertlist","yoo hoo");
                psrfdue.setText(pc.getDetails().get("FWPSRDATE"));
                psrfdue.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
                psrfdue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
<<<<<<< HEAD
        itemView.setLayoutParams(clientViewLayoutParams);
        return itemView;
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

=======
        lmp.setText(McareApplication.convertToEnglishDigits(lmp.getText().toString()));
        psrfdue.setText(McareApplication.convertToEnglishDigits(psrfdue.getText().toString()));
        CheckMisElcoSchedule(pc, mis_elco_due, smartRegisterClient, householdparent);
        itemView.setLayoutParams(clientViewLayoutParams);
        ;
    }

    private void CheckMisElcoSchedule(CommonPersonObjectClient pc, TextView mis_elco_due, SmartRegisterClient smartRegisterClient, CommonPersonObject householdparent) {
        Date lastdate = null;
        if(householdparent.getDetails().get("FWNHREGDATE")!= null && householdparent.getDetails().get("FWCENDATE")!= null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date regdate = format.parse(householdparent.getDetails().get("FWNHREGDATE"));
                Date cendate = format.parse(householdparent.getDetails().get("FWCENDATE"));

                if(regdate.before(cendate)){
                    lastdate = cendate;
                }else{
                    lastdate = regdate;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else  if(householdparent.getDetails().get("FWNHREGDATE")!= null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date regdate = format.parse(householdparent.getDetails().get("FWNHREGDATE"));


                lastdate = regdate;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        if(pc.getDetails().get("FWMISELCODATE")!=null){
                 try {
                    Date regdate = format.parse(pc.getDetails().get("FWMISELCODATE"));

                    lastdate = regdate;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            mis_elco_due.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
            mis_elco_due.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
            mis_elco_due.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });




        }else{
            mis_elco_due.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            mis_elco_due.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
            mis_elco_due.setOnClickListener(onClickListener);
            mis_elco_due.setTag(smartRegisterClient);
        }

        //psrf_schedule_logic == 1 || FWPSRSTS ==2
        if(lastdate!= null){
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(lastdate);
            calendar.add(Calendar.DATE, 56);
            lastdate.setTime(calendar.getTime().getTime());
//                String result = String.format(Locale.ENGLISH, format.format(lastdate) );

            mis_elco_due.setText(format.format(lastdate));
//           psrfdue.append(format.format(lastdate));

        }
        if(pc.getDetails().get("FWMISELCODATE")==null){
            GregorianCalendar calendar = new GregorianCalendar();
            Date date = new Date();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 0);
            date.setTime(calendar.getTime().getTime());
//                String result = String.format(Locale.ENGLISH, format.format(lastdate) );

            mis_elco_due.setText(format.format(date));
        }


        List<Alert> alertlist_for_client = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "mis_elco");
        for(int i = 0;i<alertlist_for_client.size();i++){
//           psrfdue.setText(alertlist_for_client.get(i).expiryDate());
            Log.v("printing alertlist",alertlist_for_client.get(i).status().value());
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")){
                mis_elco_due.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mis_elco_due.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
                mis_elco_due.setTextColor(context.getResources().getColor(R.color.text_black));
            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")){
                mis_elco_due.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
                mis_elco_due.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
                mis_elco_due.setOnClickListener(onClickListener);
                mis_elco_due.setTag(smartRegisterClient);

            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")){
                mis_elco_due.setOnClickListener(onClickListener);
                mis_elco_due.setTag(smartRegisterClient);
                mis_elco_due.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
                mis_elco_due.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));

            }
            if(alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")){
                mis_elco_due.setTextColor(context.getResources().getColor(R.color.text_black));
                mis_elco_due.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
                mis_elco_due.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            if(alertlist_for_client.get(i).isComplete()){
//               psrfdue.setText("visited");
                mis_elco_due.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
                mis_elco_due.setTextColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
                mis_elco_due.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
        mis_elco_due.setText(McareApplication.convertToEnglishDigits(mis_elco_due.getText().toString()));
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption, FilterOption searchFilter, SortOption sortOption) {
        return null;
    }


>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
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
<<<<<<< HEAD
=======
    @Override
    public View inflatelayoutForCursorAdapter() {
        View View = (ViewGroup) inflater().inflate(R.layout.smart_register_elco_client, null);
        return View;
    }
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
}
