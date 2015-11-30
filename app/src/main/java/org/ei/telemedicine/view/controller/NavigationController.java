package org.ei.telemedicine.view.controller;

import android.app.Activity;
import android.content.Intent;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.view.activity.*;

import static org.ei.telemedicine.view.controller.ProfileNavigationController.*;

public class NavigationController {
    private Activity activity;
    private ANMController anmController;

    public NavigationController(Activity activity, ANMController anmController) {
        this.activity = activity;
        this.anmController = anmController;
    }

    public void startReports() {
        activity.startActivity(new Intent(activity, ReportsActivity.class));
    }


    public void startECSmartRegistry() {
        activity.startActivity(new Intent(activity, NativeECSmartRegisterActivity.class));
    }

    public void startFPSmartRegistry() {
        activity.startActivity(new Intent(activity, NativeFPSmartRegisterActivity.class));
    }

    public void startANCSmartRegistry() {
        activity.startActivity(new Intent(activity, NativeANCSmartRegisterActivity.class));
    }

    public void startPNCSmartRegistry() {
        activity.startActivity(new Intent(activity, NativePNCSmartRegisterActivity.class));
    }

    public void startChildSmartRegistry() {
        activity.startActivity(new Intent(activity, NativeChildSmartRegisterActivity.class));
    }

    public String get() {
        return anmController.get();
    }

    public void goBack() {
        activity.finish();
    }

    public void startEC(String entityId) {
        navigateToECProfile(activity, entityId);
//        navigateToProfile(activity, entityId);
    }
    public void startFP(String entityId) {
        navigateToFPProfile(activity, entityId);
//        navigateToProfile(activity, entityId);
    }
    public void startDoctor(String formallData, String formData) {
        navigateToProfile(activity, formallData, formData);
    }

    public void startANC(String entityId) {
//        navigateToProfile(activity, entityId);
        navigateToANCProfile(activity, entityId);
    }

    public void startPNC(String entityId) {
        navigateToPNCProfile(activity, entityId);
    }

    public void startChild(String entityId) {
        navigateToChildProfile(activity, entityId);
    }

}
