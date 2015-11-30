package org.ei.telemedicine.view.controller;

import org.ei.telemedicine.view.activity.SecuredActivity;

public class FormController {
    private SecuredActivity activity;

    public FormController(SecuredActivity activity) {
        this.activity = activity;
    }

    public void startFormActivity(String formName, String entityId, String metaData) {
        activity.startFormActivity(formName, entityId, metaData);
    }

    public void viewPOCActivity(String visitType, String entityId) {
        activity.viewPOCActivity(visitType, entityId);
    }

    public void viewFormActivity(String formName, String entityId, String metaData, Boolean isFormView) {
        activity.startFormActivity(formName, entityId, metaData, isFormView);
    }

    public void startMicroFormActivity(String formName, String entityId, String metaData) {
        activity.startMicroFormActivity(formName, entityId, metaData);
    }
}
