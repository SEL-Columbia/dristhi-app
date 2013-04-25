package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.AllConstants;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.view.activity.*;
import org.ei.drishti.view.contract.HomeContext;

import static org.ei.drishti.AllConstants.*;

public class NavigationController {
    private Context context;
    private ANMService anmService;

    public NavigationController(Context context, ANMService anmService) {
        this.context = context;
        this.anmService = anmService;
    }

    public void startECList() {
        context.startActivity(new Intent(context, EligibleCoupleListActivity.class));
    }

    public void startANCList() {
        context.startActivity(new Intent(context, ANCListActivity.class));
    }

    public void startPNCList() {
        context.startActivity(new Intent(context, PNCListActivity.class));
    }

    public void startChildList() {
        context.startActivity(new Intent(context, ChildListActivity.class));
    }

    public void startWorkplan() {
        context.startActivity(new Intent(context, WorkplanActivity.class));
    }

    public void startReports() {
        context.startActivity(new Intent(context, ReportsActivity.class));
    }

    public void startFPSmartRegistry() {
        context.startActivity(new Intent(context, FPSmartRegistryActivity.class));
    }

    public void startFormActivity(String formName, String entityId) {
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra(FORM_NAME_PARAM, formName);
        intent.putExtra(ENTITY_ID_PARAM, entityId);
        context.startActivity(intent);
    }

    public String get() {
        return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
    }

    public void takePhoto(String entityId, String entityType) {
        Intent intent = new Intent(context, CameraLaunchActivity.class);
        intent.putExtra(AllConstants.TYPE, entityType);
        intent.putExtra(ENTITY_ID, entityId);
        context.startActivity(intent);
    }
}
