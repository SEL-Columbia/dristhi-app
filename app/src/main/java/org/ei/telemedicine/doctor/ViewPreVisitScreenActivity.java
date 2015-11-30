package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naveen on 9/15/15.
 */
public class ViewPreVisitScreenActivity extends Activity {

    EditText et_plan_of_care_date, et_doc_name, et_investigations, et_drugs, et_advice, et_diagnosis, et_risks_observed;
    Button bt_close;
    String entityId;
    private String TAG = "ViewPreVisitScreenActivity";
    String formInfo = "formInfo", documentId, pocDocumentId;
    TextView tv_anc_number_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            formInfo = bundle.containsKey(formInfo) ? bundle.getString(formInfo) : "";
            setContentView(R.layout.view_pre_visits);
            bt_close = (Button) findViewById(R.id.bt_close);
            et_plan_of_care_date = (EditText) findViewById(R.id.et_plan_of_care_date);
            et_doc_name = (EditText) findViewById(R.id.et_doctor_name);
            et_investigations = (EditText) findViewById(R.id.et_investigations);
            et_drugs = (EditText) findViewById(R.id.et_drugs);
            et_advice = (EditText) findViewById(R.id.et_advice_data);
            et_risks_observed = (EditText) findViewById(R.id.et_risks_observed);
            et_diagnosis = (EditText) findViewById(R.id.et_diagnosis);
            tv_anc_number_title = (TextView) findViewById(R.id.tv_anc_num_title);

            pocDocumentId = getDataFromJson(formInfo, "id");
            et_risks_observed.setText(getDataFromJson(formInfo, "riskObserved").replace(" ", ","));
            bt_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            try {
                String pocJson = getDataFromJson(formInfo, "docPocInfo");
                if (pocJson != null && !pocJson.equals("")) {
                    JSONArray pocJsonArray = new JSONArray(pocJson);
                    for (int i = 0; i < pocJsonArray.length(); i++) {
                        JSONObject pocDataJson = pocJsonArray.getJSONObject(i);
                        JSONObject pocInfoJson = new JSONObject(pocDataJson.getString("poc"));
                        String pocInfo = pocInfoJson.toString();
                        if (getDataFromJson(pocInfo, "documentId").equals(pocDocumentId)) {

                            JSONArray investigationsArray = new JSONArray(getDataFromJson(pocInfo, "investigations"));
                            String investigations = "", diagnosis = "", drugs = "";
                            for (int investigation = 0; investigation < investigationsArray.length(); investigation++) {
                                investigations = investigations + (!investigations.equals("") ? "," : "") + investigationsArray.getString(i);
                            }
                            JSONArray diagnosisArray = new JSONArray(getDataFromJson(pocInfo, "diagnosis"));
                            for (int d = 0; d < diagnosisArray.length(); d++) {
                                diagnosis = diagnosis + (!diagnosis.equals("") ? "," : "") + diagnosisArray.getString(d);
                            }
                            JSONArray drugsArray = new JSONArray(getDataFromJson(pocInfo, "drugs"));
                            {
                                for (int dru = 0; dru < drugsArray.length(); dru++) {
                                    String drugsJson = drugsArray.getJSONObject(dru).toString();
                                    drugs = drugs + (!drugs.equals("") ? "," : "")
                                            + getDataFromJson(drugsJson, "drugName")
                                            + "-" + getDataFromJson(drugsJson, "dosage")
                                            + "-" + getDataFromJson(drugsJson, "direction")
                                            + "-" + getDataFromJson(drugsJson, "frequency")
                                            + "- Days :" + getDataFromJson(drugsJson, "drugNoOfDays")
                                            + "- Qty :" + getDataFromJson(drugsJson, "drugQty");
                                }
                            }
                            et_plan_of_care_date.setText(getDataFromJson(pocInfo, "planofCareDate"));
                            et_doc_name.setText(getDataFromJson(pocInfo, "doctorName"));
                            et_drugs.setText(drugs);
                            et_investigations.setText(investigations);
                            et_diagnosis.setText(diagnosis);
                            et_advice.setText(getDataFromJson(pocInfo, "advice"));
                            et_doc_name.setText(getDataFromJson(pocInfo, "doctorName"));
                        }

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private String getDataFromJson(String formJson, String key) {
        if (formJson != null && !formJson.equals("")) {
            try {
                JSONObject formInfoJson = new JSONObject(formJson);
                return formInfoJson.has(key) && formInfoJson.getString(key) != null ? formInfoJson.getString(key) : "";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}