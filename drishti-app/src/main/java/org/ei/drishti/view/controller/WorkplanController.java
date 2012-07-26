package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.domain.VillageAlertSummary;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.contract.WorkplanContext;
import org.ei.drishti.view.contract.WorkplanVillageSummary;

import java.util.ArrayList;
import java.util.List;

public class WorkplanController {
    private AllAlerts allAlerts;

    public WorkplanController(AllAlerts allAlerts) {
        this.allAlerts = allAlerts;
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
}
