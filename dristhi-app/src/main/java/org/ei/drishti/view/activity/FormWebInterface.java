package org.ei.drishti.view.activity;

import android.app.Activity;

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
        activity.finish();
    }
}
