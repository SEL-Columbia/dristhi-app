package org.ei.telemedicine.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.bluetooth.BlueToothInfoActivity;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.event.Listener;
import org.ei.telemedicine.sync.DrishtiSyncScheduler;
import org.ei.telemedicine.view.controller.ANMController;
import org.ei.telemedicine.view.controller.FormController;
import org.ei.telemedicine.view.controller.NavigationController;

import java.util.Map;

import static org.ei.telemedicine.AllConstants.ALERT_NAME_PARAM;
import static org.ei.telemedicine.AllConstants.ENTITY_ID;
import static org.ei.telemedicine.AllConstants.ENTITY_ID_PARAM;
import static org.ei.telemedicine.AllConstants.FIELD_OVERRIDES_PARAM;
import static org.ei.telemedicine.AllConstants.FORM_NAME_PARAM;
import static org.ei.telemedicine.AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE;
import static org.ei.telemedicine.AllConstants.VIEW_FORM;
import static org.ei.telemedicine.R.string.no_button_label;
import static org.ei.telemedicine.R.string.yes_button_label;
import static org.ei.telemedicine.event.Event.ON_LOGOUT;
import static org.ei.telemedicine.util.Log.logError;
import static org.ei.telemedicine.util.Log.logInfo;

public abstract class SecuredActivity extends Activity {
    protected Context context;
    protected Listener<Boolean> logoutListener;
    protected FormController formController;
    protected ANMController anmController;
    protected NavigationController navigationController;
    private String metaData;
    private String TAG = "SecuredActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = Context.getInstance().updateApplicationContext(this.getApplicationContext());

        logoutListener = new Listener<Boolean>() {
            public void onEvent(Boolean data) {
                finish();
            }
        };
        ON_LOGOUT.addListener(logoutListener);

        if (context.IsUserLoggedOut()) {
            startActivity(new Intent(this, LoginActivity.class));
            context.userService().logoutSession();
            return;
        }
        formController = new FormController(this);
        anmController = context.anmController();
        navigationController = new NavigationController(this, anmController);
        onCreation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (context.IsUserLoggedOut()) {
            context.userService().logoutSession();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        onResumption();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.switchLanguageMenuItem: {
//                String newLanguagePreference = context.userService().switchLanguagePreference();
//                Toast.makeText(this, "Language preference set to " + newLanguagePreference + ". Please restart the application.", LENGTH_SHORT).show();
//            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logoutUser() {
        context.userService().logout();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected abstract void onCreation();

    protected abstract void onResumption();


    public void startFormActivity(String formName, String entityId, String metaData) {
        launchForm(formName, entityId, metaData, FormActivity.class);
    }

    public void startFormActivity(String formName, String entityId, String metaData, boolean isViewForm) {
        viewForm(formName, entityId, metaData, FormActivity.class, isViewForm);
    }

    private void viewForm(String formName, String entityId, String metaData, Class formActivityClass, boolean isViewForm) {
        this.metaData = metaData;
        Log.e("EntityId", entityId);
        Intent intent = new Intent(this, formActivityClass);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        intent.putExtra(VIEW_FORM, true);
        addFieldOverridesIfExist(intent);
        startActivity(intent);

    }

    public void startMicroFormActivity(String formName, String entityId, String metaData) {
        launchForm(formName, entityId, metaData, MicroFormActivity.class);
    }

    private void launchForm(String formName, String entityId, String metaData, Class formType) {
        this.metaData = metaData;

        Intent intent = new Intent(this, formType);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        addFieldOverridesIfExist(intent);
        startActivityForResult(intent, FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE);
    }

    private void addFieldOverridesIfExist(Intent intent) {
        if (hasMetadata()) {
            Map<String, String> metaDataMap = new Gson().fromJson(
                    this.metaData, new TypeToken<Map<String, String>>() {
                    }.getType());
            if (metaDataMap.containsKey(FIELD_OVERRIDES_PARAM)) {
                intent.putExtra(FIELD_OVERRIDES_PARAM, metaDataMap.get(FIELD_OVERRIDES_PARAM));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (isSuccessfulFormSubmission(resultCode)) {
            logInfo("Form successfully saved. MetaData: " + metaData);
            if (hasMetadata()) {
                Map<String, String> metaDataMap = new Gson().fromJson(metaData, new TypeToken<Map<String, String>>() {
                }.getType());
                if (metaDataMap.containsKey(ENTITY_ID) && metaDataMap.containsKey(ALERT_NAME_PARAM)) {
                    Context.getInstance().alertService().changeAlertStatusToInProcess(metaDataMap.get(ENTITY_ID), metaDataMap.get(ALERT_NAME_PARAM));
                }
            }
            if (context.userService().getFormName().equals(AllConstants.FormNames.ANC_VISIT) || context.userService().getFormName().equals(AllConstants.FormNames.ANC_INVESTIGATIONS) || context.userService().getFormName().equals(AllConstants.FormNames.PNC_VISIT)) {
                DrishtiSyncScheduler.stop(SecuredActivity.this);
                logError("Forms " + context.userService().getFormName());
                Log.e(TAG, "Entity Id" + context.userService().getEntityId());
                FormSubmission formSubmission = context.formDataRepository().fetchFromSubmissionUseEntity(context.userService().getEntityId());
                Log.e(TAG, "Form Data" + formSubmission.instance());
                showDialog(formSubmission.entityId(), formSubmission.instanceId(), context.userService().getFormName());
            }
        }
    }

    private void showDialog(final String entityId, final String instanceId, final String formName) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.bluetooth_info)
                .setTitle(R.string.bluetooth_info_title)
                .setCancelable(false)
                .setPositiveButton(yes_button_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Intent intent = new Intent(SecuredActivity.this, BlueToothInfoActivity.class);
                                intent.putExtra(AllConstants.ENTITY_ID, entityId);
                                intent.putExtra(AllConstants.INSTANCE_ID_PARAM, instanceId);
                                intent.putExtra(AllConstants.FORM_NAME_PARAM, formName);
                                startActivity(intent);

                            }
                        })
                .setNegativeButton(no_button_label,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                String userRole = context.userService().getUserRole();
                                DrishtiSyncScheduler.startOnlyIfConnectedToNetwork(getApplicationContext(), userRole);
                            }
                        }).show();
    }

    private boolean isSuccessfulFormSubmission(int resultCode) {
        return resultCode == AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE;
    }

    private boolean hasMetadata() {
        return this.metaData != null && !this.metaData.equalsIgnoreCase("undefined");
    }


}
