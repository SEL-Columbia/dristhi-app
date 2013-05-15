package org.ei.drishti.view.controller;

import org.ei.drishti.view.activity.SmartRegisterActivity;

public class FormController {
    private SmartRegisterActivity smartRegisterActivity;

    public FormController(SmartRegisterActivity activity) {
        this.smartRegisterActivity = activity;
    }

    public void startFormActivity(String formName, String entityId, String metaData) {
        smartRegisterActivity.startFormActivity(formName, entityId, metaData);
    }
}
