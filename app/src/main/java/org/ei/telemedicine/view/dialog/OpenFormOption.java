package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.FormController;

public class OpenFormOption implements EditOption {
    private final String name;
    private final String formName;
    private final FormController formController;

    public OpenFormOption(String name, String formName, FormController formController) {
        this.name = name;
        this.formName = formName;
        this.formController = formController;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void doEdit(SmartRegisterClient client) {
        formController.startFormActivity(formName, client.entityId(), null);
    }
}
