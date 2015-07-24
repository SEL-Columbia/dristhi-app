package org.ei.telemedicine.view.dialog;

import android.content.Intent;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;

import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.view.activity.ViewPlanOfCareActivity;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.FormController;

public class OpenFormOption implements EditOption {
    private final String name;
    private final String formName;
    private final FormController formController;
    android.content.Context context;

    public OpenFormOption(String name, String formName, FormController formController) {
        this.name = name;
        this.formName = formName;
        this.formController = formController;
    }

    public OpenFormOption(String name, String formName, FormController formController, android.content.Context context) {
        this.context = context;
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
        if (formName.equals(AllConstants.VIEW_PLAN_OF_CARE)) {
            Intent intent = new Intent(context, ViewPlanOfCareActivity.class);
            intent.putExtra(AllConstants.ENTITY_ID, client.entityId());
            intent.putExtra(AllConstants.VISIT_TYPE, "ANC");
            context.startActivity(intent);
        } else if (formName.equals(AllConstants.VIEW_PNC_PLAN_OF_CARE)) {
            Intent intent = new Intent(context, ViewPlanOfCareActivity.class);
            intent.putExtra(AllConstants.ENTITY_ID, client.entityId());
            intent.putExtra(AllConstants.VISIT_TYPE, "PNC");
            context.startActivity(intent);
        } else if (formName.equals(AllConstants.VIEW_CHILD_PLAN_OF_CARE)) {
            Intent intent = new Intent(context, ViewPlanOfCareActivity.class);
            intent.putExtra(AllConstants.ENTITY_ID, client.entityId());
            intent.putExtra(AllConstants.VISIT_TYPE, "Child");
            context.startActivity(intent);
        } else
            formController.startFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getLocationJSON()).getJSONString());
    }
}
