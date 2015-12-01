package org.ei.telemedicine.view.dialog;

import android.widget.Toast;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.form.FieldOverrides;
import org.ei.telemedicine.view.contract.ECSmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.controller.FormController;

import static org.ei.telemedicine.AllConstants.FormNames.ANC_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_REGISTRATION;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_REG_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.ANC_REG_EDIT_OA;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.CHILD_REG_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_REG_EDIT;
import static org.ei.telemedicine.AllConstants.FormNames.PNC_REG_EDIT_OA;
import static org.ei.telemedicine.AllConstants.VIEW_CHILD_PLAN_OF_CARE;
import static org.ei.telemedicine.AllConstants.VIEW_PLAN_OF_CARE;
import static org.ei.telemedicine.AllConstants.VIEW_PNC_PLAN_OF_CARE;
import static org.ei.telemedicine.view.controller.ECSmartRegisterController.STATUS_TYPE_FIELD;

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
            } else if (formName.equals(ANC_REGISTRATION)) {
                try {
                    ECSmartRegisterClient ecClient = (ECSmartRegisterClient) client;
                    if ((ecClient.status().get(STATUS_TYPE_FIELD)).equalsIgnoreCase("anc")) {
                        Toast.makeText(context, "ANC Registration is already completed", Toast.LENGTH_SHORT).show();
                    } else
                        formController.startFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (formName.equals(CHILD_EDIT)) {
                formController.startFormActivity(CHILD_REG_EDIT, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
            } else if (formName.equals(PNC_EDIT) || formName.equals(ANC_EDIT)) {
                Mother mother = Context.getInstance().allBeneficiaries().findMother(client.entityId());
                if (mother != null) {
                    switch (formName) {
                        case ANC_EDIT:
                            if (mother.getDetail("ancNumber") != null && mother.getDetail("ancNumber").toLowerCase().contains("oa"))
                                formController.startFormActivity(ANC_REG_EDIT_OA, mother.ecCaseId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
                            else
                                formController.startFormActivity(ANC_REG_EDIT, mother.ecCaseId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
                            break;
                        case PNC_EDIT:
                            if (null == mother.getDetail("ancNumber"))
                                formController.startFormActivity(PNC_REG_EDIT_OA, mother.ecCaseId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
                            else
                                formController.startFormActivity(PNC_REG_EDIT, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
                            break;
                    }

                } else
                    formController.startFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
            } else {
                formController.startFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString());
            }
        } else
            formController.viewFormActivity(formName, client.entityId(), new FieldOverrides(Context.getInstance().anmLocationController().getFormInfoJSON()).getJSONString(), isFormView);
    }
}
