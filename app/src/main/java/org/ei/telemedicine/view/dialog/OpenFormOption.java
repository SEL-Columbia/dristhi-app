package org.ei.telemedicine.view.dialog;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;

import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.view.activity.ViewPlanOfCareActivity;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.FormController;
import org.json.JSONException;
import org.json.JSONObject;

import static org.ei.telemedicine.AllConstants.*;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_VISIT;

public class OpenFormOption implements EditOption {
    private final String name;
    private final String formName;
    private final FormController formController;
    android.content.Context context;
    boolean isFormView = false;

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

    public OpenFormOption(String name, String formName, FormController formController, boolean viewForm) {
        this.name = name;
        this.formName = formName;
        this.formController = formController;
        this.isFormView = viewForm;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void doEdit(SmartRegisterClient client) {
        if (!isFormView) {
            if (formName.equals(VIEW_PLAN_OF_CARE)) {
                formController.viewPOCActivity(AllConstants.VisitTypes.ANC_VISIT, client.entityId());
            } else if (formName.equals(VIEW_PNC_PLAN_OF_CARE)) {
                formController.viewPOCActivity(AllConstants.VisitTypes.PNC_VISIT, client.entityId());
            } else if (formName.equals(VIEW_CHILD_PLAN_OF_CARE)) {
                formController.viewPOCActivity(AllConstants.VisitTypes.CHILD_VISIT, client.entityId());
            } else {
                formController.startFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
            }
        } else
            formController.viewFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString(), isFormView);
    }
}
