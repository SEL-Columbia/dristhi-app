package org.ei.telemedicine.view.dialog;

import android.content.Intent;
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
                Intent intent = new Intent(context, ViewPlanOfCareActivity.class);
                intent.putExtra(ENTITY_ID, client.entityId());
                intent.putExtra(VISIT_TYPE, "ANC");
                context.startActivity(intent);
            } else if (formName.equals(VIEW_PNC_PLAN_OF_CARE)) {
                Intent intent = new Intent(context, ViewPlanOfCareActivity.class);
                intent.putExtra(ENTITY_ID, client.entityId());
                intent.putExtra(VISIT_TYPE, "PNC");
                context.startActivity(intent);
            } else if (formName.equals(VIEW_CHILD_PLAN_OF_CARE)) {
                Intent intent = new Intent(context, ViewPlanOfCareActivity.class);
                intent.putExtra(ENTITY_ID, client.entityId());
                intent.putExtra(VISIT_TYPE, "Child");
                context.startActivity(intent);
            } else if (formName.equals(ANC_VISIT)) {
                try {
                    JSONObject visitJson = new JSONObject();
                    Mother mother = Context.getInstance().allBeneficiaries().findMother(client.entityId());
                    visitJson.put("ancVisitNumber", mother.getDetail("ancVisitNumber") != null ? Integer.parseInt(mother.getDetail("ancVisitNumber")) + 1 : 1);
                    formController.startFormActivity(formName, client.entityId(), new FieldOverrides(visitJson.toString()).getJSONString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                formController.startFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getLocationJSON()).getJSONString());
        } else
            formController.viewFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getLocationJSON()).getJSONString(), isFormView);
    }
}
