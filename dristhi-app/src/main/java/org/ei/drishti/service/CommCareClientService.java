package org.ei.drishti.service;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import org.ei.drishti.domain.CommCareForm;

import static android.widget.Toast.LENGTH_SHORT;

public class CommCareClientService {
    public void start(Context context, String formId, String caseId) {
        if (!isValidFormId(formId)) {
            return;
        }

        Intent intent = new Intent("org.commcare.dalvik.action.CommCareSession");
        intent.setComponent(ComponentName.unflattenFromString("org.commcare.dalvik/.activities.CommCareHomeActivity"));
        intent.putExtra("ccodk_session_request", CommCareForm.valueOf(formId).dataFor(caseId));

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
        }
    }

    private boolean isValidFormId(String formId) {
        for (CommCareForm form : CommCareForm.values()) {
            if (form.name().equals(formId)) {
                return true;
            }
        }
        return false;
    }

}
