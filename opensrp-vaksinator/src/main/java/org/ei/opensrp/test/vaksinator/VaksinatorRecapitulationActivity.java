package org.ei.opensrp.test.vaksinator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.test.R;
import org.ei.opensrp.view.activity.ReportsActivity;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Iq on 09/06/16, modified by Marwan on 14/07/16
 */
public class VaksinatorRecapitulationActivity extends ReportsActivity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    //  private static KmsCalc  kmsCalc;

    //image retrieving

    public static CommonPersonObjectClient controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.smart_register_jurim_client_reporting);

        Context otherContext = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        org.ei.opensrp.commonregistry.CommonPersonObjectController data = new org.ei.opensrp.commonregistry.CommonPersonObjectController(otherContext.allCommonsRepositoryobjects("anak"),
                otherContext.allBeneficiaries(), otherContext.listCache(),
                otherContext.personObjectClientsCache(), "nama_bayi", "anak", "nama_orang_tua", org.ei.opensrp.commonregistry.CommonPersonObjectController.ByColumnAndByDetails.byDetails);

        final org.ei.opensrp.commonregistry.CommonPersonObjectClients clients = data.getClients();
        final LocalVariable var = new LocalVariable();

        var.setDefaultSpinnerDate();

        var.monthSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> parentView, View selectedItemView, int position, long id) {
                        updateView(var,clients,var.monthSpinner.getSelectedItemPosition()+1,Integer.parseInt(var.yearSpinner.getSelectedItem().toString()));
                        var.setSubtitle(((org.ei.opensrp.commonregistry.CommonPersonObjectClient) clients.get(4)).getDetails().get("desa"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }
                }
        );

        var.yearSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        updateView(var,clients,var.monthSpinner.getSelectedItemPosition()+1,Integer.parseInt(var.yearSpinner.getSelectedItem().toString()));
                        var.setSubtitle(((org.ei.opensrp.commonregistry.CommonPersonObjectClient)clients.get(4)).getDetails().get("desa"));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }
                }
        );

        ImageButton backButton = (ImageButton) findViewById(R.id.btn_back_to_home);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VaksinatorRecapitulationActivity.this, VaksinatorSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

    }

    private int recapitulation(org.ei.opensrp.commonregistry.CommonPersonObjectClients clients,String fieldName, String keyword){
        int counter = 0;
        org.ei.opensrp.commonregistry.CommonPersonObjectClient cl;
        for(int i=0;i<clients.size();i++){
            cl = ((org.ei.opensrp.commonregistry.CommonPersonObjectClient)clients.get(i));
            if (cl.getDetails().get(fieldName)!=null)
                counter = cl.getDetails().get(fieldName).contains(keyword) ? counter+1:counter;
        }
        return counter;
    }

    private int recapitulation(org.ei.opensrp.commonregistry.CommonPersonObjectClients clients,String fieldName, String keyword, String filter){
        int counter = 0;
        org.ei.opensrp.commonregistry.CommonPersonObjectClient cl;
        for(int i=0;i<clients.size();i++){
            cl = ((org.ei.opensrp.commonregistry.CommonPersonObjectClient)clients.get(i));
            if (cl.getDetails().get(fieldName)!=null)
                counter = cl.getDetails().get(fieldName).contains(keyword) ? counter+1:counter;
        }
        return counter;
    }

    private void updateView(LocalVariable var, org.ei.opensrp.commonregistry.CommonPersonObjectClients clients, int month, int year){
        int counter = 0;

        String keyword = Integer.toString(year)+"-"+(month>9 ? Integer.toString(month):"0"+Integer.toString(month));

        counter = recapitulation(clients,"hb1_kurang_7_hari",keyword);
        var.hbUnder7.setText(Integer.toString(counter));
        counter = recapitulation(clients,"hb1_lebih_7_hari",keyword);
        var.hbOver7.setText(Integer.toString(counter));
        counter = recapitulation(clients,"bcg_pol_1",keyword);
        var.bcg.setText(Integer.toString(counter));
        var.pol1.setText(Integer.toString(counter));
        counter = recapitulation(clients,"dpt_1_pol_2",keyword);
        var.hb1.setText(Integer.toString(counter));
        var.pol2.setText(Integer.toString(counter));
        counter = recapitulation(clients,"dpt_2_pol_3",keyword);
        var.hb2.setText(Integer.toString(counter));
        var.pol3.setText(Integer.toString(counter));
        counter = recapitulation(clients,"dpt_3_pol_4_ipv",keyword);
        var.hb3.setText(Integer.toString(counter));
        var.pol4.setText(Integer.toString(counter));
        var.ipv.setText(Integer.toString(counter));
        counter = recapitulation(clients,"imunisasi_campak",keyword);
        var.measles.setText(Integer.toString(counter));
        counter = recapitulation(clients,"mutasi_meninggal_kurang_30hari",keyword);
        var.diedu30.setText(Integer.toString(counter));
        counter = recapitulation(clients,"mutasi_meninggal_lebih_30hari",keyword);
        var.diedo30.setText(Integer.toString(counter));
        counter = recapitulation(clients,"tanggal_pindah",keyword);
        var.moving.setText(Integer.toString(counter));
    }

    private class LocalVariable{
        TextView hbUnder7 = (TextView) findViewById(R.id.hbUnder7Reporting);
        TextView hbOver7 = (TextView) findViewById(R.id.hbOver7Reporting);
        TextView bcg = (TextView) findViewById(R.id.bcgReporting);
        TextView pol1 = (TextView) findViewById(R.id.pol1Reporting);
        TextView hb1 = (TextView) findViewById(R.id.hb1Reporting);
        TextView pol2 = (TextView) findViewById(R.id.pol2Reporting);
        TextView hb2 = (TextView) findViewById(R.id.hb2Reporting);
        TextView pol3 = (TextView) findViewById(R.id.pol3Reporting);
        TextView hb3 = (TextView) findViewById(R.id.hb3Reporting);
        TextView pol4 = (TextView) findViewById(R.id.pol4Reporting);
        TextView ipv = (TextView) findViewById(R.id.ipvReporting);
        TextView measles = (TextView) findViewById(R.id.measlesReporting);
        TextView diedu30 = (TextView) findViewById(R.id.diedunder30Reporting);
        TextView diedo30 = (TextView) findViewById(R.id.diedover30Reporting);
        TextView moving = (TextView) findViewById(R.id.movingReporting);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);

        Spinner monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);

        void setDefaultSpinnerDate(){
            String[]date = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date())).split("-");
            monthSpinner.setSelection(Integer.parseInt(date[1])-1);
            yearSpinner.setSelection(Integer.parseInt(date[0])-2015);
        }
        void setSubtitle(String villageName){
            subtitle.setText("Rekapitulasi Imunisasi di Desa "+villageName+" Bulan "+monthSpinner.getSelectedItem().toString()+" Tahun "+yearSpinner.getSelectedItem().toString());
        }
    }
}