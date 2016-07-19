package org.ei.opensrp.test.vaksinator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.test.R;


/**
 * Created by Iq on 09/06/16, modified by Marwan on 14/07/16
 */
public class VaksinatorRecapitulationActivity extends Activity {

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


        org.ei.opensrp.commonregistry.CommonPersonObjectClients clients = data.getClients();
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

        ImageButton backButton = (ImageButton) findViewById(R.id.btn_back_to_home);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VaksinatorRecapitulationActivity.this, VaksinatorSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        int counter = 0;

        counter = recapitulation(clients,"hb1_kurang_7_hari","-");
        hbUnder7.setText(Integer.toString(counter));
        counter = recapitulation(clients,"hb1_lebih_7_hari","-");
        hbOver7.setText(Integer.toString(counter));
        counter = recapitulation(clients,"bcg_pol_1","-");
        bcg.setText(Integer.toString(counter));
        pol1.setText(Integer.toString(counter));
        counter = recapitulation(clients,"dpt_1_pol_2","-");
        hb1.setText(Integer.toString(counter));
        pol2.setText(Integer.toString(counter));
        counter = recapitulation(clients,"dpt_2_pol_3","-");
        hb2.setText(Integer.toString(counter));
        pol3.setText(Integer.toString(counter));
        counter = recapitulation(clients,"dpt_3_pol_4_ipv","-");
        hb3.setText(Integer.toString(counter));
        pol4.setText(Integer.toString(counter));
        ipv.setText(Integer.toString(counter));
        counter = recapitulation(clients,"imunisasi_campak","-");
        measles.setText(Integer.toString(counter));
        counter = recapitulation(clients,"mutasi_meninggal_kurang_30hari","-");
        diedu30.setText(Integer.toString(counter));
        counter = recapitulation(clients,"mutasi_meninggal_lebih_30hari","-");
        diedo30.setText(Integer.toString(counter));
        counter = recapitulation(clients,"tanggal_pindah","-");
        moving.setText(Integer.toString(counter));
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
}