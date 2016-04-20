package org.ei.opensrp.mcare.child;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.cursoradapter.SmartRegisterCLientsProviderForCursorAdapter;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.repository.DetailsRepository;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.customControls.CustomFontTextView;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by user on 2/12/15.
 */
public class mCareChildSmartClientsProvider implements SmartRegisterCLientsProviderForCursorAdapter {

    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;

    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;
    private final AlertService alertService;


    public mCareChildSmartClientsProvider(Context context,
                                          View.OnClickListener onClickListener,
                                          AlertService alertService) {
        this.onClickListener = onClickListener;
        this.alertService = alertService;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        clientViewLayoutParams = new AbsListView.LayoutParams(MATCH_PARENT,
                (int) context.getResources().getDimension(org.ei.opensrp.R.dimen.list_item_height));
        txtColorBlack = context.getResources().getColor(org.ei.opensrp.R.color.text_black);
    }

    @Override
    public void getView(final SmartRegisterClient smartRegisterClient, View convertView) {
        View itemView = convertView;

        LinearLayout profileinfolayout = (LinearLayout) itemView.findViewById(R.id.profile_info_layout);

//        ImageView profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
        TextView mothername = (TextView) itemView.findViewById(R.id.mother_name);
        TextView fathername = (TextView) itemView.findViewById(R.id.father_name);
        TextView gobhhid = (TextView) itemView.findViewById(R.id.gobhhid);
        TextView jivitahhid = (TextView) itemView.findViewById(R.id.jivitahhid);
        TextView village = (TextView) itemView.findViewById(R.id.village);
        TextView age = (TextView) itemView.findViewById(R.id.age);
        TextView nid = (TextView) itemView.findViewById(R.id.nid);
        TextView brid = (TextView) itemView.findViewById(R.id.brid);
        TextView dateofbirth = (TextView) itemView.findViewById(R.id.dateofbirth);

        final CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;


//        TextView psrfdue = (TextView)itemView.findViewById(R.id.psrf_due_date);
////        Button due_visit_date = (Button)itemView.findViewById(R.id.hh_due_date);
//
        profileinfolayout.setTag(smartRegisterClient);

//        ImageButton follow_up = (ImageButton)itemView.findViewById(R.id.btn_edit);
        profileinfolayout.setOnClickListener(onClickListener);


        mothername.setText(pc.getColumnmaps().get("FWWOMFNAME") != null ? pc.getColumnmaps().get("FWWOMFNAME") : "");
        fathername.setText(pc.getDetails().get("FWHUSNAME") != null ? pc.getDetails().get("FWHUSNAME") : "");
        gobhhid.setText(pc.getColumnmaps().get("GOBHHID") != null ? pc.getColumnmaps().get("GOBHHID") : "");
        jivitahhid.setText(pc.getColumnmaps().get("FWJIVHHID") != null ? pc.getColumnmaps().get("FWJIVHHID") : "");
        village.setText(humanize((pc.getColumnmaps().get("existing_Mauzapara") != null ? pc.getColumnmaps().get("existing_Mauzapara") : "").replace("+", "_")));
        age.setText(pc.getColumnmaps().get("FWWOMAGE") != null ? pc.getColumnmaps().get("FWWOMAGE") : "");
        String dobTimestamp = pc.getColumnmaps().get("FWBNFDTOO") != null ? pc.getColumnmaps().get("FWBNFDTOO") : null;
        if (dobTimestamp != null) {
            Date date= new Date(Long.valueOf(dobTimestamp));
            String dob=new SimpleDateFormat("yyyy-MM-dd").format(date);
            dateofbirth.setText(dob);
        }

        nid.setText("NID :" + (pc.getColumnmaps().get("FWWOMNID") != null ? pc.getColumnmaps().get("FWWOMNID") : ""));
        brid.setText("BRID :" + (pc.getColumnmaps().get("FWWOMBID") != null ? pc.getColumnmaps().get("FWWOMBID") : ""));

        DetailsRepository detailsRepository= org.ei.opensrp.Context.getInstance().detailsRepository();
        Map<String, String> mcaremotherObject = detailsRepository.getAllDetailsForClient(pc.getColumnmaps().get("relationalid"));

        constructRiskFlagView(pc, mcaremotherObject, itemView);
        constructENCCReminderDueBlock(pc, itemView);
////        constructNBNFDueBlock(pc, itemView);s
        constructENCCVisitStatusBlock(pc, itemView);


        itemView.setLayoutParams(clientViewLayoutParams);
    }

    private void constructENCCVisitStatusBlock(CommonPersonObjectClient pc, View itemview) {
        TextView encc1tick = (TextView) itemview.findViewById(R.id.encc1tick);
        TextView encc1text = (TextView) itemview.findViewById(R.id.encc1text);
        TextView encc2tick = (TextView) itemview.findViewById(R.id.encc2tick);
        TextView encc2text = (TextView) itemview.findViewById(R.id.encc2text);
        TextView encc3tick = (TextView) itemview.findViewById(R.id.encc3tick);
        TextView encc3text = (TextView) itemview.findViewById(R.id.encc3text);
        checkEncc1StatusAndform(encc1tick, encc1text, pc);
        checkEncc2StatusAndform(encc2tick, encc2text, pc);
        checkEncc3StatusAndform(encc3tick, encc3text, pc);


    }


    private void checkEncc1StatusAndform(TextView anc1tick, TextView anc1text, CommonPersonObjectClient pc) {
        if (pc.getDetails().get("FWENC1DATE") != null) {
            anc1text.setText("ENCC1-" + pc.getDetails().get("FWENC1DATE"));
            if (pc.getDetails().get("encc1_current_formStatus") != null) {
                if (pc.getDetails().get("encc1_current_formStatus").equalsIgnoreCase("upcoming")) {

                } else if (pc.getDetails().get("encc1_current_formStatus").equalsIgnoreCase("urgent")) {
                    anc1tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc1text.setText("urgent");
                }
            }
        } else {
            List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "enccrv_1");
            String alertstate = "";
            String alertDate = "";
            if (alertlist.size() != 0) {
                for (int i = 0; i < alertlist.size(); i++) {
                    alertstate = alertlist.get(i).status().value();
                    alertDate = alertlist.get(i).startDate();
                }
                ;
            }
            if (alertstate != null && !(alertstate.trim().equalsIgnoreCase(""))) {
                if (alertstate.equalsIgnoreCase("expired")) {
                    anc1tick.setText("✘");
                    anc1tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc1text.setText("ENCC1-" + alertDate);
//                    (anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value())
                } else {
                    anc1text.setVisibility(View.GONE);
                    anc1tick.setVisibility(View.GONE);
                }
            } else {
                anc1text.setVisibility(View.GONE);
                anc1tick.setVisibility(View.GONE);
            }
        }
    }

    private void checkEncc2StatusAndform(TextView anc2tick, TextView anc2text, CommonPersonObjectClient pc) {
        if (pc.getDetails().get("FWENC2DATE") != null) {
            anc2text.setText("ENCC2-" + pc.getDetails().get("FWENC2DATE"));
            if (pc.getDetails().get("encc2_current_formStatus") != null) {
                if (pc.getDetails().get("encc2_current_formStatus").equalsIgnoreCase("upcoming")) {

                } else if (pc.getDetails().get("encc2_current_formStatus").equalsIgnoreCase("urgent")) {
                    anc2tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                }
            }
        } else {
            List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "enccrv_2");
            String alertstate = "";
            String alertDate = "";
            if (alertlist.size() != 0) {
                for (int i = 0; i < alertlist.size(); i++) {
                    alertstate = alertlist.get(i).status().value();
                    alertDate = alertlist.get(i).startDate();
                }
                ;
            }
            if (alertstate != null && !(alertstate.trim().equalsIgnoreCase(""))) {
                if (alertstate.equalsIgnoreCase("expired")) {
                    anc2tick.setText("✘");
                    anc2tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc2text.setText("ENCC2-" + alertDate);
//                    (anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value())
                } else {
                    anc2text.setVisibility(View.GONE);
                    anc2tick.setVisibility(View.GONE);
                }
            } else {
                anc2text.setVisibility(View.GONE);
                anc2tick.setVisibility(View.GONE);
            }
        }
    }

    private void checkEncc3StatusAndform(TextView anc3tick, TextView anc3text, CommonPersonObjectClient pc) {
        if (pc.getDetails().get("FWENC3DATE") != null) {
            anc3text.setText("ENCC3-" + pc.getDetails().get("FWENC3DATE"));
            if (pc.getDetails().get("encc3_current_formStatus") != null) {
                if (pc.getDetails().get("encc3_current_formStatus").equalsIgnoreCase("upcoming")) {

                } else if (pc.getDetails().get("encc3_current_formStatus").equalsIgnoreCase("urgent")) {
                    anc3tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                }
            }
        } else {
            List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "enccrv_3");
            String alertstate = "";
            String alertDate = "";
            if (alertlist.size() != 0) {
                for (int i = 0; i < alertlist.size(); i++) {
                    alertstate = alertlist.get(i).status().value();
                    alertDate = alertlist.get(i).startDate();
                }
                ;
            }
            if (alertstate != null && !(alertstate.trim().equalsIgnoreCase(""))) {
                if (alertstate.equalsIgnoreCase("expired")) {
                    anc3tick.setText("✘");
                    anc3tick.setTextColor(context.getResources().getColor(R.color.alert_urgent_red));
                    anc3text.setText("ENCC3-" + alertDate);
//                    (anc+ "-"+alertlist.get(i).startDate(),alertlist.get(i).status().value())
                } else {
                    anc3text.setVisibility(View.GONE);
                    anc3tick.setVisibility(View.GONE);
                }
            } else {
                anc3text.setVisibility(View.GONE);
                anc3tick.setVisibility(View.GONE);
            }
        }
    }

    private void constructENCCReminderDueBlock(CommonPersonObjectClient pc, View itemView) {
        alertTextandStatus alerttextstatus = null;
        List<Alert> alertlist3 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "enccrv_3");
        if (alertlist3.size() != 0) {
            alerttextstatus = setAlertStatus("ENCC3", alertlist3);
        } else {
            List<Alert> alertlist2 = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "enccrv_2");
            if (alertlist2.size() != 0) {
                alerttextstatus = setAlertStatus("ENCC2", alertlist2);
            } else {
                List<Alert> alertlist = org.ei.opensrp.Context.getInstance().alertService().findByEntityIdAndAlertNames(pc.entityId(), "enccrv_1");
                if (alertlist.size() != 0) {
                    alerttextstatus = setAlertStatus("ENCC1", alertlist);

                } else {
                    alerttextstatus = new alertTextandStatus("Not synced", "not synced");
                }
            }
        }

        CustomFontTextView pncreminderDueDate = (CustomFontTextView) itemView.findViewById(R.id.encc_reminder_due_date);
        setalerttextandColorInView(pncreminderDueDate, alerttextstatus, pc);


    }

    private void setalerttextandColorInView(CustomFontTextView customFontTextView, alertTextandStatus alerttextstatus, CommonPersonObjectClient pc) {
        customFontTextView.setText(alerttextstatus.getAlertText() != null ? alerttextstatus.getAlertText() : "");
        if (alerttextstatus.getAlertstatus().equalsIgnoreCase("normal")) {
            customFontTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_upcoming_light_blue));
        }
        if (alerttextstatus.getAlertstatus().equalsIgnoreCase("upcoming")) {
            customFontTextView.setBackgroundColor(context.getResources().getColor(R.color.alert_upcoming_yellow));
            customFontTextView.setOnClickListener(onClickListener);
            customFontTextView.setTag(R.id.clientobject, pc);
            customFontTextView.setTag(R.id.textforEnccRegister, alerttextstatus.getAlertText() != null ? alerttextstatus.getAlertText() : "");
            customFontTextView.setTag(R.id.AlertStatustextforEnccRegister, "upcoming");
        }
        if (alerttextstatus.getAlertstatus().equalsIgnoreCase("urgent")) {
            customFontTextView.setOnClickListener(onClickListener);
            customFontTextView.setTag(R.id.clientobject, pc);
            customFontTextView.setTag(R.id.textforEnccRegister, alerttextstatus.getAlertText() != null ? alerttextstatus.getAlertText() : "");
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.alert_urgent_red));
            customFontTextView.setTag(R.id.AlertStatustextforEnccRegister, "urgent");

        }
        if (alerttextstatus.getAlertstatus().equalsIgnoreCase("expired")) {
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.client_list_header_dark_grey));
            customFontTextView.setText("expired");
            customFontTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (alerttextstatus.getAlertstatus().equalsIgnoreCase("complete")) {
//               psrfdue.setText("visited");
            customFontTextView.setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green_mcare));
            customFontTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (alerttextstatus.getAlertstatus().equalsIgnoreCase("not synced")) {
            customFontTextView.setBackgroundColor(context.getResources().getColor(org.ei.opensrp.R.color.status_bar_text_almost_white));
//
        }
    }

    private alertTextandStatus setAlertStatus(String anc, List<Alert> alertlist) {
        alertTextandStatus alts = null;
        for (int i = 0; i < alertlist.size(); i++) {
            alts = new alertTextandStatus(anc + "-" + alertlist.get(i).startDate(), alertlist.get(i).status().value());
        }
        return alts;
    }

    private void constructRiskFlagView(CommonPersonObjectClient pc, Map<String,String> mcaremotherObject, View itemView) {
//        AllCommonsRepository allancRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("mcaremother");
//        CommonPersonObject ancobject = allancRepository.findByCaseID(pc.entityId());
//        AllCommonsRepository allelcorep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");
//        CommonPersonObject elcoparent = allelcorep.findByCaseID(ancobject.getRelationalId());

        ImageView hrp = (ImageView) itemView.findViewById(R.id.hrp);
        ImageView hp = (ImageView) itemView.findViewById(R.id.hr);
        ImageView vg = (ImageView) itemView.findViewById(R.id.vg);
        if (mcaremotherObject.get("FWVG") != null && mcaremotherObject.get("FWVG").equalsIgnoreCase("1")) {

        } else {
            vg.setVisibility(View.GONE);
        }
        if (mcaremotherObject.get("FWHRP") != null && mcaremotherObject.get("FWHRP").equalsIgnoreCase("1")) {

        } else {
            hrp.setVisibility(View.GONE);
        }
        if (pc.getDetails().get("FWHR_PSR") != null && pc.getDetails().get("FWHR_PSR").equalsIgnoreCase("1")) {

        } else {
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
        return (ViewGroup) inflater().inflate(R.layout.smart_register_mcare_child_client, null);
    }

    class alertTextandStatus {
        String alertText, alertstatus;

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
