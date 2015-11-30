package org.ei.telemedicine.view.activity;

import android.app.Activity;

import org.ei.telemedicine.util.Log;

import static org.ei.telemedicine.AllConstants.FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE;

public class FormWebInterface {
    private final String model;
    private final String form;
    private Activity activity;

    public FormWebInterface(String model, String form, Activity activity) {
        this.model = model;
        this.form = form;
        this.activity = activity;
    }

    public String getModel() {
        return model;
    }

    public String getForm() {
        return form;
    }

    public void goBack() {
        activity.setResult(FORM_SUCCESSFULLY_SUBMITTED_RESULT_CODE);
        activity.finish();
    }

    public void log(String message) {
        Log.logInfo(message);
    }

    public void onLoadFinished(){
        ((SecuredWebActivity)activity).closeDialog();
    }
}
