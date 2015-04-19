package org.ei.drishti.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.AllConstants;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.ANMController;
import org.ei.drishti.view.controller.FormController;
import org.ei.drishti.view.controller.NavigationController;

import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.event.Event.ON_LOGOUT;
import static org.ei.drishti.util.Log.logInfo;

public abstract class SecuredActivity extends Activity {
    protected Context context;
    protected Listener<Boolean> logoutListener;
    protected FormController formController;
    protected ANMController anmController;
    protected NavigationController navigationController;
    private String metaData;

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
            case R.id.switchLanguageMenuItem: {
                String newLanguagePreference = context.userService().switchLanguagePreference();
                Toast.makeText(this, "Language preference set to " + newLanguagePreference + ". Please restart the application.", LENGTH_SHORT).show();
            }
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
        }
    }

    private boolean isSuccessfulFormSubmission(int resultCode) {
        return resultCode == AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE;
    }

    private boolean hasMetadata() {
        return this.metaData != null && !this.metaData.equalsIgnoreCase("undefined");
    }
}
