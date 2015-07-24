package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.repository.AllSharedPreferences;

import static android.view.View.*;

/**
 * Created by naveen on 5/19/15.
 */
public class NativeSettingsActivity extends Activity implements OnClickListener {
    private FrameLayout frame_ec_reg, frame_fp_reg, frame_anc_reg, frame_pnc_reg, frame_child_reg;
    AllSharedPreferences allSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        allSharedPreferences = Context.getInstance().allSharedPreferences();
        frame_ec_reg = (FrameLayout) findViewById(R.id.frame_ec_reg);
        frame_fp_reg = (FrameLayout) findViewById(R.id.frame_fp_reg);
        frame_anc_reg = (FrameLayout) findViewById(R.id.frame_anc_reg);
        frame_pnc_reg = (FrameLayout) findViewById(R.id.frame_pnc_reg);
        frame_child_reg = (FrameLayout) findViewById(R.id.frame_child_reg);

        frame_ec_reg.setAlpha(allSharedPreferences.registerState(AllConstants.EC_REGISTERS_KEY)?1: (float) 0.25);
        frame_fp_reg.setAlpha(allSharedPreferences.registerState(AllConstants.FP_REGISTERS_KEY)?1: (float) 0.25);
        frame_anc_reg.setAlpha(allSharedPreferences.registerState(AllConstants.ANC_REGISTERS_KEY)?1: (float) 0.25);
        frame_pnc_reg.setAlpha(allSharedPreferences.registerState(AllConstants.PNC_REGISTERS_KEY)?1: (float) 0.25);
        frame_child_reg.setAlpha(allSharedPreferences.registerState(AllConstants.CHILD_REGISTERS_KEY)?1: (float) 0.25);

        frame_ec_reg.setOnClickListener(this);
        frame_fp_reg.setOnClickListener(this);
        frame_anc_reg.setOnClickListener(this);
        frame_pnc_reg.setOnClickListener(this);
        frame_child_reg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame_ec_reg:
                frame_ec_reg.setAlpha((frame_ec_reg.getAlpha() == 0.25) ? 1 : (float) 0.25);
                allSharedPreferences.saveECRegisterState(frame_ec_reg.getAlpha() == 1 ? true : false);
                break;
            case R.id.frame_fp_reg:
                frame_fp_reg.setAlpha((frame_fp_reg.getAlpha() == 0.25) ? 1 : (float) 0.25);
                allSharedPreferences.saveFPRegisterState(frame_fp_reg.getAlpha() == 1 ? true : false);
                break;
            case R.id.frame_anc_reg:
                frame_anc_reg.setAlpha((frame_anc_reg.getAlpha() == 0.25) ? 1 : (float) 0.25);
                allSharedPreferences.saveANCRegisterState(frame_anc_reg.getAlpha() == 1 ? true : false);
                break;
            case R.id.frame_pnc_reg:
                frame_pnc_reg.setAlpha((frame_pnc_reg.getAlpha() == 0.25) ? 1 : (float) 0.25);
                allSharedPreferences.savePNCRegisterState(frame_pnc_reg.getAlpha() == 1 ? true : false);
                break;
            case R.id.frame_child_reg:
                frame_child_reg.setAlpha((frame_child_reg.getAlpha() == 0.25) ? 1 : (float) 0.25);
                allSharedPreferences.saveCHILDRegisterState(frame_child_reg.getAlpha() == 1 ? true : false);
                break;

        }
    }
}
