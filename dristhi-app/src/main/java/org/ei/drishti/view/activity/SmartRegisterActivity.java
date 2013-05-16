package org.ei.drishti.view.activity;

import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.AllConstants;
import org.ei.drishti.Context;
import org.ei.drishti.view.controller.FormController;

import java.util.Map;

import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.util.Log.logInfo;

public abstract class SmartRegisterActivity extends SecuredWebActivity {
    private String metaData;

    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new FormController(this), "formContext");
        onSmartRegisterInitialization();
    }

    protected abstract void onSmartRegisterInitialization();

    public void startFormActivity(String formName, String entityId, String metaData) {
        this.metaData = metaData;

        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        startActivityForResult(intent, AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE) {
            return;
        }
        logInfo("Form successfully saved. MetaData: " + metaData);
        Map<String, String> metaDataMap = new Gson().fromJson(metaData, new TypeToken<Map<String, String>>() {
        }.getType());
        if (metaDataMap.containsKey(ENTITY_ID) && metaDataMap.containsKey(ALERT_NAME_PARAM)) {
            Context.getInstance().alertService().changeAlertPriorityToInProcess(metaDataMap.get(ENTITY_ID), metaDataMap.get(ALERT_NAME_PARAM));
        }
    }
}
