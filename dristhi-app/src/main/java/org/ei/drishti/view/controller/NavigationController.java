package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.AllConstants;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.view.activity.*;
import org.ei.drishti.view.contract.HomeContext;

import static org.ei.drishti.AllConstants.ENTITY_ID;

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

    public void startANCList() {
        activity.startActivity(new Intent(activity, ANCListActivity.class));
    }

    public void startPNCList() {
        activity.startActivity(new Intent(activity, PNCListActivity.class));
    }

    public void startChildList() {
        activity.startActivity(new Intent(activity, ChildListActivity.class));
    }

    public void startReports() {
        activity.startActivity(new Intent(activity, ReportsActivity.class));
    }

    public void startFPSmartRegistry() {
        activity.startActivity(new Intent(activity, FPSmartRegistryActivity.class));
    }

    public void startANCSmartRegistry() {
        activity.startActivity(new Intent(activity, ANCSmartRegistryActivity.class));
    }

    public String get() {
        return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
    }

    public void takePhoto(String entityId, String entityType) {
        Intent intent = new Intent(activity, CameraLaunchActivity.class);
        intent.putExtra(AllConstants.TYPE, entityType);
        intent.putExtra(ENTITY_ID, entityId);
        activity.startActivity(intent);
    }

    public void goBack() {
        activity.finish();
    }
}
