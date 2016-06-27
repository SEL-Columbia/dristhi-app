package org.ei.opensrp.test.vaksinator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.test.R;

/**
 * Created by Iq on 09/06/16.
 */
public class VaksinatorDetailActivity extends Activity {

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
        setContentView(R.layout.smart_register_jurim_detail_client);

        //profile
        TextView nama = (TextView) findViewById(R.id.childName);
        TextView motherName = (TextView) findViewById(R.id.motherName);
        //TextView fatherName = (TextView) findViewById(R.id.fatherName);
        TextView posyandu = (TextView) findViewById(R.id.posyandu);
        TextView village = (TextView) findViewById(R.id.village);
        TextView dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        TextView birthWeight = (TextView) findViewById(R.id.birthWeight);
        //TextView currentWeight = (TextView) findViewById(R.id.currentWeight);

        //vaccination date
        TextView hb1Under7 = (TextView) findViewById(R.id.hb1under7);
        TextView hb1After7 = (TextView) findViewById(R.id.hb1after7);
        TextView bcg = (TextView) findViewById(R.id.bcg);
        TextView pol1 = (TextView) findViewById(R.id.pol1);
        TextView dpt1 = (TextView) findViewById(R.id.dpt1);
        TextView pol2 = (TextView) findViewById(R.id.pol2);
        TextView dpt2 = (TextView) findViewById(R.id.dpt2);
        TextView pol3 = (TextView) findViewById(R.id.pol3);
        TextView dpt3 = (TextView) findViewById(R.id.dpt3);
        TextView pol4 = (TextView) findViewById(R.id.pol4);
        TextView measles = (TextView) findViewById(R.id.measles);
        TextView mutationUnder30 = (TextView) findViewById(R.id.mutationUnder30);
        TextView mutationAfter30 = (TextView) findViewById(R.id.mutationAfter30);
        TextView mutationMoving = (TextView) findViewById(R.id.mutationMoving);
        TextView complete = (TextView) findViewById(R.id.complete);
        TextView additionalDPT = (TextView) findViewById(R.id.additionalDPT);
        TextView additionalMeasles = (TextView) findViewById(R.id.additionalMeasles);
        ImageView photo = (ImageView)findViewById(R.id.photo);

        ImageButton backButton = (ImageButton) findViewById(R.id.btn_back_to_home);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VaksinatorDetailActivity.this, VaksinatorSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        nama.setText(": "+(controller.getDetails().get("nama_bayi") != null ? controller.getDetails().get("nama_bayi") : "-"));
        motherName.setText(": "+(controller.getDetails().get("nama_orang_tua") != null ? controller.getDetails().get("nama_orang_tua") : "-"));
        //fatherName.setText(": "+(controller.getDetails().get("fatherName") != null? controller.getDetails().get("fatherName"):"-"));
        village.setText(": "+(controller.getDetails().get("village") != null? controller.getDetails().get("village"):"-"));
        posyandu.setText(": "+(controller.getDetails().get("nama_lokasi") != null? controller.getDetails().get("nama_lokasi"):"-"));
        dateOfBirth.setText(": "+(controller.getDetails().get("tanggal_lahir") != null? controller.getDetails().get("tanggal_lahir"):"-"));
        birthWeight.setText(": "+(controller.getDetails().get("berat_badan_saat_lahir") != null? controller.getDetails().get("berat_badan_saat_lahir"):"-"));
        //currentWeight.setText(": "+(controller.getDetails().get("currentWeight") != null? controller.getDetails().get("currentWeight"):"-"));

        hb1Under7.setText(": "+(controller.getDetails().get("hb1_kurang_7_hari") != null? controller.getDetails().get("hb1_kurang_7_hari"):"-"));
        hb1After7.setText(": "+(controller.getDetails().get("hb1_lebih_7_hari") != null? controller.getDetails().get("hb1_lebih_7_hari"):"-"));
        bcg.setText(": "+(controller.getDetails().get("bcg_pol_1") != null? controller.getDetails().get("bcg_pol_1"):"-"));
        pol1.setText(": "+(controller.getDetails().get("bcg_pol_1") != null? controller.getDetails().get("bcg_pol_1"):"-"));
        dpt1.setText(": "+(controller.getDetails().get("dpt_1_pol_2") != null? controller.getDetails().get("dpt_1_pol_2"):"-"));
        pol2.setText(": "+(controller.getDetails().get("dpt_1_pol_2") != null? controller.getDetails().get("dpt_1_pol_2"):"-"));
        dpt2.setText(": "+(controller.getDetails().get("dpt_2_pol_3") != null? controller.getDetails().get("dpt_2_pol_3"):"-"));
        pol3.setText(": "+(controller.getDetails().get("dpt_2_pol_3") != null? controller.getDetails().get("dpt_2_pol_3"):"-"));
        dpt3.setText(": "+(controller.getDetails().get("dpt_3_pol_4_ipv") != null? controller.getDetails().get("dpt_3_pol_4_ipv"):"-"));
        pol4.setText(": "+(controller.getDetails().get("dpt_3_pol_4_ipv") != null? controller.getDetails().get("dpt_3_pol_4_ipv"):"-"));
        measles.setText(": "+(controller.getDetails().get("imunisasi_campak") != null? controller.getDetails().get("imunisasi_campak"):"-"));
        mutationUnder30.setText(": "+(controller.getDetails().get("mutasi_meninggal_kurang_30hari") != null? controller.getDetails().get("mutasi_meninggal_kurang_30hari"):"-"));
        mutationAfter30.setText(": "+(controller.getDetails().get("mutasi_meninggal_lebih_30hari") != null? controller.getDetails().get("mutasi_meninggal_lebih_30hari"):"-"));
        mutationMoving.setText(": "+(controller.getDetails().get("tanggal_pindah") != null? controller.getDetails().get("tanggal_pindah"):"-"));
        complete.setText(": "+(controller.getDetails().get("imunisasi_lengkap") != null? controller.getDetails().get("imunisasi_lengkap"):"-"));
        additionalDPT.setText(": " + (controller.getDetails().get("dpt_hb_campak_lanjutan") != null ? controller.getDetails().get("dpt_hb_campak_lanjutan") : "-"));
        additionalMeasles.setText(": " + (controller.getDetails().get("dpt_hb_campak_lanjutan") != null ? controller.getDetails().get("dpt_hb_campak_lanjutan") : "-"));

        if(controller.getDetails().get("jenis_kelamin").contains("l")){
            photo.setImageResource(R.drawable.child_boy_infant);
        }else{
            photo.setImageResource(R.drawable.child_girl_infant);
        }
    }
}
