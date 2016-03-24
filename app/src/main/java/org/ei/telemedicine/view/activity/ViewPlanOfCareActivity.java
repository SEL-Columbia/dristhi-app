package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.doctor.DoctorFormDataConstants;
import org.ei.telemedicine.domain.Child;
import org.ei.telemedicine.domain.EligibleCouple;
import org.ei.telemedicine.domain.Mother;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naveen on 6/10/15.
 */
public class ViewPlanOfCareActivity extends SecuredActivity {
    EditText et_anc_num, et_woman_name, et_plan_of_care_date, et_doc_name, et_investigations, et_drugs, et_advice, et_diagnosis;
    Button bt_close;
    String entityId;
    private String TAG = "ViewPlanofCareActivity";
    String visitType, intentVisitType;
    TextView tv_anc_number_title;

    @Override
    public void onCreation() {
        Bundle bundle = getIntent().getExtras();
//        et_anc_num = (EditText) findViewById(R.id.et_anc_num);
        if (bundle != null) {
            entityId = bundle.getString(AllConstants.ENTITY_ID);
            intentVisitType = bundle.getString(AllConstants.VISIT_TYPE);

            setContentView(R.layout.view_anc_poc);
            bt_close = (Button) findViewById(R.id.bt_close);
//            bt_history = (Button) findViewById(R.id.bt_history);
            et_anc_num = (EditText) findViewById(R.id.et_anc_num);
            et_woman_name = (EditText) findViewById(R.id.et_woman_name);
            et_plan_of_care_date = (EditText) findViewById(R.id.et_plan_of_care_date);
            et_doc_name = (EditText) findViewById(R.id.et_doctor_name);
            et_investigations = (EditText) findViewById(R.id.et_investigations);
            et_drugs = (EditText) findViewById(R.id.et_drugs);
            et_advice = (EditText) findViewById(R.id.et_advice_data);
            et_diagnosis = (EditText) findViewById(R.id.et_diagnosis);
            tv_anc_number_title = (TextView) findViewById(R.id.tv_anc_num_title);

//            bt_history.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(ViewPlanOfCareActivity.this, "Poc History", Toast.LENGTH_SHORT).show();
//                }
//            });
            bt_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            if (!intentVisitType.equalsIgnoreCase("child")) {
                Mother mother = Context.getInstance().childService().getMotherUseEntityId(entityId);
                EligibleCouple eligibleCouple = Context.getInstance().allEligibleCouples().findByCaseID(mother.ecCaseId());
                String docPocInfo = mother.getDetail("docPocInfo");
                tv_anc_number_title.setText(intentVisitType.equals(DoctorFormDataConstants.ancvisit) ? "ANC Number" : "PNC Number");
                if (docPocInfo != null && !docPocInfo.equals("")) {
                    Log.e(TAG, "Doc Poc" + docPocInfo);
                    try {
                        JSONArray docPocInfoArray = new JSONArray(docPocInfo);
                        JSONObject pocInfo = docPocInfoArray.length() != 0 ? docPocInfoArray.getJSONObject(docPocInfoArray.length() - 1)
                                : new JSONObject();
                        if (pocInfo.getString("pending").length() == 0) {
                            JSONObject pocJson = pocInfo.has("poc") ? new JSONObject(pocInfo.getString("poc")) : new JSONObject();
                            Log.e("Pox", pocJson.toString());
                            visitType = getDataFromJson(pocJson.toString(), "visitType");
                            if (visitType.equals(intentVisitType)) {
                                et_woman_name.setText(eligibleCouple.wifeName());
                                et_investigations.setText(getDatafromArray(pocJson.getJSONArray("investigations").toString()));
                                et_doc_name.setText(getDataFromJson(pocJson.toString(), "doctorName"));
                                et_anc_num.setText(mother.getDetail("ancNumber"));
                                if (visitType.equals("PNC")) {
                                    tv_anc_number_title.setText("PNC Number");
                                    et_anc_num.setText(mother.getDetail("pncNumber"));
                                }
                                et_plan_of_care_date.setText(getDataFromJson(pocJson.toString(), "planofCareDate"));
                                et_drugs.setText(getDatafromDrugsArray(pocJson.getString("drugs")));
                                et_diagnosis.setText(getDatafromArray(pocJson.getJSONArray("diagnosis").toString()));
                                Log.e(TAG, "Advice" + pocJson.getString("advice"));
                                et_advice.setText(getDataFromJson(pocJson.toString(), "advice"));
                            }
                        } else {
                            Toast.makeText(ViewPlanOfCareActivity.this, "Pending= " + pocInfo.getString("pending"), Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(ViewPlanOfCareActivity.this).setTitle("Poc pending reason").setMessage(pocInfo.getString("pending")).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ViewPlanOfCareActivity.this.finish();
                                }
                            }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showNoPoc();
                    Toast.makeText(ViewPlanOfCareActivity.this, "No Plan Of Care", Toast.LENGTH_SHORT).show();
                }
            } else {
                tv_anc_number_title.setVisibility(View.GONE);
                et_anc_num.setVisibility(View.GONE);
                Log.e("Entity Id Child", entityId);
                Child child = Context.getInstance().allBeneficiaries().findChild(entityId);
                Mother mother = Context.getInstance().childService().getMotherUseEntityId(child.motherCaseId());
                EligibleCouple eligibleCouple = Context.getInstance().allEligibleCouples().findByCaseID(mother.ecCaseId());
                String docPocInfo = child.getDetail("docPocInfo");
                if (docPocInfo != null && !docPocInfo.equals("")) {
                    Log.e(TAG, "Child Doc Poc" + docPocInfo);
                    try {
                        JSONArray docPocInfoArray = new JSONArray(docPocInfo);
                        JSONObject pocInfo = docPocInfoArray.length() != 0 ? docPocInfoArray.getJSONObject(docPocInfoArray.length() - 1)
                                : new JSONObject();
                        if (pocInfo.getString("pending").length() == 0) {
                            JSONObject pocJson = pocInfo.has("poc") ? new JSONObject(pocInfo.getString("poc")) : new JSONObject();
                            visitType = getDataFromJson(pocJson.toString(), "visitType");
                            if (visitType.equalsIgnoreCase(intentVisitType)) {
                                et_woman_name.setText(eligibleCouple.wifeName());
                                et_investigations.setText(getDatafromArray(pocJson.getJSONArray("investigations").toString()));
                                et_doc_name.setText(getDataFromJson(pocJson.toString(), "doctorName"));
//                          et_anc_num.setText(mother.getDetail("ancNumber"));
//                          tv_anc_number_title.setText("PNC Number");
                                et_plan_of_care_date.setText(getDataFromJson(pocJson.toString(), "planofCareDate"));
                                et_drugs.setText(getDatafromDrugsArray(pocJson.getString("drugs")));
                                et_diagnosis.setText(getDatafromArray(pocJson.getJSONArray("diagnosis").toString()));
                                Log.e(TAG, "Advice" + pocJson.getString("advice"));
                                et_advice.setText(getDataFromJson(pocJson.toString(), "advice"));
                            }
                        } else {
                            Toast.makeText(ViewPlanOfCareActivity.this, "Pending= " + pocInfo.getString("pending"), Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(ViewPlanOfCareActivity.this).setTitle("Poc pending reason").setMessage(pocInfo.getString("pending")).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ViewPlanOfCareActivity.this.finish();
                                }
                            }).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showNoPoc();
                    Toast.makeText(ViewPlanOfCareActivity.this, "No Plan Of Care", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    public void onResumption() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void showNoPoc() {
        new AlertDialog.Builder(ViewPlanOfCareActivity.this).setTitle("Plan of care").setMessage("There is no plan of care.").setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ViewPlanOfCareActivity.this.finish();
            }
        }).show();
    }

    public String getDatafromArray(String jsonArray) {
        try {
            String result = "";
            if (jsonArray != null) {
                JSONArray jsonArray1 = new JSONArray(jsonArray);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    result = result + "\n" + jsonArray1.getString(i);
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getDatafromDrugsArray(String jsonArray) {
        try {
            Log.e(TAG, "JsonArray " + jsonArray);
            String result = "";
            if (jsonArray != null) {
                JSONArray jsonArray1 = new JSONArray(jsonArray);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    String data = jsonObject.getString("drugName") + "-" + jsonObject.getString("direction") + "-" + jsonObject.getString("dosage") + "-" + jsonObject.getString("frequency") + "- Days :" + jsonObject.getString("drugNoOfDays") + "- Qty :" + jsonObject.getString("drugQty");
                    result = result + "\n" + data;
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
