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
        TextView fatherName = (TextView) findViewById(R.id.fatherName);
        TextView posyandu = (TextView) findViewById(R.id.posyandu);
        TextView village = (TextView) findViewById(R.id.village);
        TextView dateOfBirth = (TextView) findViewById(R.id.dateOfBirth);
        TextView birthWeight = (TextView) findViewById(R.id.birthWeight);
        TextView currentWeight = (TextView) findViewById(R.id.currentWeight);

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

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(VaksinatorDetailActivity.this, VaksinatorSmartRegisterActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        nama.setText(controller.getDetails().get("childName") != null ? controller.getDetails().get("childName") : " ");
        motherName.setText(controller.getDetails().get("motherName") != null ? controller.getDetails().get("motherName") : " ");
        fatherName.setText(controller.getDetails().get("fatherName") != null? controller.getDetails().get("fatherName"):" ");
        village.setText(controller.getDetails().get("village") != null? controller.getDetails().get("village"):" ");
        posyandu.setText(controller.getDetails().get("posyandu") != null? controller.getDetails().get("posyandu"):" ");
        dateOfBirth.setText(controller.getDetails().get("dateOfBirth") != null? controller.getDetails().get("dateOfBirth"):" ");
        birthWeight.setText(controller.getDetails().get("birthWeight") != null? controller.getDetails().get("birthWeight"):" ");
        currentWeight.setText(controller.getDetails().get("currentWeight") != null? controller.getDetails().get("currentWeight"):" ");

        hb1Under7.setText(controller.getDetails().get("hb1Under7") != null? controller.getDetails().get("hb1Under7"):" ");
        hb1After7.setText(controller.getDetails().get("hb1After7") != null? controller.getDetails().get("hb1After7"):" ");
        bcg.setText(controller.getDetails().get("bcg") != null? controller.getDetails().get("bcg"):" ");
        pol1.setText(controller.getDetails().get("pol1") != null? controller.getDetails().get("pol1"):" ");
        dpt1.setText(controller.getDetails().get("dpt1") != null? controller.getDetails().get("dpt1"):" ");
        pol2.setText(controller.getDetails().get("pol2") != null? controller.getDetails().get("pol2"):" ");
        dpt2.setText(controller.getDetails().get("dpt2") != null? controller.getDetails().get("dpt2"):" ");
        pol3.setText(controller.getDetails().get("pol3") != null? controller.getDetails().get("pol3"):" ");
        dpt3.setText(controller.getDetails().get("dpt3") != null? controller.getDetails().get("dpt3"):" ");
        pol4.setText(controller.getDetails().get("pol4") != null? controller.getDetails().get("pol4"):" ");
        measles.setText(controller.getDetails().get("measles") != null? controller.getDetails().get("measles"):" ");
        mutationUnder30.setText(controller.getDetails().get("mutationUnder30") != null? controller.getDetails().get("mutationUnder30"):" ");
        mutationAfter30.setText(controller.getDetails().get("mutationAfter30") != null? controller.getDetails().get("mutationAfter30"):" ");
        mutationMoving.setText(controller.getDetails().get("mutationMoving") != null? controller.getDetails().get("mutationMoving"):" ");
        complete.setText(controller.getDetails().get("complete") != null? controller.getDetails().get("complete"):" ");
        additionalDPT.setText(controller.getDetails().get("additionalDPT") != null? controller.getDetails().get("additionalDPT"):" ");
        additionalMeasles.setText(controller.getDetails().get("additionalMeasles") != null? controller.getDetails().get("additionalMeasles"):" ");
    }
}
