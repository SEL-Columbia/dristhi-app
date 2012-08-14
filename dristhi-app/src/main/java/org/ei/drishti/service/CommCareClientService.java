package org.ei.drishti.service;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.MessageFormat;

import static android.widget.Toast.LENGTH_SHORT;

public class CommCareClientService {
    public void start(Context context, String formId, String caseId) {
        if (!isValidFormId(formId)) {
            return;
        }

        Intent intent = new Intent("org.commcare.dalvik.action.CommCareSession");
        intent.setComponent(ComponentName.unflattenFromString("org.commcare.dalvik/.activities.CommCareHomeActivity"));
        intent.putExtra("ccodk_session_request", CommCareForms.valueOf(formId).dataFor(caseId));

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
        }
    }

    private boolean isValidFormId(String formId) {
        for (CommCareForms form : CommCareForms.values()) {
            if (form.name().equals(formId)) {
                return true;
            }
        }
        return false;
    }

    enum CommCareForms {
        EC_SERVICES("m0", "m0-f1", true),
        ANC_REGISTER("m1", "m1-f0", false),
        PNC_SERVICES("m1", "m1-f3", true),
        ANC_SERVICES("m1", "m1-f1", true);

        private final String moduleId;
        private final String formId;
        private boolean takesCaseId;

        CommCareForms(String moduleId, String formId, boolean takesCaseId) {
            this.moduleId = moduleId;
            this.formId = formId;
            this.takesCaseId = takesCaseId;
        }

        public String dataFor(String caseId) {
            String caseData = "";
            if (takesCaseId) {
                caseData = MessageFormat.format("CASE_ID case_id {0} ", caseId);
            }
            return MessageFormat.format("COMMAND_ID {0} {1}COMMAND_ID {2}", moduleId, caseData, formId);
        }
    }
}
