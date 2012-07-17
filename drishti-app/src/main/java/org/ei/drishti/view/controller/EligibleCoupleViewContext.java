package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.google.gson.Gson;
import org.ei.drishti.view.domain.ECContext;

import static android.widget.Toast.LENGTH_SHORT;

public class EligibleCoupleViewContext {
    private ECContext ecContext;
    private Activity eligibleCoupleViewActivity;

    public EligibleCoupleViewContext(ECContext ecContext, Activity eligibleCoupleViewActivity) {
        this.ecContext = ecContext;
        this.eligibleCoupleViewActivity = eligibleCoupleViewActivity;
    }

    public String get() {
        return new Gson().toJson(ecContext);
    }

    public void startCommCare() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(ComponentName.unflattenFromString("org.commcare.dalvik/.activities.CommCareHomeActivity"));
        intent.addCategory("android.intent.category.Launcher");
        try {
            eligibleCoupleViewActivity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(eligibleCoupleViewActivity.getApplicationContext(), "CommCare ODK is not installed.", LENGTH_SHORT).show();
        }
    }

    public void startContacts() {
        eligibleCoupleViewActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/")));
    }
}
