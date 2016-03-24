package org.ei.telemedicine.dummy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.ei.telemedicine.R;
import org.ei.telemedicine.view.activity.SecuredActivity;

//public class DummyActivity extends SecuredActivity {
public class DummyActivity2 extends SecuredActivity {
    TextView tv_sam;
    EditText et_value;

    @Override
    public void onCreation() {
        setContentView(R.layout.dummy);
//        tv_sam = (TextView) findViewById(R.id.tv_sample);
        et_value = (EditText) findViewById(R.id.et_sad);
    }

    @Override
    public void onResumption() {

    }
}
