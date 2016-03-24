package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.text.WordUtils;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.doctor.NativeGraphActivity;
import org.ei.telemedicine.domain.ANM;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.repository.AllSharedPreferences;
import org.ei.telemedicine.service.PendingFormSubmissionService;
import org.ei.telemedicine.sync.DrishtiSyncScheduler;
import org.ei.telemedicine.sync.SyncAfterFetchListener;
import org.ei.telemedicine.sync.SyncProgressIndicator;
import org.ei.telemedicine.sync.UpdateActionsTask;
import org.ei.telemedicine.view.contract.HomeContext;
import org.ei.telemedicine.view.controller.NativeAfterANMDetailsFetchListener;
import org.ei.telemedicine.view.controller.NativeUpdateANMDetailsTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import static java.lang.String.valueOf;
import static org.ei.telemedicine.event.Event.ACTION_HANDLED;
import static org.ei.telemedicine.event.Event.FORM_SUBMITTED;
import static org.ei.telemedicine.event.Event.NETWORK_AVAILABLE;
import static org.ei.telemedicine.event.Event.SYNC_COMPLETED;
import static org.ei.telemedicine.event.Event.SYNC_STARTED;

//import org.ei.telemedicine.doctor.NativeDoctorSmartRegisterActivity;
//import org.ei.telemedicine.doctor.NativeDoctorFragmentActivity;

public class NativeHomeActivity extends SecuredActivity {
    private MenuItem updateMenuItem;
    private MenuItem networkMenuItem;
    private MenuItem remainingFormsToSyncMenuItem;
    private PendingFormSubmissionService pendingFormSubmissionService;
    Dialog popup_dialog;
    Object obj;

    private Listener<Boolean> onSyncStartListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            if (updateMenuItem != null) {
                updateMenuItem.setActionView(R.layout.progress);
            }
            if (context.allSharedPreferences().getScreen().equals(AllConstants.HOME_SCREEN) && context.allSharedPreferences().fetchIsFirstLogin()) {
                progressDialog = new ProgressDialog(NativeHomeActivity.this);
                progressDialog.setMessage((!context.allSharedPreferences().getVillageName().toString().equals("") ? "\"" + context.allSharedPreferences().getVillageName().toString() + "\"" + " " : "") + "Data is Syncing");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
    };

    private Listener<Boolean> onNetworkChangeListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            if (networkMenuItem != null)
                Log.e("Internet Status", data + "");
            networkMenuItem.setIcon(data ? R.drawable.online : R.drawable.offline);
        }
    };


    private Listener<Boolean> onSyncCompleteListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            //#TODO: RemainingFormsToSyncCount cannot be updated from a back ground thread!!
            updateRemainingFormsToSyncCount();
            if (updateMenuItem != null) {
                updateMenuItem.setActionView(null);
            }
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            updateRegisterCounts();
        }
    };

    private Listener<String> onFormSubmittedListener = new Listener<String>() {
        @Override
        public void onEvent(String instanceId) {
            updateRegisterCounts();
        }
    };

    private Listener<String> updateANMDetailsListener = new Listener<String>() {
        @Override
        public void onEvent(String data) {
            updateRegisterCounts();
        }
    };

    private TextView ecRegisterClientCountView;
    private TextView ancRegisterClientCountView;
    private TextView pncRegisterClientCountView;
    private TextView fpRegisterClientCountView;
    private TextView childRegisterClientCountView;
    private ProgressDialog progressDialog;
    private org.ei.telemedicine.view.customControls.CustomFontTextView tv_anm_name, tv_phc_name, tv_subcenter_name;
    private FrameLayout ec_register, fp_register, anc_register, pnc_register, child_register;
    private String TAG = "NativeHomeActivity";

    @Override
    public void onCreation() {
        setContentView(R.layout.smart_registers_home);
        setupViews();
        initialize();
    }

    private void visibleRegisters() {
        AllSharedPreferences allSharedPreferences = Context.getInstance().allSharedPreferences();
        ec_register.setVisibility(allSharedPreferences.registerState(AllConstants.EC_REGISTERS_KEY) ? View.VISIBLE : View.GONE);
        fp_register.setVisibility(allSharedPreferences.registerState(AllConstants.FP_REGISTERS_KEY) ? View.VISIBLE : View.GONE);
        anc_register.setVisibility(allSharedPreferences.registerState(AllConstants.ANC_REGISTERS_KEY) ? View.VISIBLE : View.GONE);
        pnc_register.setVisibility(allSharedPreferences.registerState(AllConstants.PNC_REGISTERS_KEY) ? View.VISIBLE : View.GONE);
        child_register.setVisibility(allSharedPreferences.registerState(AllConstants.CHILD_REGISTERS_KEY) ? View.VISIBLE : View.GONE);
    }

    private void setupViews() {
        findViewById(R.id.btn_ec_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_pnc_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_anc_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_fp_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_child_register).setOnClickListener(onRegisterStartListener);

        findViewById(R.id.btn_reporting).setOnClickListener(onButtonsClickListener);
//        findViewById(R.id.btn_videos).setOnClickListener(onButtonsClickListener);

        ecRegisterClientCountView = (TextView) findViewById(R.id.txt_ec_register_client_count);
        pncRegisterClientCountView = (TextView) findViewById(R.id.txt_pnc_register_client_count);
        ancRegisterClientCountView = (TextView) findViewById(R.id.txt_anc_register_client_count);
        fpRegisterClientCountView = (TextView) findViewById(R.id.txt_fp_register_client_count);
        childRegisterClientCountView = (TextView) findViewById(R.id.txt_child_register_client_count);

        ec_register = (FrameLayout) findViewById(R.id.frame_ec_reg);
        fp_register = (FrameLayout) findViewById(R.id.frame_fp_reg);
        anc_register = (FrameLayout) findViewById(R.id.frame_anc_reg);
        pnc_register = (FrameLayout) findViewById(R.id.frame_pnc_reg);
        child_register = (FrameLayout) findViewById(R.id.frame_child_reg);

        tv_anm_name = (org.ei.telemedicine.view.customControls.CustomFontTextView) findViewById(R.id.tv_anm_name);
        tv_phc_name = (org.ei.telemedicine.view.customControls.CustomFontTextView) findViewById(R.id.tv_phc_name);
        tv_subcenter_name = (org.ei.telemedicine.view.customControls.CustomFontTextView) findViewById(R.id.tv_sub_center_name);
//        iv_networkStatus = (ImageView) findViewById(R.id.iv_network);
    }

    private void initialize() {
        if (context.allSharedPreferences().fetchIsFirstLogin()) {
            updateRegisterCounts(new HomeContext(new ANM(context.allSharedPreferences().fetchRegisteredANM(), 0, 0, 0, 0, 0)));
        } else {
            updateRegisterCounts();
        }
        pendingFormSubmissionService = context.pendingFormSubmissionService();
        SYNC_STARTED.addListener(onSyncStartListener);
        SYNC_COMPLETED.addListener(onSyncCompleteListener);
        FORM_SUBMITTED.addListener(onFormSubmittedListener);
        ACTION_HANDLED.addListener(updateANMDetailsListener);
//        NETWORK_AVAILABLE.addListener(onNetworkChangeListener);

    }

    @Override
    public void onResumption() {
        Log.e("Resume", "resume");
        context.allSharedPreferences().saveCurrent(AllConstants.HOME_SCREEN);
        visibleRegisters();
//        updateRegisterCounts();
        updateSyncIndicator();
        updateRemainingFormsToSyncCount();
        try {
            JSONObject locationJson = new JSONObject(context.allSettings().fetchANMLocation());
            tv_anm_name.setText(WordUtils.capitalize(context.allSharedPreferences().fetchRegisteredANM()));
            tv_phc_name.setText("PHC: " + (locationJson.has("phcName") ? locationJson.getString("phcName") : ""));
            tv_subcenter_name.setText("Sub Center: " + (locationJson.has("subCenter") ? locationJson.getString("subCenter") : ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        NETWORK_AVAILABLE.addListener(onNetworkChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Pause", "pause");
        context.allSharedPreferences().saveCurrent("");
        NETWORK_AVAILABLE.removeListener(onNetworkChangeListener);
    }


    private void updateRegisterCounts() {
        NativeUpdateANMDetailsTask task = new NativeUpdateANMDetailsTask(Context.getInstance().anmController());
        task.fetch(new NativeAfterANMDetailsFetchListener() {
            @Override
            public void afterFetch(HomeContext anmDetails) {
                updateRegisterCounts(anmDetails);
            }
        });
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        updateRegisterCounts();
////        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
//    }

    private void updateRegisterCounts(HomeContext homeContext) {
        ecRegisterClientCountView.setText(valueOf(homeContext.eligibleCoupleCount()));
        ancRegisterClientCountView.setText(valueOf(homeContext.ancCount()));
        pncRegisterClientCountView.setText(valueOf(homeContext.pncCount()));
        fpRegisterClientCountView.setText(valueOf(homeContext.fpCount()));
        childRegisterClientCountView.setText(valueOf(homeContext.childCount()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if (networkMenuItem == null) {
            networkMenuItem = menu.findItem(R.id.networkMenuItem);
        }
        networkMenuItem.setIcon(DrishtiSyncScheduler.isNetWorkAvailable(this) ? R.drawable.online : R.drawable.offline);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        updateMenuItem = menu.findItem(R.id.updateMenuItem);
        remainingFormsToSyncMenuItem = menu.findItem(R.id.remainingFormsToSyncMenuItem);
        networkMenuItem = menu.findItem(R.id.networkMenuItem);
        updateSyncIndicator();
        updateRemainingFormsToSyncCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateMenuItem:
                updateFromServer();
                return true;

            case R.id.video:
                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(new ComponentName("org.appspot.apprtc",
                            "org.appspot.apprtc.ConnectActivity"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Please install Apprtc APK", Toast.LENGTH_SHORT).show();
                }
                return true;
//            case R.id.export:
//                File sd = Environment.getExternalStorageDirectory();
//                File data = Environment.getDataDirectory();
//                FileChannel source = null;
//                FileChannel destination = null;
//                String SAMPLE_DB_NAME = "drishti.db";
//                String currentDBPath = "/data/" + "org.ei.telemedicine" + "/databases/" + SAMPLE_DB_NAME;
//                String backupDBPath = SAMPLE_DB_NAME;
//                File currentDB = new File(data, currentDBPath);
//                File backupDB = new File(sd, backupDBPath);
//                try {
//                    source = new FileInputStream(currentDB).getChannel();
//                    destination = new FileOutputStream(backupDB).getChannel();
//                    destination.transferFrom(source, 0, source.size());
//                    source.close();
//                    destination.close();
//                    Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(this, "Comple", Toast.LENGTH_SHORT).show();
//                return true;

            case R.id.logout:
                new AlertDialog.Builder(this).setTitle("Do you want logout?").setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!context.allSharedPreferences().getIsSyncInProgress() && pendingFormSubmissionService.pendingFormSubmissionCount() == 0) {
                            logoutUser();
                        } else {
                            Toast.makeText(NativeHomeActivity.this, "Need to sync all information in server", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

                return true;
//            case R.id.overview:
//                startActivity(new Intent(this, NativeANMPlanofCareActivity.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void updateFromServer() {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(
                this, context.actionService(), context.formSubmissionSyncService(), new SyncProgressIndicator());
        final ArrayList<String> villagesList = context.allSettings().getVillages();
//        ArrayList<String> villagesList = new ArrayList<String>();
//        villagesList.add("Gazi Pur");
//        villagesList.add("Kotla");
//        villagesList.add("Dalu Pura");
//        villagesList.add("Hasan Pur");
        for (String villageName : villagesList)
            updateActionsTask.updateFromServer(new SyncAfterFetchListener(), villageName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SYNC_STARTED.removeListener(onSyncStartListener);
        SYNC_COMPLETED.removeListener(onSyncCompleteListener);
        FORM_SUBMITTED.removeListener(onFormSubmittedListener);
        ACTION_HANDLED.removeListener(updateANMDetailsListener);
//        NETWORK_AVAILABLE.removeListener(onNetworkChangeListener);
    }

    private void updateSyncIndicator() {
        if (updateMenuItem != null) {
            if (context.allSharedPreferences().fetchIsSyncInProgress()) {
                updateMenuItem.setActionView(R.layout.progress);
            } else {
                updateMenuItem.setActionView(null);

            }
        }
    }

    private void updateRemainingFormsToSyncCount() {
        if (remainingFormsToSyncMenuItem == null) {
            return;
        }

        long size = pendingFormSubmissionService.pendingFormSubmissionCount();
        if (size > 0) {
            remainingFormsToSyncMenuItem.setTitle(valueOf(size) + " " + getString(R.string.unsynced_forms_count_message));
            remainingFormsToSyncMenuItem.setVisible(true);
        } else {
            remainingFormsToSyncMenuItem.setVisible(false);
        }
    }

    private View.OnClickListener onRegisterStartListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_ec_register:
                    navigationController.startECSmartRegistry();
                    break;

                case R.id.btn_anc_register:
                    navigationController.startANCSmartRegistry();
                    break;

                case R.id.btn_pnc_register:
                    navigationController.startPNCSmartRegistry();
                    break;

                case R.id.btn_child_register:
                    navigationController.startChildSmartRegistry();
                    break;

                case R.id.btn_fp_register:
                    navigationController.startFPSmartRegistry();
                    break;
            }
        }
    };

    private View.OnClickListener onButtonsClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_reporting:
                    navigationController.startReports();
                    break;
//
//                case R.id.btn_videos:
//                    navigationController.startVideos();
//                    break;
            }
        }
    };

}
