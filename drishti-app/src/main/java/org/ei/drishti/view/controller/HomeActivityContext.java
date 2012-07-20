package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import org.ei.drishti.view.activity.EligibleCoupleActivity;

public class HomeActivityContext {
    private Context context;

    public HomeActivityContext(Context context) {
        this.context = context;
    }

    public void startECList() {
        context.startActivity(new Intent(context, EligibleCoupleActivity.class));
    }
}
