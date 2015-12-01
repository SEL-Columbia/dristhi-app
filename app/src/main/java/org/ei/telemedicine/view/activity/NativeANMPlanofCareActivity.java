package org.ei.telemedicine.view.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.bluetooth.BlueToothInfoActivity;
import org.ei.telemedicine.view.customControls.CustomFontTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static org.ei.telemedicine.AllConstants.*;

public class NativeANMPlanofCareActivity extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    LinearLayout ll_drugs;
    Context context;
    CheckBox[] checkBox;
    ArrayList<String> drugsList = new ArrayList<String>();
    JSONArray drugsArray = new JSONArray();
    CustomFontTextView save_poc_data;
    String risks = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anm_poc_info);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(AllConstants.ANCVisitFields.RISKS))
            risks = extras.getString(AllConstants.ANCVisitFields.RISKS, "");
//        Toast.makeText(this, risks, Toast.LENGTH_SHORT).show();

        ll_drugs = (LinearLayout) findViewById(R.id.ll_drugs);
        save_poc_data = (CustomFontTextView) findViewById(R.id.bt_info_save);
        context = Context.getInstance();
        String drugsJson = context.allSettings().fetchDrugs();
        save_poc_data.setOnClickListener(this);
        if (!risks.equals("")) {
            try {
                JSONObject jsonData = new JSONObject(drugsJson);
                Iterator<String> keys = jsonData.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (risks.toLowerCase().contains(key.toLowerCase())) {
                        TextView tv_diseaseName = new TextView(this);
                        tv_diseaseName.setText(key);
                        tv_diseaseName.setTextColor(context.getColorResource(R.color.light_blue));
                        tv_diseaseName.setTextSize(20);
                        tv_diseaseName.setPadding(10, 10, 10, 10);
                        ll_drugs.addView(tv_diseaseName);
                        JSONArray drugsArray = jsonData.getJSONArray(key);
                        checkBox = new CheckBox[drugsArray.length()];
                        for (int i = 0; i < drugsArray.length(); i++) {
                            checkBox[i] = new CheckBox(this);
                            checkBox[i].setText(drugsArray.getString(i));
                            checkBox[i].setTextColor(Color.BLACK);
                            checkBox[i].setTextSize(25);
                            checkBox[i].setTag(tv_diseaseName.getText().toString());
                            checkBox[i].setOnCheckedChangeListener(this);
                            ll_drugs.addView(checkBox[i]);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, "No Risks", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (!drugsList.contains(buttonView.getTag().toString() + "-" + buttonView.getText().toString()))
                drugsList.add(buttonView.getTag().toString().replace(" ", "_") + "-" + buttonView.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_info_save:
                for (int i = 0; i < drugsList.size(); i++) {
                    drugsArray.put(drugsList.get(i));
                }
                Intent intent = new Intent();
                intent.putExtra(DRUGS, drugsArray.toString());
                setResult(DRUGS_INFO_RESULT_CODE, intent);
                finish();
        }
    }
}
