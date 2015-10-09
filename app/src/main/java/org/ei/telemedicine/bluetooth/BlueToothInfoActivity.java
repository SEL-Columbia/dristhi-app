package org.ei.telemedicine.bluetooth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.R;
import org.ei.telemedicine.bluetooth.blood.BloodBuf;
import org.ei.telemedicine.bluetooth.bp.BPBuf;
import org.ei.telemedicine.bluetooth.eet.EETBuf;
import org.ei.telemedicine.bluetooth.fetal.FetalBuf;
import org.ei.telemedicine.bluetooth.pulse.BluetoothService;
import org.ei.telemedicine.bluetooth.pulse.CallBack;
import org.ei.telemedicine.bluetooth.pulse.ICallBack;
import org.ei.telemedicine.bluetooth.pulse.PulseBuf;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.sync.DrishtiSyncScheduler;
import org.ei.telemedicine.view.activity.NativeANMPlanofCareActivity;
import org.ei.telemedicine.view.activity.SecuredActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Vector;

import static android.view.View.*;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static org.ei.telemedicine.AllConstants.DRUGS;
import static org.ei.telemedicine.AllConstants.DRUGS_INFO_RESULT_CODE;

public class BlueToothInfoActivity extends SecuredActivity implements OnClickListener,
        ICallBack, OnBluetoothResult, org.ei.telemedicine.bluetooth.bp.ICallBack,
        org.ei.telemedicine.bluetooth.eet.ICallBack, org.ei.telemedicine.bluetooth.fetal.ICallBack, org.ei.telemedicine.bluetooth.blood.ICallBack {

    org.ei.telemedicine.view.customControls.CustomFontTextView bt_save;
    String entityId, instanceId, formName;
    int subFormCount;

    ImageView iv_bp, iv_steh, iv_bgm, iv_eet, iv_fetal, iv_poc;
    EditText et_bp_sys, et_bp_dia, et_steh, et_bgm, et_fetal, et_eet, et_bp_heart;
    TextView tv_eet_cen, tv_fetal;
    LinearLayout ll_fetal_data, ll_fetal, ll_eet, ll_bp, ll_bgm, ll_poc, ll_steh, ll_eet_child, ll_eet_child_data;

    org.ei.telemedicine.bluetooth.pulse.BluetoothService pulseService;
    org.ei.telemedicine.bluetooth.pulse.CallBack pulsecall;

    org.ei.telemedicine.bluetooth.bp.CallBack bpCall;
    org.ei.telemedicine.bluetooth.bp.BluetoothService bpService;

    org.ei.telemedicine.bluetooth.eet.BluetoothService eetService;
    org.ei.telemedicine.bluetooth.eet.CallBack eetCall;

    org.ei.telemedicine.bluetooth.fetal.BluetoothService fetalService;
    org.ei.telemedicine.bluetooth.fetal.CallBack fetalCall;

    org.ei.telemedicine.bluetooth.blood.BluetoothService bloodService;
    org.ei.telemedicine.bluetooth.blood.CallBack bloodCall;

    ImageButton ib_record, ib_play;
    private String TAG = "BlueToothInfoActivity";
    public static String progrs = null;
    private static int device = 0;
    private static int tempartureFor = 0;
    private static int opacity = 128;
    BluetoothAdapter bluetoothAdapter;
    public static BluetoothSocket btSocket;
    ProgressDialog progressDialog, recordProgressDialog;
    public static String bpSystolic = "bpSystolic";
    public static String bpDiastolic = "bpDiastolic";
    public static String temperature = "temperature";
    public static String fetal_data = "fetalData";
    public static String blood_glucose_data = "bloodGlucoseData";
    public static String pstechoscope_data = "pstechoscopeData";
    public static String anm_poc = "anmPoc";
    static String anmPocInfo = "";
    private static int time = 10000;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;

    @Override
    protected void onStart() {
        super.onStart();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        if (pulseService == null) {
            pulsecall = new CallBack(new PulseBuf(), this);
            pulseService = new BluetoothService(BlueToothInfoActivity.this, pulsecall);
        }
        if (bpService == null) {
            bpCall = new org.ei.telemedicine.bluetooth.bp.CallBack(new BPBuf(), this);
            bpService = new org.ei.telemedicine.bluetooth.bp.BluetoothService(
                    BlueToothInfoActivity.this, bpCall);
        }
        if (eetService == null) {
            eetCall = new org.ei.telemedicine.bluetooth.eet.CallBack(new EETBuf(), this);
            eetService = new org.ei.telemedicine.bluetooth.eet.BluetoothService(
                    BlueToothInfoActivity.this, eetCall);
        }
        if (fetalService == null) {
            fetalCall = new org.ei.telemedicine.bluetooth.fetal.CallBack(new FetalBuf(), this);
            fetalService = new org.ei.telemedicine.bluetooth.fetal.BluetoothService(
                    BlueToothInfoActivity.this, fetalCall);
        }
        if (bloodService == null) {
            bloodCall = new org.ei.telemedicine.bluetooth.blood.CallBack(new BloodBuf(), this);
            bloodService = new org.ei.telemedicine.bluetooth.blood.BluetoothService(
                    BlueToothInfoActivity.this, bloodCall);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onCreation() {
        {
            setContentView(R.layout.bluetooth_info_layout);
            if (getActionBar() != null) {
                getActionBar().setTitle("Vital Information");
            }
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(AllConstants.ENTITY_ID) && extras.containsKey(AllConstants.INSTANCE_ID_PARAM)) {
                entityId = extras.getString(AllConstants.ENTITY_ID, "");
                instanceId = extras.getString(AllConstants.INSTANCE_ID_PARAM, "");
                formName = extras.getString(AllConstants.FORM_NAME_PARAM, "");
                subFormCount = extras.getInt(AllConstants.SUB_FORM_COUNT, 0);

                iv_eet = (ImageView) findViewById(R.id.iv_eet);
                iv_bgm = (ImageView) findViewById(R.id.iv_bgm);
                iv_fetal = (ImageView) findViewById(R.id.iv_fetal);
                iv_bp = (ImageView) findViewById(R.id.iv_bp);
                iv_steh = (ImageView) findViewById(R.id.iv_steh);
                ib_play = (ImageButton) findViewById(R.id.ib_play);
                ib_record = (ImageButton) findViewById(R.id.ib_record);
                tv_fetal = (TextView) findViewById(R.id.tv_fetal);
                ll_fetal_data = (LinearLayout) findViewById(R.id.ll_fetal_data);
                iv_poc = (ImageView) findViewById(R.id.iv_poc);

                et_bp_heart = (EditText) findViewById(R.id.et_bp_heart);
                et_bgm = (EditText) findViewById(R.id.et_bgm);
                et_bp_sys = (EditText) findViewById(R.id.et_bp_sys);
                et_bp_dia = (EditText) findViewById(R.id.et_bp_dia);
                et_fetal = (EditText) findViewById(R.id.et_fetal);
//            et_steh = (EditText) findViewById(R.id.et_steh);
                et_eet = (EditText) findViewById(R.id.et_eet);
                tv_eet_cen = (TextView) findViewById(R.id.tv_eet_cen);
                tv_eet_cen.setText(context.allSettings().fetchANMConfiguration("temperature").startsWith("c") ? "C" : "F");
                bt_save = (org.ei.telemedicine.view.customControls.CustomFontTextView) findViewById(R.id.bt_info_save);


                ll_fetal = (LinearLayout) findViewById(R.id.ll_fetal);
                ll_bgm = (LinearLayout) findViewById(R.id.ll_bgm);
                ll_bp = (LinearLayout) findViewById(R.id.ll_bp);
                ll_eet = (LinearLayout) findViewById(R.id.ll_eet);
                ll_steh = (LinearLayout) findViewById(R.id.ll_steh);
                ll_poc = (LinearLayout) findViewById(R.id.ll_poc);
                ll_eet_child = (LinearLayout) findViewById(R.id.ll_eet_child);
                ll_eet_child_data = (LinearLayout) findViewById(R.id.ll_eet_child_data);

                if (formName.equalsIgnoreCase(AllConstants.FormNames.PNC_VISIT)) {
                    ll_fetal.setVisibility(INVISIBLE);
                    ll_eet_child.setVisibility(VISIBLE);
                    if (subFormCount == 0) {
                        ll_eet_child.setVisibility(INVISIBLE);
                    }
                    for (int i = 1; i <= subFormCount; i++) {
                        final int value = i;
                        LinearLayout ll_eet_child_dat = new LinearLayout(this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.gravity = Gravity.CENTER;
                        ll_eet_child_dat.setWeightSum(4);
                        ll_eet_child_dat.setGravity(Gravity.CENTER);
                        ll_eet_child_dat.setLayoutParams(params);

                        TextView textView = new TextView(this);
                        textView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.5f));
                        textView.setText("Child-" + i + " Temp");
                        textView.setTextSize(20);

                        final EditText editText = new EditText(this);
                        editText.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.5f));
                        editText.setTag("Child temperature");
                        editText.setId(value);

                        TextView textView2 = new TextView(this);
                        textView2.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 0.5f));
                        textView2.setText((context.allSettings().fetchANMConfiguration("temperature").startsWith("c") ? "C" : "F"));
                        textView2.setTextSize(20);

                        ImageButton imageButton = new ImageButton(this);
                        imageButton.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 0.5F));
                        imageButton.setTag("ib_eet-" + value);
                        imageButton.setId(value);
                        imageButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tempartureFor = value;
                                device = Constants.EET_DEVICE_NUM;
                                iv_eet.setImageDrawable(getResources().getDrawable(R.drawable.eet_enable));
                                startDiscovery();
                            }
                        });

                        ll_eet_child_dat.addView(textView);
                        ll_eet_child_dat.addView(editText);
                        ll_eet_child_dat.addView(textView2);
                        ll_eet_child_dat.addView(imageButton);

                        ll_eet_child.addView(ll_eet_child_dat);
                    }
                } else if (formName.equalsIgnoreCase(AllConstants.FormNames.CHILD_ILLNESS)) {
                    ll_fetal.setVisibility(INVISIBLE);
                    ll_bgm.setVisibility(INVISIBLE);
                    ll_bp.setVisibility(INVISIBLE);
                    ll_steh.setVisibility(INVISIBLE);
                    ll_poc.setVisibility(INVISIBLE);
                }

                iv_eet.setOnClickListener(this);
                iv_fetal.setOnClickListener(this);
                iv_steh.setOnClickListener(this);
                iv_bgm.setOnClickListener(this);
                iv_bp.setOnClickListener(this);
                bt_save.setOnClickListener(this);
                ib_play.setOnClickListener(this);
                ib_record.setOnClickListener(this);
                iv_poc.setOnClickListener(this);
                File file = new File(getFilePath());
                if (file.exists()) {
                    ib_play.setVisibility(VISIBLE);
                }

                progressDialog = new ProgressDialog(BlueToothInfoActivity.this);
                progressDialog.setTitle("Device Connecting");
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                if (bluetoothAdapter != null) {
                    IntentFilter intent = new IntentFilter();
                    intent.addAction(BluetoothDevice.ACTION_FOUND);
                    intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
                    intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
                    intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                    registerReceiver(searchDevices, intent);
                }
            }

        }
    }

    @Override
    protected void onResumption() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DRUGS_INFO_RESULT_CODE) {
            Log.e("drugs", data.getExtras().getString(DRUGS));
            anmPocInfo = data.getExtras().getString(DRUGS);
        }
    }


    @Override
    public void onBackPressed() {
        if (bluetoothAdapter != null)
            bluetoothAdapter.cancelDiscovery();
        if (formName.equalsIgnoreCase(AllConstants.FormNames.ANC_VISIT) || formName.equalsIgnoreCase(AllConstants.FormNames.ANC_VISIT_EDIT)) {
            this.finish();
            startFormActivity(AllConstants.FormNames.ANC_VISIT_EDIT, entityId, null);
        }
    }

    private BroadcastReceiver searchDevices = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            Object[] lstName = b.keySet().toArray();

            for (int i = 0; i < lstName.length; i++) {
                String keyName = lstName[i].toString();
                Log.e(TAG, String.valueOf(b.get(keyName)));
            }

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                if (device.getName() != null
                        && device.getName().contains(Constants.PULSE_DEVICE)) {

                    if (pulseService != null)
                        pulseService.stop();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pulseService.start();
                    pulseService.connect(device, context,
                            Constants.PULSE_DEVICE_NUM);
                } else if (device.getName() != null
                        && device.getName().startsWith(Constants.BP_DEVICE)) {
                    Log.e(TAG, "Connect" + "BP Device");

                    if (bpService != null)
                        bpService.stop();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bpService.start();
                    bpService.connect(device, BlueToothInfoActivity.this,
                            Constants.BP_DEVICE_NUM);
                } else if (device.getName() != null
                        && device.getName().startsWith(Constants.EET_DEVICE)) {
                    Log.e(TAG, "Connect" + "EET Device");

                    if (eetService != null)
                        eetService.stop();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    eetService.start();
                    eetService.connect(device, BlueToothInfoActivity.this,
                            Constants.EET_DEVICE_NUM);
                } else if (device.getName() != null
                        && device.getName().startsWith(Constants.FET_DEVICE)) {
                    Log.e(TAG, "Connect" + "FET Device");

                    if (fetalService != null)
                        fetalService.stop();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fetalService.start();
                    fetalService.connect(device, BlueToothInfoActivity.this,
                            Constants.FET_DEVICE_NUM);
                } else if (device.getName() != null
                        && device.getName().startsWith(Constants.BLOOD_DEVICE)) {
                    Log.e(TAG, "Connect" + "Blood Device");

                    if (bloodService != null)
                        bloodService.stop();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bloodService.start();
                    bloodService.connect(device, BlueToothInfoActivity.this,
                            Constants.BLOOD_DEVICE_NUM);
                }
            } else {
//                Toast.makeText(BlueToothInfoActivity.this, "No Device", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void startDiscovery() {
        progressDialog.show();
//        progressDialog.setCancelable(false);
        bluetoothAdapter.startDiscovery();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bp:
                device = Constants.BP_DEVICE_NUM;
                iv_bp.setImageDrawable(getResources().getDrawable(R.drawable.bp_enable));
                startDiscovery();
                break;
            case R.id.iv_bgm:
                device = Constants.BLOOD_DEVICE_NUM;
                iv_bgm.setImageDrawable(getResources().getDrawable(R.drawable.bgm_enable));
                startDiscovery();
                break;
            case R.id.iv_steh:
                device = 0;
                iv_steh.setImageDrawable(getResources().getDrawable(R.drawable.steh_enable));
                startRecord();
                break;
            case R.id.iv_eet:
                tempartureFor = 0;
                device = Constants.EET_DEVICE_NUM;
                iv_eet.setImageDrawable(getResources().getDrawable(R.drawable.eet_enable));
                startDiscovery();
                break;
            case R.id.iv_fetal:
                device = Constants.FET_DEVICE_NUM;
                iv_fetal.setImageDrawable(getResources().getDrawable(R.drawable.fetal_enable));
                startDiscovery();
                break;
            case R.id.iv_poc:
                startActivityForResult(new Intent(this, NativeANMPlanofCareActivity.class), DRUGS_INFO_RESULT_CODE);
                break;
            case R.id.bt_info_save:
                if (et_bp_dia.getText().toString().equals("") && et_bp_sys.getText().toString().equals("") && et_eet.getText().toString().equals("") && et_fetal.getText().toString().equals("") && et_bgm.getText().toString().equals("")) {
                    new AlertDialog.Builder(this).setTitle("Do you want save with out vital reading?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveData();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } else {
                    saveData();
                }

                break;
            case R.id.ib_play:
                startPlaying();
                break;
            default:

                break;
        }
    }

    private void saveData() {
        try {
            if (searchDevices != null)
                unregisterReceiver(searchDevices);
            saveDevicesData(entityId);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("Exception", "UnSupported Encoding Exception");
        }

    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(getFilePath());
//            mPlayer.setDataSource("http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3");
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startRecord() {
        File file = new File(getFilePath());
        if (!file.exists()) {
            new AsyncTaskRunner().execute();
        }
    }

    public String getFilePath() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm
// :ss");
//        Calendar calendar = Calendar.getInstance();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path += "/audiorecordtest.wav";
        return path;
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, String> {

        private String resp;

        @Override
        protected void onPreExecute() {
//            progressDialog.setTitle("Recording Audio");
//            progressDialog.show();
            recordProgressDialog = new ProgressDialog(BlueToothInfoActivity.this);
            recordProgressDialog.setTitle("Recording Audio");
            recordProgressDialog.show();
        }


        @Override
        protected String doInBackground(Void... params) {
            try {
                // Do your long operations here and return the result
                // Sleeping for given time period
                startRecording();
                Thread.sleep(time);
                resp = "Slept for " + time + " milliseconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            recordProgressDialog.dismiss();
            Toast.makeText(BlueToothInfoActivity.this, "Record Completed", Toast.LENGTH_SHORT).show();
            stopRecording();

        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(getFilePath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("Recording", "prepare() failed");
        }
        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    private void saveDevicesData(String entityId) throws JSONException, UnsupportedEncodingException {
//        Toast.makeText(BlueToothInfoActivity.this, "cleic", Toast.LENGTH_SHORT).show();
        org.ei.telemedicine.Context context = org.ei.telemedicine.Context.getInstance();
        Log.e(TAG, "Entity ID----" + entityId);

        if (entityId != null) {
            FormSubmission formSubmission = context.formDataRepository().fetchFromSubmissionUseEntity(entityId);
            Log.e(TAG, "Instance Id " + formSubmission.instanceId());
            Log.e(TAG, "Form Name " + formSubmission.formName());
            JSONObject formData = new JSONObject(formSubmission.instance());
            JSONObject instanceData = formData.getJSONObject("form");
            JSONArray fieldsJsonArray = instanceData.getJSONArray("fields");
            JSONArray instancesArray = instanceData.has("sub_forms") ? instanceData.getJSONArray("sub_forms").getJSONObject(0).getJSONArray("instances") : null;
            if (instancesArray != null && subFormCount == 0) {
                ;
                for (int i = 1; i <= subFormCount; i++) {
                    int value = i;
                    EditText editText = (EditText) findViewById(value);
                    instancesArray.getJSONObject(i - 1).put("childTemperature", editText.getText().toString());
                }
                instanceData.getJSONArray("sub_forms").getJSONObject(0).put("instances", instancesArray);
            }
            for (int i = 0; i < fieldsJsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(fieldsJsonArray.get(i).toString());

                if (jsonObject.has("name")) {
                    if (jsonObject.get("name").equals(bpDiastolic))
                        jsonObject.put("value", et_bp_dia.getText().toString());
                    else if (jsonObject.get("name").equals(bpSystolic))
                        jsonObject.put("value", et_bp_sys.getText().toString());
                    else if (jsonObject.get("name").equals(temperature))
                        jsonObject.put("value", et_eet.getText().toString() + (context.allSettings().fetchANMConfiguration("temperature").startsWith("c") ? "-C" : "-F"));
                    else if (jsonObject.get("name").equals(AllConstants.PNCVisitFields.CHILD_TEMPERATURE))
                        jsonObject.put("value", et_eet.getText().toString() + (context.allSettings().fetchANMConfiguration("childtemperature").startsWith("c") ? "-C" : "-F"));
                    else if (jsonObject.get("name").equals(fetal_data))
                        jsonObject.put("value", et_fetal.getText().toString());
                    else if (jsonObject.get("name").equals(blood_glucose_data))
                        jsonObject.put("value", et_bgm.getText().toString());
                    else if (jsonObject.get("name").equals(pstechoscope_data))
                        jsonObject.put("value", getFilePath());
                    else if (jsonObject.get("name").equals(anm_poc))
                        jsonObject.put("value", anmPocInfo);
                    fieldsJsonArray.put(i, jsonObject);
                }
            }
            Log.e(TAG, "After Putting values ---- " + fieldsJsonArray);
            instanceData.put("fields", fieldsJsonArray);
            formData.put("form", instanceData);
            context.formDataRepository().updateInstance(instanceId, formData.toString());
        }
        Log.e(TAG, "Over");
//        File file = new File(getFilePath());
//        if (file.exists()) {
//            if (file.delete())
//                Toast.makeText(BlueToothInfoActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();
//        }
        Toast.makeText(BlueToothInfoActivity.this, "Record Stored", Toast.LENGTH_SHORT).show();
        String userRole = context.userService().getUserRole();
        DrishtiSyncScheduler.startOnlyIfConnectedToNetwork(getApplicationContext(), userRole);
        this.finish();
    }

    @Override
    public void call() {
        Vector<Integer> _ver = BPBuf.m_buf;
        for (int i = 0; i < _ver.size(); i++) {
            Log.i("........", Integer.toHexString(_ver.get(i) & 0xFF));
        }
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bluetoothAdapter != null & bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.cancelDiscovery();
            bluetoothAdapter.disable();
        }
    }

    @Override
    public void onResult(final byte[] resultData, final int deviceNum) {
        Log.e(TAG, "Coming Interface" + deviceNum);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    bluetoothAdapter.cancelDiscovery();
                }
                if (deviceNum != Constants.NO_DEVICE && resultData != null) {
                    if (deviceNum == Constants.PULSE_DEVICE_NUM && deviceNum == BlueToothInfoActivity.device) {
                        Log.e(TAG, "SPO2= " + resultData[6]
                                + " BPM= " + resultData[7]);
                    }
                    if (deviceNum == Constants.BP_DEVICE_NUM && deviceNum == BlueToothInfoActivity.device) {
                        Log.e(TAG, "Bp Data=" + new String(resultData));
                        for (int i = 0; i < resultData.length; i++) {
                            Log.e(TAG, "Bp Data" + i + resultData[i] + "");
                        }
                        int sys = (resultData[0] << 8 | resultData[1]) & 255;

                        et_bp_sys.setText(sys + "");
                        et_bp_dia.setText(resultData[2] + "");
                        et_bp_heart.setText(resultData[3] + "");
                    }
                    if (deviceNum == Constants.EET_DEVICE_NUM && deviceNum == BlueToothInfoActivity.device) {
                        String result = new String(resultData);
                        Log.e(TAG, "Data EET = " + result);
                        if (tempartureFor == 0)
                            et_eet.setText(result);
                        else {
                            EditText editText = (EditText) findViewById(tempartureFor);
                            editText.setText(result);
                        }
                    }
                    if (deviceNum == Constants.FET_DEVICE_NUM && deviceNum == BlueToothInfoActivity.device) {
                        Log.e(TAG,
                                "Data FET= " + Arrays.toString(resultData));
                        et_fetal.setText(resultData[0] + "");
                    }
                    if (deviceNum == Constants.BLOOD_DEVICE_NUM && deviceNum == BlueToothInfoActivity.device) {
                        String result = new String(resultData);
                        Log.e(TAG, "Data Blood= " + new String(resultData));
                        et_bgm.setText(result + "");
                    }
                } else {
                    Toast.makeText(BlueToothInfoActivity.this, "Device Disconnected",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
