package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.contract.WorkplanContext;
import org.ei.drishti.view.contract.WorkplanVillageSummary;

import java.util.Arrays;

public class WorkplanController {
    private WorkplanContext workplanContext;
    private AllAlerts allAlerts;

    public WorkplanController(AllAlerts allAlerts) {
        this.allAlerts = allAlerts;
    }

    public String get() {
        return new Gson().toJson(new WorkplanContext(4, 2, Arrays.asList(new WorkplanVillageSummary("Harshit", 3, 0), new WorkplanVillageSummary("FooBar", 2, 2))));
    }
}
