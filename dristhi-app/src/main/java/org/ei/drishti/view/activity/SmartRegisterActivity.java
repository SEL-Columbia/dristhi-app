package org.ei.drishti.view.activity;

import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.AllConstants;
import org.ei.drishti.Context;
import org.ei.drishti.event.CapturedPhotoInformation;
import org.ei.drishti.event.Listener;

import java.util.Map;

import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.event.Event.ON_PHOTO_CAPTURED;
import static org.ei.drishti.util.Log.logInfo;

public abstract class SmartRegisterActivity extends SecuredWebActivity {
    protected Listener<CapturedPhotoInformation> photoCaptureListener;
    private String metaData;

    @Override
    protected void onInitialization() {
        onSmartRegisterInitialization();

        photoCaptureListener = new Listener<CapturedPhotoInformation>() {
            @Override
            public void onEvent(CapturedPhotoInformation data) {
                if (webView != null) {
                    webView.loadUrl("javascript:pageView.reloadPhoto('" + data.entityId() + "', '" + data.photoPath() + "')");
                }
            }
        };
        ON_PHOTO_CAPTURED.addListener(photoCaptureListener);
    }

    protected abstract void onSmartRegisterInitialization();

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
        launchForm(formName, entityId, metaData, FormActivity.class);
    }

    @Override
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
        Map<String, String> metaDataMap = new Gson().fromJson(this.metaData, new TypeToken<Map<String, String>>() {
        }.getType());
        if (metaDataMap.containsKey(FIELD_OVERRIDES_PARAM)) {
            intent.putExtra(FIELD_OVERRIDES_PARAM, metaDataMap.get(FIELD_OVERRIDES_PARAM));
        }
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
            Context.getInstance().alertService().changeAlertStatusToInProcess(metaDataMap.get(ENTITY_ID), metaDataMap.get(ALERT_NAME_PARAM));
        }
    }

    @Override
    protected void onResumption() {
        webView.loadUrl("javascript:if(window.pageView) {window.pageView.reload();}");
    }
}
