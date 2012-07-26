package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.service.ANMService;
import org.ei.drishti.view.activity.EligibleCoupleActivity;
import org.ei.drishti.view.activity.WorkplanActivity;
import org.ei.drishti.view.contract.HomeContext;

public class HomeActivityController {
    private Context context;
    private ANMService anmService;

    public HomeActivityController(Context context, ANMService anmService) {
        this.context = context;
        this.anmService = anmService;
    }

    public void startECList() {
        context.startActivity(new Intent(context, EligibleCoupleActivity.class));
    }

    public String get() {
        return new Gson().toJson(new HomeContext(anmService.fetchDetails()));
    }

    public void startWorkplan() {
        context.startActivity(new Intent(context, WorkplanActivity.class));
    }
}
