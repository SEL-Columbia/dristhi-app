package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.view.customControls.CustomFontTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by naveen on 6/1/15.
 */
public class DoctorPlanofCareActivity extends Activity {
    AutoCompleteTextView act_icd10Diagnosis, act_tests;
    ListView lv_selected_icd10, lv_selected_tests, lv_selected_drugs;
    Switch swich_poc_pending;
    Spinner sp_services, sp_drug_name, sp_drug_frequency, sp_drug_direction, sp_drug_dosage;
    EditText et_drug_qty, et_drug_no_of_days, et_reason, et_advice;

    Button bt_save_plan_of_care;
    ImageButton ib_stop_by, ib_add_drug, ib_anm_logo;
    CustomFontTextView tv_stop_date, tv_health_worker_name, tv_health_worker_village, tv_doc_name, tv_doc_type, tv_mother_name, tv_age, tv_visit_type, tv_village;

    Dialog popup_dialog;
    Object obj;

    static PocBaseAdapter pocDiagnosisBaseAdapter, pocTestBaseAdapter;
    static PocDrugBaseAdapter pocDrugBaseAdapter;

    final ArrayList<String> servicesArrayList = new ArrayList<String>();
    ArrayList<String> selectICD10Diagnosis = new ArrayList<String>();
    ArrayList<String> selectTests = new ArrayList<String>();
    ArrayList<PocDrugData> selectDrugs = new ArrayList<PocDrugData>();

    ArrayList<String> pocDrugNamesList = new ArrayList<String>();
    ArrayList<String> pocDrugFrequenciesList = new ArrayList<String>();
    ArrayList<String> pocDrugDirectionsList = new ArrayList<String>();
    ArrayList<String> pocDrugDosagesList = new ArrayList<String>();
    ArrayList<PocDrugData> pocDrugDatas = new ArrayList<PocDrugData>();

    ArrayList<String> pocTestsList = new ArrayList<String>();
    ArrayList<String> pocServicesList = new ArrayList<String>();
    ArrayList<PocDiagnosis> pocDiagnosises = new ArrayList<PocDiagnosis>();
    ArrayList<PocInvestigation> pocInvestigations = new ArrayList<PocInvestigation>();
    SimpleDateFormat dateFormatter;
    Context context;
    private String TAG = "DoctorPlanOfCareActivity";
    String visitType, visitNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString(AllConstants.DRUG_INFO_RESULT) != null && bundle.getString(DoctorFormDataConstants.documentId) != null && bundle.getString("formData") != null) {
            String resultData = bundle.getString(AllConstants.DRUG_INFO_RESULT);
            final String documentId = bundle.getString(DoctorFormDataConstants.documentId);
            final String formData = bundle.getString("formData");
            try {
                setContentView(R.layout.doc_plan_of_care);
                context = Context.getInstance();
                act_icd10Diagnosis = (AutoCompleteTextView) findViewById(R.id.mact_icd10_diagnosis);
                act_tests = (AutoCompleteTextView) findViewById(R.id.act_tests);
                lv_selected_icd10 = (ListView) findViewById(R.id.lv_selected_icd10);
                lv_selected_tests = (ListView) findViewById(R.id.lv_selected_tests);
                lv_selected_drugs = (ListView) findViewById(R.id.lv_selected_drugs);
                sp_services = (Spinner) findViewById(R.id.sp_services);
                ib_add_drug = (ImageButton) findViewById(R.id.ib_add_drug);

                bt_save_plan_of_care = (Button) findViewById(R.id.bt_save_plan_of_care);
                ib_anm_logo = (ImageButton) findViewById(R.id.iv_anm_logo);
                tv_health_worker_name = (CustomFontTextView) findViewById(R.id.tv_health_worker_name);

                tv_health_worker_village = (CustomFontTextView) findViewById(R.id.tv_health_worker_village_name);
                tv_doc_name = (CustomFontTextView) findViewById(R.id.tv_doc_name);
                tv_doc_type = (CustomFontTextView) findViewById(R.id.tv_doc_type);
                tv_mother_name = (CustomFontTextView) findViewById(R.id.tv_mother_name);
                tv_age = (CustomFontTextView) findViewById(R.id.tv_age);
                tv_visit_type = (CustomFontTextView) findViewById(R.id.tv_visit_type);
                tv_village = (CustomFontTextView) findViewById(R.id.tv_village);


                ib_stop_by = (ImageButton) findViewById(R.id.ib_stop_by_date);
                tv_stop_date = (CustomFontTextView) findViewById(R.id.tv_stop_by_date);
                sp_drug_name = (Spinner) findViewById(R.id.sp_drug_name);
                swich_poc_pending = (Switch) findViewById(R.id.switch_poc_pending);
                sp_drug_direction = (Spinner) findViewById(R.id.sp_drug_direction);
                sp_drug_dosage = (Spinner) findViewById(R.id.sp_drug_dosage);
                sp_drug_frequency = (Spinner) findViewById(R.id.sp_drug_frequency);
                et_drug_no_of_days = (EditText) findViewById(R.id.et_drug_no_of_days);
                et_drug_qty = (EditText) findViewById(R.id.et_drug_qty);
                et_reason = (EditText) findViewById(R.id.et_reason);
                et_advice = (EditText) findViewById(R.id.et_advice);

                dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

//                progressDialog = new ProgressDialog(DoctorPlanofCareActivity.this);
//                progressDialog.setCancelable(false);
//                progressDialog.setMessage(getString(R.string.loggin_in_dialog_message));

                pocServicesList.add("");
                pocDrugNamesList.add(getString(R.string.please_select_drug));
                pocDrugFrequenciesList.add(getString(R.string.please_select_frequency));
                pocDrugDirectionsList.add(getString(R.string.please_select_direction));
                pocDrugDosagesList.add(getString(R.string.please_select_dosage));

                String pocInfoCaseId = context.allDoctorRepository().getPocInfoCaseId(documentId);
                if (pocInfoCaseId != null) {
                    JSONObject pocInfo = new JSONObject(pocInfoCaseId);
                    JSONArray diagnosisArray = pocInfo.has("diagnosis") ? pocInfo.getJSONArray("diagnosis") : new JSONArray();
                    JSONArray investigationsArray = pocInfo.has("investigations") ? pocInfo.getJSONArray("investigations") : new JSONArray();
                    JSONArray drugsArray = pocInfo.has("drugs") ? pocInfo.getJSONArray("drugs") : new JSONArray();
                    Log.e(TAG, "PocInfoCaseId " + diagnosisArray.length() + "--" + drugsArray.length() + "---" + investigationsArray.length() + "----" + pocInfoCaseId);
                    for (int i = 0; i < diagnosisArray.length(); i++) {
                        lv_selected_icd10.setVisibility(View.VISIBLE);
                        selectICD10Diagnosis.add(diagnosisArray.get(i).toString());
                    }
                    for (int i = 0; i < investigationsArray.length(); i++) {
                        lv_selected_tests.setVisibility(View.VISIBLE);
                        selectTests.add(investigationsArray.get(i).toString());
                    }
                    for (int i = 0; i < drugsArray.length(); i++) {
                        lv_selected_drugs.setVisibility(View.VISIBLE);
                        JSONObject drugData = drugsArray.getJSONObject(i);
                        PocDrugData pocDrugData = new PocDrugData();
                        pocDrugData.setDrugName(getData(drugData, "drugName"));
                        pocDrugData.setDosage(getData(drugData, "dosage"));
                        pocDrugData.setFrequncy(getData(drugData, "frequency"));
                        pocDrugData.setDrugNoofDays(getData(drugData, "drugNoOfDays"));
                        pocDrugData.setDrugQty(getData(drugData, "drugQty"));
                        pocDrugData.setDirection(getData(drugData, "direction"));
                        pocDrugData.setDrugStopByDate(getData(drugData, "drugStopDate"));
                        selectDrugs.add(pocDrugData);
                    }
                    et_advice.setText(getData(pocInfo, "advice"));
                }
                final JSONObject pocData = new JSONObject(resultData);
                final JSONObject formDataJson = new JSONObject(formData);
                JSONArray diagnosisJsonArray = pocData.has(AllConstants.POC_DIAGNOSIS) ? pocData.getJSONArray(AllConstants.POC_DIAGNOSIS) : new JSONArray();
                Log.e(TAG, "Diagnosis Size" + diagnosisJsonArray.length());
                for (int i = 0; i < diagnosisJsonArray.length(); i++) {
                    JSONObject diagnosisDataJson = diagnosisJsonArray.getJSONObject(i);
                    PocDiagnosis pocDiagnosis = new PocDiagnosis();
                    pocDiagnosis.setIcd10_name(diagnosisDataJson.getString("ICD10_Name") + "");
                    pocDiagnosis.setIcd10_chapter(diagnosisDataJson.getString("ICD10_Chapter") + "");
                    pocDiagnosis.setIcd10_code(diagnosisDataJson.getString("ICD10_Code") + "");
                    pocDiagnosises.add(pocDiagnosis);
                }

                JSONArray investigationJsonArray = pocData.has(AllConstants.POC_INVESTIGATIONS) ? pocData.getJSONArray(AllConstants.POC_INVESTIGATIONS) : new JSONArray();
                for (int i = 0; i < investigationJsonArray.length(); i++) {
                    JSONObject investigationDataJson = investigationJsonArray.getJSONObject(i);
                    PocInvestigation pocInvestigation = new PocInvestigation();
                    pocInvestigation.setInvestigation_name(investigationDataJson.getString("investigation_name") + "");
                    pocInvestigation.setService_group_name(investigationDataJson.getString("service_group_name") + "");
                    if (!pocServicesList.contains(investigationDataJson.getString("service_group_name")))
                        pocServicesList.add(investigationDataJson.getString("service_group_name"));
                    pocInvestigations.add(pocInvestigation);
                }

                final JSONArray drugsJsonArray = pocData.has(AllConstants.POC_DRUGS) ? pocData.getJSONArray(AllConstants.POC_DRUGS) : new JSONArray();
                for (int i = 0; i < drugsJsonArray.length(); i++) {
                    JSONObject drugJsonObject = drugsJsonArray.getJSONObject(i);
                    PocDrugData pocDrugData = new PocDrugData();
                    pocDrugData.setDrugName(drugJsonObject.getString("name"));
                    pocDrugData.setDirection(drugJsonObject.getString("direction"));
                    pocDrugData.setDosage(drugJsonObject.getString("dosage"));
                    pocDrugData.setFrequncy(drugJsonObject.getString("frequency"));
                    pocDrugDatas.add(pocDrugData);

                    pocDrugNamesList.add(drugJsonObject.getString("name"));
                    if (!pocDrugDirectionsList.contains(drugJsonObject.getString("direction")))
                        pocDrugDirectionsList.add(drugJsonObject.getString("direction").trim());
                    if (!pocDrugDosagesList.contains(drugJsonObject.getString("dosage")))
                        pocDrugDosagesList.add(drugJsonObject.getString("dosage"));
                    if (!pocDrugFrequenciesList.contains(drugJsonObject.getString("frequency")))
                        pocDrugFrequenciesList.add(drugJsonObject.getString("frequency"));

                }
                tv_doc_name.setText(Context.getInstance().allSharedPreferences().fetchRegisteredANM());
                tv_doc_type.setText("");
                tv_mother_name.setText(formDataJson.getString(DoctorFormDataConstants.wife_name));
                tv_age.setText(formDataJson.getString(DoctorFormDataConstants.age));
                visitType = formDataJson.getString(DoctorFormDataConstants.visit_type);
                visitNumber = formDataJson.getString(DoctorFormDataConstants.anc_visit_number);

                tv_visit_type.setText(visitType);
                tv_village.setText(formDataJson.getString(DoctorFormDataConstants.village_name));
                tv_health_worker_name.setText(formDataJson.getString(DoctorFormDataConstants.anmId));
                tv_health_worker_village.setText(formDataJson.getString(DoctorFormDataConstants.village_name));

                Log.e(TAG, selectICD10Diagnosis.size() + "--" + selectDrugs.size() + "--" + selectTests.size());

                pocDiagnosisBaseAdapter = new PocBaseAdapter(DoctorPlanofCareActivity.this, selectICD10Diagnosis);
                lv_selected_icd10.setAdapter(pocDiagnosisBaseAdapter);

                pocTestBaseAdapter = new PocBaseAdapter(DoctorPlanofCareActivity.this, selectTests);
                lv_selected_tests.setAdapter(pocTestBaseAdapter);

                pocDrugBaseAdapter = new PocDrugBaseAdapter(DoctorPlanofCareActivity.this, selectDrugs);
                lv_selected_drugs.setAdapter(pocDrugBaseAdapter);

                sp_services.setAdapter(new ArrayAdapter(DoctorPlanofCareActivity.this, android.R.layout.simple_list_item_1, pocServicesList));
                act_icd10Diagnosis.setAdapter(new DiagnosisArrayAdapter(DoctorPlanofCareActivity.this, R.layout.diagnosis_list_item, pocDiagnosises));

                sp_drug_name.setAdapter(new ArrayAdapter(DoctorPlanofCareActivity.this, android.R.layout.simple_list_item_1, pocDrugNamesList));
                sp_drug_dosage.setAdapter(new ArrayAdapter(DoctorPlanofCareActivity.this, android.R.layout.simple_list_item_1, pocDrugDosagesList));
                sp_drug_frequency.setAdapter(new ArrayAdapter(DoctorPlanofCareActivity.this, android.R.layout.simple_list_item_1, pocDrugFrequenciesList));
                sp_drug_direction.setAdapter(new ArrayAdapter(DoctorPlanofCareActivity.this, android.R.layout.simple_list_item_1, pocDrugDirectionsList));

                ib_anm_logo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog chooseDialog = new Dialog(DoctorPlanofCareActivity.this);
                        chooseDialog.setContentView(R.layout.dialog_box);
                        ListView chooselistview = (ListView) chooseDialog
                                .findViewById(R.id.listview);

                        Button submit = (Button) chooseDialog.findViewById(R.id.btn);
                        submit.setVisibility(View.GONE);
                        String[] channels = new String[]{"AppRTC", "Jitsi"};

                        chooseDialog.show();

                        ArrayAdapter<String> chooseadapter = new ArrayAdapter<String>(
                                DoctorPlanofCareActivity.this,
                                android.R.layout.simple_list_item_1, channels);
                        chooselistview.setAdapter(chooseadapter);

                        chooselistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1,
                                                    int arg2, long arg3) {

                                obj = arg0.getItemAtPosition(arg2);
                                Log.v("Selected Item", obj.toString());
                                if (obj.toString().equals("AppRTC")) {
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.setComponent(new ComponentName("org.appspot.apprtc",
                                            "org.appspot.apprtc.ConnectActivity"));
                                    startActivity(intent);
                                } else {

                                    popup_dialog = new Dialog(DoctorPlanofCareActivity.this);
                                    popup_dialog.setContentView(R.layout.dialog_box);

                                    //   getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                                    ListView listview = (ListView) popup_dialog
                                            .findViewById(R.id.listview);

                                    Button submit = (Button) popup_dialog.findViewById(R.id.btn);
                                    String[] accounts = new String[]{"Dhanush1", "Dhanush2"};

                                    popup_dialog.show();

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                            DoctorPlanofCareActivity.this,
                                            android.R.layout.simple_list_item_1, accounts);
                                    listview.setAdapter(adapter);

                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                                int arg2, long arg3) {
                                            // TODO Auto-generated method stub

                                            obj = arg0.getItemAtPosition(arg2);
                                            Log.v("Selected Item", obj.toString());


                                        }

                                    });

                                    submit.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            popup_dialog.dismiss();
                                            if (obj.toString().equalsIgnoreCase("Dhanush1")) {

                                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                                intent.setComponent(new ComponentName("org.jitsi",
                                                        "org.jitsi.android.gui.LauncherActivity"));

                                                intent.putExtra("Username",
                                                        "dhanush1@jwchat.org");
                                                intent.putExtra("Password", "123456");

                                                startActivity(intent);
                                                finish();

                                            } else if (obj.toString().equalsIgnoreCase("Dhanush2")) {

                                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                                intent.setComponent(new ComponentName("org.jitsi",
                                                        "org.jitsi.android.gui.LauncherActivity"));

                                                intent.putExtra("Username",
                                                        "dhanush2@jwchat.org");
                                                intent.putExtra("Password", "123456");


                                                startActivity(intent);
                                                finish();
                                            } else {

                                            }


                                        }

                                    });
                                }
                            }
                        });
                    }
                });


                sp_services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                                      {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> parent, View view,
                                                                                     int position, long id) {
                                                              TextView tc = (TextView) view;
                                                              if (!tc.getText().toString().equals("")) {
                                                                  String serviceGroupName = pocServicesList.get(position);
                                                                  for (int i = 0; i < pocInvestigations.size(); i++) {
                                                                      PocInvestigation pocInvestigation = pocInvestigations.get(i);
                                                                      if (pocInvestigation.getService_group_name().equals(serviceGroupName) && !pocTestsList.contains(pocInvestigation.getInvestigation_name())) {
                                                                          pocTestsList.add(pocInvestigation.getInvestigation_name());
                                                                      }
                                                                  }
                                                                  for (int i = 0; i < pocTestsList.size(); i++) {
                                                                      Log.e(TAG, pocTestsList.get(i));
                                                                  }
                                                                  act_tests.setAdapter(new ArrayAdapter(DoctorPlanofCareActivity.this, android.R.layout.simple_list_item_1, pocTestsList));
                                                              }
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> parent) {

                                                          }
                                                      }

                );
                sp_drug_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                                       {
                                                           @Override
                                                           public void onItemSelected(AdapterView<?> parent, View view,
                                                                                      int position, long id) {
                                                               TextView tv_drugName = (TextView) view;
                                                               String drugName = tv_drugName.getText().toString();
                                                               for (int i = 0; i < pocDrugDatas.size(); i++) {
                                                                   PocDrugData pocDrugData = pocDrugDatas.get(i);
                                                                   if (pocDrugData.getDrugName().equals(drugName)) {
                                                                       sp_drug_direction.setSelection(pocDrugDirectionsList.indexOf(pocDrugData.getDirection()), true);
                                                                       sp_drug_frequency.setSelection(pocDrugFrequenciesList.indexOf(pocDrugData.getFrequncy()), true);
                                                                       sp_drug_dosage.setSelection(pocDrugDosagesList.indexOf(pocDrugData.getDosage()), true);
                                                                   }
                                                               }
                                                           }

                                                           @Override
                                                           public void onNothingSelected(AdapterView<?> parent) {

                                                           }
                                                       }

                );

                act_icd10Diagnosis.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                                          {
                                                              @Override
                                                              public void onItemClick(AdapterView parent, View view, int position,
                                                                                      long id) {
                                                                  act_icd10Diagnosis.setText("");
                                                                  lv_selected_icd10.setVisibility(View.VISIBLE);
                                                                  String diagnoseCode = pocDiagnosises.get(position).getIcd10_code().toString();
                                                                  String diagnoseName = pocDiagnosises.get(position).getIcd10_name().toString();
                                                                  if (!selectICD10Diagnosis.contains(diagnoseCode + " - " + diagnoseName)) {
                                                                      selectICD10Diagnosis.add(diagnoseCode + " - " + diagnoseName);
                                                                      pocDiagnosisBaseAdapter.notifyDataSetChanged();
                                                                  }

                                                              }
                                                          }

                );
                act_tests.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                                 {
                                                     @Override
                                                     public void onItemClick(AdapterView parent, View view, int position,
                                                                             long id) {
                                                         act_tests.setText("");
                                                         lv_selected_tests.setVisibility(View.VISIBLE);
                                                         TextView tv = (TextView) view;
                                                         String testName = sp_services.getSelectedItem().toString() + "-" + tv.getText().toString();
                                                         if (!selectTests.contains(testName)) {
                                                             selectTests.add(testName);
                                                             pocTestBaseAdapter.notifyDataSetChanged();
                                                         }
                                                         for (int i = 0; i < selectTests.size(); i++) {
                                                             Log.e(TAG, selectTests.get(i));
                                                         }

                                                     }
                                                 }

                );
                ib_add_drug.setOnClickListener(new View.OnClickListener()

                                               {
                                                   @Override
                                                   public void onClick(View v) {
                                                       lv_selected_drugs.setVisibility(View.VISIBLE);
                                                       boolean isDrugExist = false;
                                                       String drugName = sp_drug_name.getSelectedItem().toString();
                                                       String drugFrequency = sp_drug_frequency.getSelectedItem().toString();
                                                       String drugDosage = sp_drug_dosage.getSelectedItem().toString();
                                                       String drugDirection = sp_drug_direction.getSelectedItem().toString();
                                                       String drugNoOfDays = et_drug_no_of_days.getText().toString();
                                                       String drugQty = et_drug_qty.getText().toString();
                                                       String drugStopDate = tv_stop_date.getText().toString();

                                                       PocDrugData pocDrugData = new PocDrugData();
                                                       pocDrugData.setDrugName(drugName);
                                                       pocDrugData.setDosage(drugDosage);
                                                       pocDrugData.setFrequncy(drugFrequency);
                                                       pocDrugData.setDirection(drugDirection);
                                                       pocDrugData.setDrugNoofDays(drugNoOfDays);
                                                       pocDrugData.setDrugQty(drugQty);

                                                       for (int i = 0; i < selectDrugs.size(); i++) {
                                                           PocDrugData pocDrugData1 = selectDrugs.get(i);
                                                           if (pocDrugData1.getDrugName().equals(drugName)) {
                                                               tv_stop_date.setVisibility(View.VISIBLE);
                                                               ib_stop_by.setVisibility(View.VISIBLE);
                                                               isDrugExist = true;
                                                               pocDrugData.setIsDrugDuplicate(true);
                                                               pocDrugData.setDrugStopByDate(drugStopDate);
                                                           }

                                                       }
                                                       if (!drugName.equals(getString(R.string.please_select_drug)) && !drugDirection.equals(getString(R.string.please_select_direction)) && !drugFrequency.equals(getString(R.string.please_select_frequency)) && !drugDosage.equals(getString(R.string.please_select_dosage)) && !drugNoOfDays.equals("") && !drugQty.equals("")) {
                                                           if (isDrugExist) {
                                                               Log.e(TAG, "length " + drugQty.length() + "" + drugNoOfDays.length() + "" + drugStopDate.length() + drugStopDate);
                                                               if (drugQty.length() != 0 && drugNoOfDays.length() != 0 && !drugStopDate.equals("Stop By")) {
                                                                   selectDrugs.add(pocDrugData);
                                                                   pocDrugBaseAdapter.notifyDataSetChanged();
                                                               } else {
                                                                   Toast.makeText(DoctorPlanofCareActivity.this, "Stop Date Need", Toast.LENGTH_SHORT).show();
                                                               }
                                                           } else {
                                                               selectDrugs.add(pocDrugData);
                                                               pocDrugBaseAdapter.notifyDataSetChanged();
                                                           }
                                                       } else
                                                           Toast.makeText(DoctorPlanofCareActivity.this, "All Fields are mandatory", Toast.LENGTH_SHORT).show();
                                                       tv_stop_date.setText("");
                                                       et_drug_no_of_days.setText("");
                                                       et_drug_qty.setText("");
                                                   }
                                               }

                );
                bt_save_plan_of_care.setOnClickListener(new View.OnClickListener()

                                                        {
                                                            @Override
                                                            public void onClick(View v) {
//                                                                if (!swich_poc_pending.isChecked() && et_reason.getText().length()@) {
                                                                Toast.makeText(DoctorPlanofCareActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                                                                try {
                                                                    JSONObject resultJson = new JSONObject();
                                                                    JSONArray drugsArray = new JSONArray();
                                                                    JSONArray diagnosisArray = new JSONArray();
                                                                    JSONArray testsArray = new JSONArray();

                                                                    for (int i = 0; i < selectICD10Diagnosis.size(); i++) {
                                                                        diagnosisArray.put(selectICD10Diagnosis.get(i).toString());
                                                                    }
                                                                    Log.e(TAG, "Drugs Size " + selectDrugs.size() + "");
                                                                    for (int i = 0; i < selectDrugs.size(); i++) {
                                                                        JSONObject drugsJson = new JSONObject();
                                                                        PocDrugData pocDrugData = selectDrugs.get(i);
                                                                        drugsJson.put("direction", pocDrugData.getDirection());
                                                                        drugsJson.put("dosage", pocDrugData.getDosage());
                                                                        drugsJson.put("drugName", pocDrugData.getDrugName());
                                                                        drugsJson.put("drugNoOfDays", pocDrugData.getDrugNoofDays());
                                                                        drugsJson.put("drugQty", pocDrugData.getDrugQty());
                                                                        drugsJson.put("drugStopDate", pocDrugData.getDrugStopByDate());
                                                                        drugsJson.put("frequency", pocDrugData.getFrequncy());
                                                                        drugsArray.put(drugsJson);
                                                                    }

                                                                    for (int i = 0; i < selectTests.size(); i++) {
                                                                        testsArray.put(selectTests.get(i).toString());
                                                                    }
                                                                    resultJson.put("visitType", visitType);
                                                                    resultJson.put("visitNumber", visitNumber);
                                                                    resultJson.put("doctorName", Context.getInstance().allSharedPreferences().fetchRegisteredANM());
                                                                    resultJson.put("planofCareDate", dateFormatter.format(new Date()));
                                                                    resultJson.put("diagnosis", diagnosisArray);
                                                                    resultJson.put("drugs", drugsArray);
                                                                    resultJson.put("investigations", testsArray);
                                                                    resultJson.put("advice", et_advice.getText().toString());
                                                                    //                            resultJson.put("reason", et_reason.getText().toString());
                                                                    Log.e(TAG, "Reason" + et_reason.getText().toString() + "---" + swich_poc_pending.isChecked() + "");
                                                                    Log.e(TAG, "selected Json" + resultJson.toString());
                                                                    if (swich_poc_pending.isChecked() && et_reason.getText().toString().trim().length() != 0) {
                                                                        saveDatainLocal(documentId, resultJson.toString(), et_reason.getText().toString());
//                                                                        saveData(documentId, resultJson.toString(), formDataJson, et_reason.getText().toString());
                                                                    } else if (!swich_poc_pending.isChecked() && (diagnosisArray.length() != 0 || drugsArray.length() != 0 || testsArray.length() != 0 || resultJson.getString("advice").length() != 0)) {
                                                                        saveData(documentId, resultJson.toString(), formDataJson, et_reason.getText().toString());
                                                                    } else {
                                                                        Toast.makeText(DoctorPlanofCareActivity.this, "Plan of care / Reason for Pending must be given", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
//                                                                } else
//                                                                    Toast.makeText(DoctorPlanofCareActivity.this, "Reason mandatory", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                );
                swich_poc_pending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

                                                             {
                                                                 @Override
                                                                 public void onCheckedChanged(CompoundButton buttonView,
                                                                                              boolean isChecked) {
                                                                     if (isChecked) {
                                                                         et_reason.setVisibility(View.VISIBLE);
                                                                     } else {
                                                                         et_reason.setVisibility(View.GONE);
                                                                     }
                                                                 }
                                                             }

                );
                ib_stop_by.setOnClickListener(new View.OnClickListener()

                                              {
                                                  @Override
                                                  public void onClick(View v) {
                                                      Calendar calendar = Calendar.getInstance();
                                                      new DatePickerDialog(DoctorPlanofCareActivity.this, new DatePickerDialog.OnDateSetListener() {
                                                          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                              Calendar newDate = Calendar.getInstance();
                                                              newDate.set(year, monthOfYear, dayOfMonth);
                                                              tv_stop_date.setVisibility(View.VISIBLE);
                                                              tv_stop_date.setText(dateFormatter.format(newDate.getTime()));
                                                          }

                                                      }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                                                  }
                                              }

                );

            } catch (
                    Exception e
                    )

            {
                e.printStackTrace();
                Toast.makeText(DoctorPlanofCareActivity.this, "Json Failure" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else

        {
            Log.e(TAG, "No Data");
        }
    }

    private String getData(JSONObject jsonData, String key) {
        Log.e(TAG, key);
        if (jsonData != null) try {
//            JSONObject jsonData = new JSONObject(formData);
            Log.e(TAG, jsonData.get(key) + key);
            return (jsonData != null && jsonData.has(key)) ? jsonData.getString(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveDatainLocal(String documentId, String pocJsonInfo, String
            pocPendingInfo) {
        context.allDoctorRepository().updatePocInLocal(documentId, pocJsonInfo, pocPendingInfo);
        Toast.makeText(DoctorPlanofCareActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        gotoHome();
    }

    private void saveData(final String docId, String pocJsonData, JSONObject formDataJson, String pocPendingReason) {
        savePocData(docId, pocJsonData, formDataJson, pocPendingReason, new Listener<String>() {
            //        getDataFromServer(new Listener<String>() {
            public void onEvent(String resultData) {
                if (resultData != null) {
                    context.allDoctorRepository().deleteUseCaseId(docId);
                    gotoHome();
                } else {
                    Toast.makeText(DoctorPlanofCareActivity.this, "Data is not saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void gotoHome() {
        startActivity(new Intent(DoctorPlanofCareActivity.this, NativeDoctorActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void savePocData(final String docId, final String pocJsonData,
                            final JSONObject formDataJson, final String pendingReason, final Listener<String> afterResult) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                String result = null;
                try {
                    List<NameValuePair> _params = new ArrayList<NameValuePair>();
                    _params.add(new BasicNameValuePair("docid", docId));
                    _params.add(new BasicNameValuePair("doctorid", context.allSharedPreferences().fetchRegisteredANM()));
                    _params.add(new BasicNameValuePair("pocinfo", pocJsonData));
                    _params.add(new BasicNameValuePair("pending", pendingReason));
                    _params.add(new BasicNameValuePair("entityid", formDataJson.getString(DoctorFormDataConstants.entityId)));

                    switch (formDataJson.getString(DoctorFormDataConstants.visit_type)) {
                        case DoctorFormDataConstants.ancvisit:
                            _params.add(new BasicNameValuePair("visitid", formDataJson.getString(DoctorFormDataConstants.anc_entityId)));
                            break;
                        case DoctorFormDataConstants.pncVisit:
                            _params.add(new BasicNameValuePair("visitid", formDataJson.getString(DoctorFormDataConstants.pnc_entityId)));
                            break;
                        case DoctorFormDataConstants.childVisit:
                            _params.add(new BasicNameValuePair("visitid", formDataJson.getString(DoctorFormDataConstants.child_entityId)));
                            break;
                    }

                    String encodedParams = URLEncodedUtils.format(_params, "utf-8");
                    String url = AllConstants.POC_DATA_SAVE_URL_PATH + encodedParams;
                    Log.e("Url", url);
                    result = context.userService().gettingFromRemoteURL(url);
                    Log.e(TAG, "Save POC Info " + result);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPostExecute(String resultData) {
                super.onPostExecute(resultData);
                afterResult.onEvent(resultData);
            }
        }.execute();

    }

}