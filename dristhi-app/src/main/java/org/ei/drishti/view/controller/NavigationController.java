package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.apache.commons.lang.NotImplementedException;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.view.activity.ANCListActivity;
import org.ei.drishti.view.activity.EligibleCoupleListActivity;
import org.ei.drishti.view.activity.PNCListActivity;
import org.ei.drishti.view.activity.WorkplanActivity;
import org.ei.drishti.view.contract.HomeContext;

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

    public String get() {
        return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
    }

    public void startWorkplan() {
        context.startActivity(new Intent(context, WorkplanActivity.class));
    }
}
