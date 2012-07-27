package org.ei.drishti.view.controller;

import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import org.ei.drishti.domain.VillageAlertSummary;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.activity.WorkplanDetailActivity;
import org.ei.drishti.view.contract.WorkplanContext;
import org.ei.drishti.view.contract.WorkplanVillageSummary;

import java.util.ArrayList;
import java.util.List;

public class WorkplanController {
    private AllAlerts allAlerts;
    private Context context;

    public WorkplanController(AllAlerts allAlerts, Context context) {
        this.allAlerts = allAlerts;
        this.context = context;
    }

    public String get() {
        List<VillageAlertSummary> villageAlertSummaries = allAlerts.villageSummary();
        List<WorkplanVillageSummary> workplanVillageSummaries = new ArrayList<WorkplanVillageSummary>();
        int totalAlertCount = 0;
        for (VillageAlertSummary villageAlertSummary : villageAlertSummaries) {
            workplanVillageSummaries.add(new WorkplanVillageSummary(villageAlertSummary.name(), 0, villageAlertSummary.alertCount()));
            totalAlertCount += villageAlertSummary.alertCount();
        }
        return new Gson().toJson(new WorkplanContext(totalAlertCount, 0, workplanVillageSummaries));
    }

    public void startWorkplanDetail(String village) {
        Intent intent = new Intent(context.getApplicationContext(), WorkplanDetailActivity.class);
        intent.putExtra("villageName", village);
        context.startActivity(intent);
    }
}
