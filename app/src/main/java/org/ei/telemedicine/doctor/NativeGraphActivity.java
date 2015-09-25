package org.ei.telemedicine.doctor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.internal.util.collections.ArrayUtils;

import java.util.ArrayList;
import java.util.Iterator;

import static org.ei.telemedicine.AllConstants.GraphFields.BLOODGLUCOSEDATA;
import static org.ei.telemedicine.AllConstants.GraphFields.BP_DIA;
import static org.ei.telemedicine.AllConstants.GraphFields.BP_SYS;
import static org.ei.telemedicine.AllConstants.GraphFields.FETALDATA;
import static org.ei.telemedicine.AllConstants.GraphFields.TEMPERATURE;
import static org.ei.telemedicine.AllConstants.GraphFields.VISITNUMBER;
import static org.ei.telemedicine.AllConstants.GraphFields.VISIT_DATE;

public class NativeGraphActivity extends Activity {

    private View mChart;

    String vitalsData, vitalType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vitalsData = bundle.getString(AllConstants.VITALS_INFO_RESULT);
            vitalType = bundle.getString(AllConstants.VITAL_TYPE);
            try {
                if (vitalsData != null && new JSONArray(vitalsData).length() != 0)
                    if (vitalType.equals(AllConstants.GraphFields.BP))
                        bpChart(vitalsData);
                    else
                        openChart(vitalsData, vitalType);
                else {
                    Toast.makeText(this, "No vital Data", Toast.LENGTH_SHORT).show();
                    this.finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void bpChart(String vitalsData) throws JSONException {

        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<Integer> datalist1 = new ArrayList<Integer>();
        ArrayList<Integer> datalist2 = new ArrayList<Integer>();
        ArrayList<String> datelist = new ArrayList<String>();
        int[] vitalReadings1, vitalReadings2;
        String[] vitalDates;
        JSONArray vitalsArray = new JSONArray(vitalsData);
        for (int i = 0; i < vitalsArray.length(); i++) {
            datalist1.add(getIntDatafromJson(vitalsArray.getJSONObject(i).toString(), BP_SYS));
            datalist2.add(getIntDatafromJson(vitalsArray.getJSONObject(i).toString(), BP_DIA));
            datelist.add(getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISIT_DATE) + "\n" + (!getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER).equals("") ? "ANC- " + getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER) : ""));
        }
        vitalReadings1 = new int[datalist1.size()];
        vitalReadings2 = new int[datalist2.size()];
        vitalDates = new String[datelist.size()];
        for (int i = 0; i < datalist1.size(); i++) {
            vitalReadings1[i] = datalist1.get(i).intValue();
            vitalDates[i] = datelist.get(i).toString();
        }
        for (int i = 0; i < datalist2.size(); i++) {
            vitalReadings2[i] = datalist2.get(i).intValue();
            vitalDates[i] = datelist.get(i).toString();
        }
        XYSeries vitalData = new XYSeries(BP_SYS);
        for (int i = 0; i < vitalReadings1.length; i++) {
            vitalData.add(i, vitalReadings1[i]);
        }
        XYSeries vitalData2 = new XYSeries(BP_DIA);
        for (int i = 0; i < vitalReadings2.length; i++) {
            vitalData2.add(i, vitalReadings2[i]);
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(vitalData);
        dataset.addSeries(vitalData2);

        // Creating XYSeriesRenderer to customize vitalData
        XYSeriesRenderer dataRenderer = new XYSeriesRenderer();
        dataRenderer.setColor(Color.RED);
        dataRenderer.setPointStyle(PointStyle.CIRCLE);
        dataRenderer.setFillPoints(true);
        dataRenderer.setLineWidth(3);
        dataRenderer.setDisplayChartValues(true);
        dataRenderer.setDisplayBoundingPoints(true);
        dataRenderer.setHighlighted(true);

        XYSeriesRenderer dataRenderer2 = new XYSeriesRenderer();
        dataRenderer2.setColor(Color.BLUE);
        dataRenderer2.setPointStyle(PointStyle.SQUARE);
        dataRenderer2.setFillPoints(true);
        dataRenderer2.setLineWidth(3);
        dataRenderer2.setDisplayChartValues(true);
        dataRenderer2.setDisplayBoundingPoints(true);
        dataRenderer2.setHighlighted(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Vital Readings");
        multiRenderer.setXTitle("Visit Date");
        multiRenderer.setYTitle("Readings");
        multiRenderer.setZoomButtonsVisible(false);
        multiRenderer.setAxisTitleTextSize(20);

        multiRenderer.setXLabelsColor(Color.parseColor("#ff0099cc"));
        multiRenderer.setXLabelsPadding(5);
        for (int i = 0; i < vitalDates.length; i++) {
            multiRenderer.addXTextLabel(i, vitalDates[i]);
        }

        multiRenderer.addSeriesRenderer(dataRenderer);
        multiRenderer.addSeriesRenderer(dataRenderer2);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
        mChart = ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);
        chartContainer.addView(mChart);
    }

    private void openChart(String vitalsData, String vitalType) throws JSONException {

        ArrayList<Double> datalist = new ArrayList<Double>();
        ArrayList<String> datelist = new ArrayList<String>();
        int[] vitalReadings;
        String[] vitalDates;
        JSONArray vitalsArray = new JSONArray(vitalsData);
        for (int i = 0; i < vitalsArray.length(); i++) {
            switch (vitalType) {
                case TEMPERATURE:
                    datalist.add(Double.parseDouble(getStrDatafromJson(vitalsArray.getJSONObject(i).toString(), TEMPERATURE)));
                    datelist.add(getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISIT_DATE) + "\n" + (!getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER).equals("") ? "ANC- " + getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER) : ""));
                    break;
                case BLOODGLUCOSEDATA:
                    datalist.add(getDatafromJson(vitalsArray.getJSONObject(i).toString(), BLOODGLUCOSEDATA));
                    datelist.add(getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISIT_DATE) + "\n" + (!getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER).equals("") ? "ANC- " + getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER) : ""));
                    break;
                case FETALDATA:
                    datalist.add(getDatafromJson(vitalsArray.getJSONObject(i).toString(), FETALDATA));
                    datelist.add(getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISIT_DATE) + "\n" + (!getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER).equals("") ? "ANC- " + getStringDatafromJson(vitalsArray.getJSONObject(i).toString(), VISITNUMBER) : ""));
                    break;
            }
        }
        vitalReadings = new int[datalist.size()];

        vitalDates = new String[datelist.size()];
        for (int i = 0; i < datalist.size(); i++) {
            vitalReadings[i] = datalist.get(i).intValue();
            vitalDates[i] = datelist.get(i).toString();
        }

        XYSeries vitalData = new XYSeries(vitalType);
        for (int i = 0; i < vitalReadings.length; i++) {
            vitalData.add(i, vitalReadings[i]);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(vitalData);

        // Creating XYSeriesRenderer to customize vitalData
        XYSeriesRenderer dataRenderer = new XYSeriesRenderer();
        dataRenderer.setColor(Color.RED);
        dataRenderer.setPointStyle(PointStyle.CIRCLE);
        dataRenderer.setFillPoints(true);
        dataRenderer.setLineWidth(3);
        dataRenderer.setDisplayChartValues(true);
        dataRenderer.setDisplayBoundingPoints(true);
        dataRenderer.setHighlighted(true);


        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Vital Readings");
        multiRenderer.setXTitle("Visit Date");
        multiRenderer.setYTitle("Readings");
        multiRenderer.setZoomButtonsVisible(false);


        multiRenderer.setXLabelsColor(Color.parseColor("#ff0099cc"));
        multiRenderer.setXLabelsPadding(5);
        for (int i = 0; i < vitalDates.length; i++) {
            multiRenderer.addXTextLabel(i, vitalDates[i]);
        }
        multiRenderer.addSeriesRenderer(dataRenderer);


        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
        mChart = ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);
        chartContainer.addView(mChart);
    }

    public String getStringDatafromJson(String jsonStr, String key) {
        if (jsonStr != null) {
            try {
                JSONObject jsonData = new JSONObject(jsonStr);
                return jsonData.has(key) ? jsonData.getString(key) : "";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public int getIntDatafromJson(String jsonStr, String key) {
        if (jsonStr != null) {
            try {
                JSONObject jsonData = new JSONObject(jsonStr);
                return jsonData.has(key) ? jsonData.getInt(key) : 0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public double getDatafromJson(String jsonStr, String key) {
        if (jsonStr != null) {
            try {
                JSONObject jsonData = new JSONObject(jsonStr);
                return jsonData.has(key) ? jsonData.getDouble(key) : 0;
            } catch (JSONException e) {
                Log.e("Ersrot", e.toString());
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String getStrDatafromJson(String jsonStr, String key) {
        if (jsonStr != null) {
            try {
                JSONObject jsonData = new JSONObject(jsonStr);
                String tempVal = "0";
                if (jsonData.has(key) && jsonData.getString(key).contains("-")) {
                    String[] temp = jsonData.getString(key).split("-");
                    tempVal = temp[0];
                }
                return tempVal;
            } catch (JSONException e) {
                Log.e("Ersrot", e.toString());
                e.printStackTrace();
            }
        }
        return "0";
    }
}
