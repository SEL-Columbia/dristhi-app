package org.ei.telemedicine.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebSettings;

import org.apache.commons.io.IOUtils;
import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.ei.telemedicine.AllConstants.ENTITY_ID_PARAM;
import static org.ei.telemedicine.AllConstants.FIELD_OVERRIDES_PARAM;
import static org.ei.telemedicine.AllConstants.FORM_NAME_PARAM;
import static org.ei.telemedicine.AllConstants.FORM_SUBMISSION_ROUTER;
import static org.ei.telemedicine.AllConstants.INSTANCE_ID_PARAM;
import static org.ei.telemedicine.AllConstants.REPOSITORY;
import static org.ei.telemedicine.AllConstants.VIEW_FORM;
import static org.ei.telemedicine.AllConstants.ZIGGY_FILE_LOADER;
import static org.ei.telemedicine.R.string.edd_label;
import static org.ei.telemedicine.R.string.form_back_confirm_dialog_message;
import static org.ei.telemedicine.R.string.form_back_confirm_dialog_title;
import static org.ei.telemedicine.R.string.no_button_label;
import static org.ei.telemedicine.R.string.yes_button_label;
import static org.ei.telemedicine.util.Log.logError;

public abstract class SecuredFormActivity extends SecuredWebActivity {
    public static final String ANDROID_CONTEXT_FIELD = "androidContext";
    private String model;
    private String form;
    private String formName;
    private boolean isViewForm;
    private String entityId;
    private String fieldOverrides;
    private String TAG = "SecuredFormActivity";

    public SecuredFormActivity() {
        super();
        shouldDismissProgressBarOnProgressComplete = false;
    }

    @Override
    protected void onInitialization() {
        try {
            getIntentData();
        } catch (IOException e) {
            logError(e.toString());
            finish();
        }
        webViewInitialization();
    }

    private void getIntentData() throws IOException {
        Intent intent = getIntent();
        formName = intent.getStringExtra(FORM_NAME_PARAM);
        entityId = intent.getStringExtra(ENTITY_ID_PARAM);
        fieldOverrides = intent.getStringExtra(FIELD_OVERRIDES_PARAM);
        isViewForm = intent.hasExtra(VIEW_FORM) && intent.getBooleanExtra(VIEW_FORM, false);
        model = IOUtils.toString(getAssets().open(
                "www/form/" + formName + "/model.xml"));
        form = IOUtils.toString(getAssets().open(
                "www/form/" + formName + "/form.xml"));
//        model = IOUtils.toString(new FileInputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/forms/anc_visit/model.xml")), null);
//        form = IOUtils.toString(new FileInputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/forms/anc_visit/form.xml")), null);
        Log.e("Form Details", formName + "--" + entityId);
        context.userService().setFormDetails(formName, entityId);
    }

    private void webViewInitialization() {
        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        webView.addJavascriptInterface(new FormWebInterface(model, form, this),
                ANDROID_CONTEXT_FIELD);
        webView.addJavascriptInterface(Context.getInstance()
                .formDataRepository(), REPOSITORY);
        webView.addJavascriptInterface(Context.getInstance().ziggyFileLoader(),
                ZIGGY_FILE_LOADER);
        webView.addJavascriptInterface(Context.getInstance()
                .formSubmissionRouter(), FORM_SUBMISSION_ROUTER);
        String encodedFieldOverrides = null;
        try {
            if (isNotBlank(this.fieldOverrides)) {
                encodedFieldOverrides = URLEncoder.encode(this.fieldOverrides,
                        "utf-8");
                Log.e(TAG, "Encoded Field Overriedes" + encodedFieldOverrides);
            }
        } catch (Exception e) {
            logError(MessageFormat.format(
                    "Cannot encode field overrides: {0} due to : {1}",
                    fieldOverrides, e));
        }
        String loadingURL;
        Log.e(TAG, "View " + isViewForm + "");
        if (!isViewForm)
            loadingURL = MessageFormat
                    .format("file:///android_asset/www/enketo/template.html?{0}={1}&{2}={3}&{4}={5}&{6}={7}&touch=true",
                            FORM_NAME_PARAM, formName, ENTITY_ID_PARAM, entityId,
                            INSTANCE_ID_PARAM, randomUUID(), FIELD_OVERRIDES_PARAM,
                            encodedFieldOverrides);
        else
            loadingURL = MessageFormat
                    .format("file:///android_asset/www/enketo/view_template.html?{0}={1}&{2}={3}&{4}={5}&{6}={7}&touch=true",
                            FORM_NAME_PARAM, formName, ENTITY_ID_PARAM, entityId,
                            INSTANCE_ID_PARAM, randomUUID(), FIELD_OVERRIDES_PARAM,
                            encodedFieldOverrides);

        Log.e(TAG, "Loading URl " + loadingURL);
        webView.loadUrl(loadingURL);
    }

    @Override
    protected void reportException(String message) {

    }

    @Override
    public void onBackPressed() {
        if (isViewForm)
            this.finish();
//            goBack();
        else
            new AlertDialog.Builder(this)
                    .setMessage(form_back_confirm_dialog_message)
                    .setTitle(form_back_confirm_dialog_title)
                    .setCancelable(false)
                    .setPositiveButton(yes_button_label,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    goBack();
                                }
                            })
                    .setNegativeButton(no_button_label,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                }
                            }).show();
    }

    private void goBack() {
//        super.onBackPressed();
        finish();
    }
}
