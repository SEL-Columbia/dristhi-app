package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.AllConstants;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.view.activity.*;
import org.ei.drishti.view.contract.HomeContext;

import static org.ei.drishti.AllConstants.FORM_NAME_PARAMETER;

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

    public void startFormActivity(String formName) {
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra(FORM_NAME_PARAMETER, formName);
        context.startActivity(intent);
    }

    public String get() {
        return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
    }
}
