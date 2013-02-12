package org.ei.drishti.service;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import org.ei.drishti.view.activity.HomeActivity;

import static android.widget.Toast.LENGTH_SHORT;
import static org.ei.drishti.AllConstants.CC_KEY_EXCHANGE_API_REQUEST_ACTION;
import static org.ei.drishti.AllConstants.CC_KEY_EXCHANGE_API_REQUEST_CODE;
import static org.ei.drishti.util.Log.logError;
import static org.ei.drishti.util.Log.logInfo;

public class NavigationService {
    private static final String WORKFLOW_INTENT_ACTION = "org.commcare.dalvik.action.CommCareSession";
    private static final String CC_FORM_ACTIVITY = "org.commcare.dalvik/.activities.CommCareHomeActivity";
    private static final String CCODK_SESSION_REQUEST = "ccodk_session_request";

    public void goHome(Activity activity) {
        activity.startActivity(new Intent(activity, HomeActivity.class));
        activity.finish();
    }

    public void requestKeyAccessFromCommCare(Activity activity) {
        try {
            logInfo("Requesting Key access from CC!");
            Intent intent = new Intent(CC_KEY_EXCHANGE_API_REQUEST_ACTION);
            activity.startActivityForResult(intent, CC_KEY_EXCHANGE_API_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            logError("CC ODK version mismatch. Cannot request for keys! " + e);
        }
    }

    public void openCommCareForm(Context context, String formInformation) {
        Intent intent = new Intent(WORKFLOW_INTENT_ACTION);
        intent.setComponent(ComponentName.unflattenFromString(CC_FORM_ACTIVITY));
        intent.putExtra(CCODK_SESSION_REQUEST, formInformation);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
        }
    }
}
