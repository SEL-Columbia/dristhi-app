package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.commons.lang3.text.WordUtils;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.view.controller.NavigationController;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.ei.telemedicine.AllConstants.DOCTOR_OVERVIEW_URL_PATH;
import static org.ei.telemedicine.doctor.DoctorFormDataConstants.*;

/**
 * Created by naveen on 5/20/15.
 */
public class PendingConsultantBaseAdapter extends BaseAdapter {
    List<DoctorData> consultantsList;
    List<DoctorData> remainingList;
    List<DoctorData> searchconsultantsList;
    Context context;
    private String TAG = "PendingConsultantBaseAdapter";
    Activity activity;
    ProgressDialog progressDialog;

    public PendingConsultantBaseAdapter(Context context, ArrayList<DoctorData> consultantsList, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.consultantsList = consultantsList;
        this.searchconsultantsList = new ArrayList<DoctorData>();
        this.remainingList = new ArrayList<DoctorData>();
        this.searchconsultantsList.addAll(NativeDoctorActivity.doctorDataArrayList);
    }

    @Override
    public int getCount() {
        return consultantsList.size();
    }

    @Override
    public DoctorData getItem(int position) {
        return consultantsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {

        org.ei.telemedicine.view.customControls.CustomFontTextView tv_wife_name, tv_husband_name, tv_village_name, tv_id_no, tv_visit_type, tv_poc_pending, tv_status, tv_wife_age, tv_status2;
        ImageView iv_profile, iv_hr;
        ImageButton ib_btn_edit;
        LinearLayout ll_clients_header_layout, profile_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.consultant_item_list_view, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.profile_layout = (LinearLayout) convertView.findViewById(R.id.profile_layout);
        viewHolder.ll_clients_header_layout = (LinearLayout) convertView.findViewById(R.id.clients_header_layout);
        viewHolder.tv_wife_name = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_wife_name);
        viewHolder.tv_husband_name = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_husband_name);
        viewHolder.tv_village_name = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_village_name);
        viewHolder.tv_wife_age = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_wife_age);

        viewHolder.tv_id_no = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_id_no);
        viewHolder.tv_visit_type = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_visit_type);
        viewHolder.tv_poc_pending = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_poc_pending);
        viewHolder.tv_status = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_status1);
        viewHolder.tv_status2 = (org.ei.telemedicine.view.customControls.CustomFontTextView) convertView.findViewById(R.id.item_status2);
        viewHolder.iv_profile = (ImageView) convertView.findViewById(R.id.iv_profile);
        viewHolder.iv_hr = (ImageView) convertView.findViewById(R.id.iv_hr);
        viewHolder.ib_btn_edit = (ImageButton) convertView.findViewById(R.id.btn_edit);

        final String formData = consultantsList.get(position).getFormInformation();

//        String wifeName = getData(DoctorFormDataConstants.wife_name, formData);

        viewHolder.tv_husband_name.setText(!getData(husband_name, formData).equals("Null") ? getData(husband_name, formData) : "");
        viewHolder.tv_village_name.setText(getData(village_name, formData));
        viewHolder.tv_id_no.setText(getData(id_no, formData));
        viewHolder.tv_visit_type.setText(getData(visit_type, formData));
        String pocPendingInfo = getData(poc_pending, formData);
        viewHolder.tv_poc_pending.setText(pocPendingInfo);
//        viewHolder.tv_status.setText(getData(status, formData));
        viewHolder.tv_wife_age.setText(getData(age, formData));
//        Log.e(TAG, "pocPending Info" + pocPendingInfo.length());
        viewHolder.ll_clients_header_layout.setBackgroundColor(Color.parseColor((pocPendingInfo.length() != 0 && !pocPendingInfo.equals("")) ? "#c0c0c0" : "#FFFFFF"));
        Drawable imgae = null;
        String data = getData(visit_type, formData);
        viewHolder.iv_hr.setVisibility(getData(isHighRisk, formData).equalsIgnoreCase("yes") ? View.VISIBLE : View.GONE);
        switch (getData(visit_type, formData)) {
            case ancvisit:
                viewHolder.tv_wife_name.setText(getData(wife_name, formData));
                imgae = context.getResources().getDrawable(R.drawable.woman_placeholder);
                viewHolder.tv_status.setText("LMP: " + getData(lmp, formData));
                viewHolder.tv_status2.setText("Edd:" + getData(edd, formData));
                break;
            case pncVisit:
                viewHolder.tv_wife_name.setText(getData(wife_name, formData));
                imgae = context.getResources().getDrawable(R.drawable.woman_placeholder);
                viewHolder.tv_status.setText("Place : " + getData(pnc_visit_place, formData));
                viewHolder.tv_status2.setText("Date :" + getData(pnc_visit_date, formData));
//
//                viewHolder.tv_status.setText("Place: " + getData(pnc_visit_place, formData) + "\n Date:" + getData(pnc_visit_date, formData));
                break;
            case childVisit:
                viewHolder.tv_wife_name.setText("B/o " + getData(wife_name, formData));
                imgae = context.getResources().getDrawable(R.drawable.child_boy_infant);
                viewHolder.tv_status.setText(!getData(child_report_child_disease_place, formData).equals("") ? "Place : " + getData(child_report_child_disease_place, formData) : "");
                viewHolder.tv_status2.setText(!getData(child_report_child_disease_date, formData).equals("") ? "Date :" + getData(child_report_child_disease_date, formData) : "");
//                viewHolder.tv_status.setText("Place: " + getData(child_report_child_disease_place, formData) + "\n Date:" + getData(child_report_child_disease_date, formData));
                break;

        }
        viewHolder.iv_profile.setImageDrawable(imgae);
        viewHolder.profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = DOCTOR_OVERVIEW_URL_PATH + "visitid=" + getData(visitId, formData) + "&entityid=" + getData(entityId, formData);

                getData(url, new Listener<String>() {
                    @Override
                    public void onEvent(String data) {
                        if (data != null) {
                            NavigationController navigationController = new NavigationController(activity, org.ei.telemedicine.Context.getInstance().anmController());
                            navigationController.startDoctor(data, formData);
                        } else
                            Toast.makeText(context, "No Data from server", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        viewHolder.ib_btn_edit.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          Intent intent = null;
                                                          Log.e("Visit Type", getData(visit_type, formData));
                                                          switch (getData(visit_type, formData)) {
                                                              case ancvisit:
                                                                  intent = new Intent(context, DoctorANCScreenActivity.class);
                                                                  break;
                                                              case pncVisit:
                                                                  intent = new Intent(context, DoctorPNCScreenActivity.class);
                                                                  break;
                                                              case childVisit:
                                                                  intent = new Intent(context, DoctorChildScreenActivity.class);
                                                                  break;
                                                          }
                                                          if (intent != null) {
                                                              intent.putExtra("formData", formData);
//                                                              Log.e(TAG, "Data from adapter " + formData);
                                                              context.startActivity(intent);
                                                          }
                                                      }
                                                  }

        );

        return convertView;
    }


    private void getData(final String url, final Listener<String> afterResult) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                org.ei.telemedicine.Context context = org.ei.telemedicine.Context.getInstance();
                Log.e("Doctor Overview URL", context.configuration().dristhiDjangoBaseURL() + url);
                String result = context.userService().gettingFromRemoteURL(context.configuration().dristhiDjangoBaseURL() + url);
                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(context.getString(R.string.dialog_message));
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String resultData) {
                super.onPostExecute(resultData);
                if (progressDialog.isShowing())
                    progressDialog.hide();
                afterResult.onEvent(resultData);
            }
        }.execute();
    }

    public void villagesFilter(String text) {
        NativeDoctorActivity.village_name_view.setText(text);
        text = text.toLowerCase(Locale.getDefault());
        consultantsList.clear();
        if (text.length() == 0 || text.equals("all")) {
            consultantsList.addAll(searchconsultantsList);
        } else {
            for (DoctorData doctorData : searchconsultantsList) {
//                String womanName = getData(wife_name, doctorData.getFormInformation());
                String villageName = getData(village_name, doctorData.getFormInformation());
                if (villageName != null && villageName.toLowerCase(Locale.getDefault()).contains(text)) {
                    consultantsList.add(doctorData);
                }
            }
        }

        if (NativeDoctorActivity.syncAdapter != null)
            NativeDoctorActivity.syncAdapter.notifyDataSetChanged();
        else
            NativeDoctorActivity.pendingConsultantBaseAdapter.notifyDataSetChanged();
    }

    public void filter(String text) {

        text = text.toLowerCase(Locale.getDefault());
        consultantsList.clear();
        if (text.length() == 0 && text.equalsIgnoreCase("all")) {
            consultantsList.addAll(searchconsultantsList);
        } else {
            for (DoctorData doctorData : searchconsultantsList) {
                String womanName = getData(wife_name, doctorData.getFormInformation());
                if (womanName != null && womanName.toLowerCase(Locale.getDefault()).contains(text)) {
                    consultantsList.add(doctorData);
                }
            }
        }

        if (NativeDoctorActivity.syncAdapter != null)
            NativeDoctorActivity.syncAdapter.notifyDataSetChanged();
        else
            NativeDoctorActivity.pendingConsultantBaseAdapter.notifyDataSetChanged();
    }

    public String getData(String key, String formData) {

        if (formData != null) try {
            JSONObject jsonData = new JSONObject(formData);
            return (jsonData != null && jsonData.has(key) && jsonData.getString(key) != null) ? WordUtils.capitalize(jsonData.getString(key)) : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sort(String sortingOption) {
        switch (sortingOption) {
            case sorting_anc:
                consultantsList.clear();
                for (DoctorData doctorData : searchconsultantsList) {
                    String visitType = getData(visit_type, doctorData.getFormInformation());
                    if (visitType != null && visitType.toLowerCase(Locale.getDefault()).contains("anc")) {
                        consultantsList.add(doctorData);
                    }
                }
                Collections.sort(consultantsList, DoctorData.womanNameComparator);
//                consultantsList.addAll(remainingList);
                break;
            case sorting_pnc:
                consultantsList.clear();
                for (DoctorData doctorData : searchconsultantsList) {
                    String visitType = getData(visit_type, doctorData.getFormInformation());
                    if (visitType != null && visitType.toLowerCase(Locale.getDefault()).contains("pnc")) {
                        consultantsList.add(doctorData);
                    }
                }
                Collections.sort(consultantsList, DoctorData.womanNameComparator);
//                consultantsList.addAll(remainingList);
                break;
            case sorting_child:
                consultantsList.clear();
                for (DoctorData doctorData : searchconsultantsList) {
                    String visitType = getData(visit_type, doctorData.getFormInformation());
                    if (visitType != null && visitType.toLowerCase(Locale.getDefault()).contains("child")) {
                        consultantsList.add(doctorData);
                    }
                }
                Collections.sort(consultantsList, DoctorData.womanNameComparator);
//                consultantsList.addAll(remainingList);
                break;
            case sorting_hr:
                consultantsList.clear();
                for (DoctorData doctorData : searchconsultantsList) {
                    String isHighRisk = getData(DoctorFormDataConstants.isHighRisk, doctorData.getFormInformation());
                    if (isHighRisk != null && isHighRisk.equalsIgnoreCase("yes")) {
                        consultantsList.add(doctorData);
                    }
                }
                Collections.sort(consultantsList, DoctorData.womanNameComparator);
//                consultantsList.addAll(remainingList);
                break;
            default:
                consultantsList.clear();
                for (DoctorData doctorData : searchconsultantsList) {
                    consultantsList.add(doctorData);
                }
                Collections.sort(consultantsList, DoctorData.womanNameComparator);
                break;
        }

        if (NativeDoctorActivity.syncAdapter != null)
            NativeDoctorActivity.syncAdapter.notifyDataSetChanged();
        else
            NativeDoctorActivity.pendingConsultantBaseAdapter.notifyDataSetChanged();
    }


}
