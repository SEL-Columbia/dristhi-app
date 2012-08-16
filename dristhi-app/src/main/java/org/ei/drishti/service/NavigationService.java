package org.ei.drishti.service;

import android.app.Activity;
import android.content.Intent;
import org.ei.drishti.view.activity.HomeActivity;

public class NavigationService {
    public void goHome(Activity activity) {
        activity.startActivity(new Intent(activity, HomeActivity.class));
        activity.finish();
    }
}
