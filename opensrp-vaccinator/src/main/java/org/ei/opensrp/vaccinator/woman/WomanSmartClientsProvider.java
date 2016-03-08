package org.ei.opensrp.vaccinator.woman;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.commonregistry.CommonPersonObjectController;
import org.ei.opensrp.domain.Alert;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.service.AlertService;
import org.ei.opensrp.vaccinator.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.contract.SmartRegisterClients;
import org.ei.opensrp.view.dialog.FilterOption;
import org.ei.opensrp.view.dialog.ServiceModeOption;
import org.ei.opensrp.view.dialog.SortOption;
import org.ei.opensrp.view.viewHolder.OnClickFormLauncher;

import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Ahmed on 19-Oct-15.
 */
public class WomanSmartClientsProvider implements SmartRegisterClientsProvider {


    private final LayoutInflater inflater;
    private final Context context;
    private final View.OnClickListener onClickListener;
    AlertService alertService;
    private final int txtColorBlack;
    private final AbsListView.LayoutParams clientViewLayoutParams;

    protected CommonPersonObjectController controller;

    public WomanSmartClientsProvider(Context context,
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
    public View getView(SmartRegisterClient client, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = (ViewGroup) inflater().inflate(R.layout.smart_register_woman_client, null);
            viewHolder = new ViewHolder();
            viewHolder.profilelayout = (LinearLayout) convertView.findViewById(R.id.woman_profile_info_layout);
            viewHolder.womanId = (TextView) convertView.findViewById(R.id.woman_id);
            viewHolder.womanName = (TextView) convertView.findViewById(R.id.woman_name);
            viewHolder.fatherName = (TextView) convertView.findViewById(R.id.woman_fh_name);
            //viewHolder.womanDOB=(TextView)convertView.findViewById(R.id.woman_dob);
            viewHolder.edd = (TextView) convertView.findViewById(R.id.woman_dob);
            viewHolder.profilepic = (ImageView) convertView.findViewById(R.id.woman_profilepic);
            viewHolder.last_visit_date = (TextView) convertView.findViewById(R.id.woman_last_visit_date);
            viewHolder.last_vaccine = (TextView) convertView.findViewById(R.id.woman_last_vaccine);
            viewHolder.next_visit_date = (TextView) convertView.findViewById(R.id.woman_next_visit);
            viewHolder.next_due_date_holder = (FrameLayout) convertView.findViewById(R.id.woman_next_visit_holder);
            //viewHolder.profilepic.setImageResource();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        CommonPersonObjectClient pc = (CommonPersonObjectClient) client;


        viewHolder.womanId.setText(pc.getDetails().get("existing_program_client_id") != null ? pc.getDetails().get("existing_program_client_id") : "N/A");
        viewHolder.womanName.setText(pc.getDetails().get("first_name") != null ? pc.getDetails().get("first_name") : "");
        viewHolder.fatherName.setText(pc.getDetails().get("father_name") != null ? pc.getDetails().get("father_name") : "");
        // viewHolder.womanDOB.setText(pc.getDetails().get("client_dob_confirm") != null ? pc.getDetails().get("chid_dob_confirm") : "");
        viewHolder.edd.setText(pc.getDetails().get("final_edd") != null ? pc.getDetails().get("final_edd") : "N/A");
        viewHolder.profilelayout.setOnClickListener(onClickListener);
        viewHolder.profilelayout.setTag(client);

        // HashMap<String, String> dateMaps=  getPreloadVaccineData(pc);
        String vaccineretro = pc.getColumnmaps().get("vaccines") != null ? pc.getColumnmaps().get("vaccines") : "";
        String vaccine2 = pc.getColumnmaps().get("vaccines2") != null ? pc.getColumnmaps().get("vaccines2") : "";

        viewHolder.last_vaccine.setText(vaccineretro + " " + vaccine2);
        String lastVaccine = pc.getColumnmaps().get("date") != null ? pc.getColumnmaps().get("date") : "";

        viewHolder.last_visit_date.setText(lastVaccine);
        List<Alert> alertlist_for_client = alertService.findByEntityIdAndAlertNames(pc.entityId(), "TT 1", "TT 2", "TT 3", "TT 4", "TT 5");


        if (alertlist_for_client.size() == 0) {
            viewHolder.next_visit_date.setText("Schedule Not Generated");
            viewHolder.next_due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.status_bar_text_almost_white));
            viewHolder.next_due_date_holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        for (int i = 0; i < alertlist_for_client.size(); i++) {
            viewHolder.next_visit_date.setText(alertlist_for_client.get(i).expiryDate());
            if (alertlist_for_client.get(i).status().value().equalsIgnoreCase("normal")) {
                String dueDate = alertlist_for_client.get(i).startDate();
                String vaccine = alertlist_for_client.get(i).visitCode();
                viewHolder.next_visit_date.setText("Vaccine : " + vaccine + "\n Due  : " + dueDate);
                viewHolder.next_visit_date.setOnClickListener(onClickListener);
                viewHolder.next_due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.client_light_blue));
            }
            if (alertlist_for_client.get(i).status().value().equalsIgnoreCase("upcoming")) {
                String dueDate = alertlist_for_client.get(i).startDate();
                String vaccine = alertlist_for_client.get(i).visitCode();
                viewHolder.next_visit_date.setText("Vaccine : " + vaccine + "\n Due  : " + dueDate);
                viewHolder.next_due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.client_light_blue));
                viewHolder.next_visit_date.setOnClickListener(onClickListener);

                viewHolder.next_visit_date.setTag(client);
                // viewHolder.

            }
            if (alertlist_for_client.get(i).status().value().equalsIgnoreCase("urgent")) {
                String dueDate = alertlist_for_client.get(i).startDate();
                String vaccine = alertlist_for_client.get(i).visitCode();
                viewHolder.next_visit_date.setText("Vaccine : " + vaccine + "\n Due  : " + dueDate);
                viewHolder.next_visit_date.setOnClickListener(onClickListener);
                viewHolder.next_visit_date.setTag(client);
                viewHolder.next_due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.client_dark_blue));
            }
            if (alertlist_for_client.get(i).status().value().equalsIgnoreCase("expired")) {
                viewHolder.next_due_date_holder.setBackgroundColor(context.getResources().getColor(R.color.alert_urgent_red));
                viewHolder.next_visit_date.setOnClickListener(onClickListener);
            }
            if (alertlist_for_client.get(i).isComplete()) {
                viewHolder.last_visit_date.setText(alertlist_for_client.get(i).scheduleName());
                viewHolder.last_visit_date.setText(alertlist_for_client.get(i).completionDate());
                viewHolder.next_visit_date.setOnClickListener(onClickListener);
                //  viewHolder..setBackgroundColor(context.getResources().getColor(R.color.alert_complete_green));
            }
        }
        viewHolder.next_visit_date.setOnClickListener(onClickListener);
        viewHolder.next_visit_date.setTag(client);
        convertView.setLayoutParams(clientViewLayoutParams);
        return convertView;
    }

    @Override
    public SmartRegisterClients getClients() {
        return controller.getClients();
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption, FilterOption searchFilter, SortOption sortOption) {
        return getClients().applyFilter(villageFilter, serviceModeOption, searchFilter, sortOption);
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {

    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    public LayoutInflater inflater() {
        return inflater;
    }

    private HashMap<String, String> getPreloadVaccineData(CommonPersonObjectClient client) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (String s : client.getColumnmaps().keySet()) {
            if (s.contains("tt1")) {
                if (client.getColumnmaps().get(s) != null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("tt1", client.getColumnmaps().get(s).toString());
                } else {
                    map.put("tt1", "");

                }
            } else if (s.contains("tt2")) {
                if (client.getColumnmaps().get(s) != null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("tt2", client.getColumnmaps().get(s).toString());
                } else {
                    map.put("tt2", "");

                }
            } else if (s.contains("tt3")) {
                if (client.getColumnmaps().get(s) != null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("tt3", client.getColumnmaps().get(s).toString());
                } else {
                    map.put("tt3", "");

                }
            } else if (s.contains("tt4")) {
                if (client.getColumnmaps().get(s) != null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("tt4", client.getColumnmaps().get(s).toString());
                } else {
                    map.put("tt4", "");

                }
            } else if (s.contains("tt5")) {
                if (client.getColumnmaps().get(s) != null && !client.getColumnmaps().get(s).toString().equalsIgnoreCase("")) {
                    map.put("tt5", client.getColumnmaps().get(s).toString());
                } else {
                    map.put("tt5", "");

                }
            }

            Log.d("column name :", s + "---" + client.getColumnmaps().get(s));
        }
        return map;

    }

    class ViewHolder {

        TextView womanId;
        TextView womanName;
        TextView fatherName;
        TextView edd;
        TextView womanDOB;
        TextView last_vaccine;
        TextView last_visit_date;
        TextView next_visit_date;
        ImageButton follow_up;
        LinearLayout profilelayout;
        ImageView profilepic;
        FrameLayout next_due_date_holder;
    }
}
