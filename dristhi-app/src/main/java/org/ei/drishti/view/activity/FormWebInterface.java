package org.ei.drishti.view.activity;

public class FormWebInterface {
    private final String model;
    private final String form;

    public FormWebInterface(String model, String form) {
        this.model = model;
        this.form = form;
    }

    public String getModel() {
        return model;
    }

    public String getForm() {
        return form;
    }
}
