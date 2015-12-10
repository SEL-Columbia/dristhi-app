package org.ei.opensrp.indonesia.view.controller;

import android.app.Activity;
import android.content.Intent;

import org.ei.opensrp.indonesia.view.activity.NativeKBSmartRegisterActivity;
import org.ei.opensrp.indonesia.view.activity.NativeKIANCSmartRegisterActivity;
import org.ei.opensrp.indonesia.view.activity.NativeKIAnakSmartRegisterActivity;
import org.ei.opensrp.indonesia.view.activity.NativeKIPNCSmartRegisterActivity;
import org.ei.opensrp.indonesia.view.activity.NativeKISmartRegisterActivity;
import org.ei.opensrp.indonesia.view.activity.NativeReportingActivity;
import org.ei.opensrp.view.controller.ANMController;
import org.ei.opensrp.view.controller.NavigationController;

/**
 * Created by Dimas Ciputra on 9/12/15.
 */
public class NavigationControllerINA extends NavigationController {

    private Activity activity;

    public NavigationControllerINA(Activity activity, ANMController anmController) {
        super(activity, anmController);
        this.activity = activity;
    }

    public void startKartuIbuRegistry() {
        activity.startActivity(new Intent(activity, NativeKISmartRegisterActivity.class));
    }

    public void startKartuIbuANCRegistry() {
        activity.startActivity(new Intent(activity, NativeKIANCSmartRegisterActivity.class));
    }

    public void startKartuIbuPNCRegistry() {
        activity.startActivity(new Intent(activity, NativeKIPNCSmartRegisterActivity.class));
    }

    public void startAnakBayiRegistry() {
        activity.startActivity(new Intent(activity, NativeKIAnakSmartRegisterActivity.class));
    }

    public void startKBRegistry() {
        activity.startActivity(new Intent(activity, NativeKBSmartRegisterActivity.class));
    }

    public void startReports() {
        activity.startActivity(new Intent(activity, NativeReportingActivity.class));
    }
}
