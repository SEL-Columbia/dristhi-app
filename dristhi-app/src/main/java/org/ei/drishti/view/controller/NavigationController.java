package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.AllConstants;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.view.activity.*;
import org.ei.drishti.view.contract.HomeContext;

import static org.ei.drishti.AllConstants.ENTITY_ID;
import static org.ei.drishti.view.controller.ProfileNavigationController.*;

public class NavigationController {
    private Activity activity;
    private ANMService anmService;

    public NavigationController(Activity activity, ANMService anmService) {
        this.activity = activity;
        this.anmService = anmService;
    }

    public void startECList() {
        activity.startActivity(new Intent(activity, EligibleCoupleListActivity.class));
    }

    public void startChildList() {
        activity.startActivity(new Intent(activity, ChildListActivity.class));
    }

    public void startReports() {
        activity.startActivity(new Intent(activity, ReportsActivity.class));
    }

    public void startVideos() {
        activity.startActivity(new Intent(activity, VideosActivity.class));
    }

    public void startFPSmartRegistry() {
        activity.startActivity(new Intent(activity, FPSmartRegistryActivity.class));
    }

    public void startANCSmartRegistry() {
        activity.startActivity(new Intent(activity, ANCSmartRegistryActivity.class));
    }

    public void startPNCSmartRegistry() {
        activity.startActivity(new Intent(activity, PNCSmartRegistryActivity.class));
    }

    public void startChildSmartRegistry() {
        activity.startActivity(new Intent(activity, ChildSmartRegistryActivity.class));
    }

    public String get() {
        return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
    }

    public void goBack() {
        activity.finish();
    }

    public void startEC(String entityId) {
        navigateToECProfile(activity, entityId);
    }

    public void startANC(String entityId) {
        navigateToANCProfile(activity, entityId);
    }

    public void startPNC(String entityId) {
        navigateToPNCProfile(activity, entityId);
    }

    public void startChild(String entityId) {
        navigateToChildProfile(activity, entityId);
    }
}
