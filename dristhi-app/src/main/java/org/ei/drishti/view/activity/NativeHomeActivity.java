package org.ei.drishti.view.activity;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.ei.drishti.R;
import org.ei.drishti.event.Listener;
import org.ei.drishti.service.PendingFormSubmissionService;
import org.ei.drishti.sync.SyncAfterFetchListener;
import org.ei.drishti.sync.SyncProgressIndicator;
import org.ei.drishti.sync.UpdateActionsTask;

import static org.ei.drishti.event.Event.*;

public class NativeHomeActivity extends SecuredActivity {
    public static final String TAG = "NativeHomeActivity";

    private MenuItem updateMenuItem;
    private MenuItem remainingFormsToSyncMenuItem;
    private PendingFormSubmissionService pendingFormSubmissionService;

    private Listener<Boolean> onSyncStartListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            Log.d(TAG, "onSyncStartListener::onEvent:" + data);
            if (updateMenuItem != null) {
            updateMenuItem.setActionView(R.layout.progress);
            }
        }
    };

    private Listener<Boolean> onSyncCompleteListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            Log.d(TAG, "onSyncCompleteListener::onEvent:" + data);
            //#TODO: RemainingFormsToSyncCount cannot be updated from a back ground thread!!
            updateRemainingFormsToSyncCount();
            if (updateMenuItem != null) {
            updateMenuItem.setActionView(null);
            }
        }
    };

    private Listener<String> onFormSubmittedListener = new Listener<String>() {
        @Override
        public void onEvent(String instanceId) {
            //updateController.updateANMDetails();
        }
    };

    private Listener<String> updateANMDetailsListener = new Listener<String>() {
        @Override
        public void onEvent(String data) {
            //updateController.updateANMDetails();
        }
    };

    private void initialize() {
        pendingFormSubmissionService = context.pendingFormSubmissionService();
        SYNC_STARTED.addListener(onSyncStartListener);
        SYNC_COMPLETED.addListener(onSyncCompleteListener);
        FORM_SUBMITTED.addListener(onFormSubmittedListener);
        ACTION_HANDLED.addListener(updateANMDetailsListener);
    }

    @Override
    protected void onCreation() {
        Log.d(TAG, "onCreation:");

        setContentView(R.layout.smart_registers_home);

        setupViews();

        initialize();
    }

    private void setupViews() {
        findViewById(R.id.btn_ec_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_pnc_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_anc_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_fp_register).setOnClickListener(onRegisterStartListener);
        findViewById(R.id.btn_child_register).setOnClickListener(onRegisterStartListener);
    }

    @Override
    protected void onResumption() {
        Log.d(TAG, "onResumption:");
        updateSyncIndicator();
        updateRemainingFormsToSyncCount();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: entry");
        super.onPrepareOptionsMenu(menu);
        updateMenuItem = menu.findItem(R.id.updateMenuItem);
        remainingFormsToSyncMenuItem = menu.findItem(R.id.remainingFormsToSyncMenuItem);

        updateSyncIndicator();
        updateRemainingFormsToSyncCount();

        Log.d(TAG, "onPrepareOptionsMenu:" + menu.size());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected:");
        switch (item.getItemId()) {
            case R.id.updateMenuItem:
                updateFromServer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateFromServer() {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(this, context.actionService(), context.formSubmissionSyncService(), new SyncProgressIndicator());
        updateActionsTask.updateFromServer(new SyncAfterFetchListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SYNC_STARTED.removeListener(onSyncStartListener);
        SYNC_COMPLETED.removeListener(onSyncCompleteListener);
        FORM_SUBMITTED.removeListener(onFormSubmittedListener);
        ACTION_HANDLED.removeListener(updateANMDetailsListener);
    }

    private void updateSyncIndicator() {
        if (updateMenuItem != null) {
            if (context.allSharedPreferences().fetchIsSyncInProgress()) {
                updateMenuItem.setActionView(R.layout.progress);
            } else
                updateMenuItem.setActionView(null);
        }
    }

    private void updateRemainingFormsToSyncCount() {
        if (remainingFormsToSyncMenuItem == null) {
            return;
        }

        long size = pendingFormSubmissionService.pendingFormSubmissionCount();
        if (size > 0) {
            remainingFormsToSyncMenuItem.setTitle(String.valueOf(size) + " " + getString(R.string.unsynced_forms_count_message));
            remainingFormsToSyncMenuItem.setVisible(true);
        } else {
            remainingFormsToSyncMenuItem.setVisible(false);
        }
    }

    private View.OnClickListener onRegisterStartListener  = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_ec_register:
                    startRegister(NativeECSmartRegisterActivity.class);
                    break;

                case R.id.btn_anc_register:
                    startRegister(ANCSmartRegisterActivity.class);
                    break;

                case R.id.btn_pnc_register:
                    startRegister(PNCSmartRegisterActivity.class);
                    break;

                case R.id.btn_child_register:
                    startRegister(ChildSmartRegisterActivity.class);
                    break;

                case R.id.btn_fp_register:
                    startRegister(FPSmartRegisterActivity.class);
                    break;
            }
        }

        private <T> void startRegister(Class<T> register) {
            startActivity(new Intent(NativeHomeActivity.this, register));
        }
    };
}
