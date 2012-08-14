package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import org.ei.drishti.domain.Alert;
import org.ei.drishti.repository.AllAlerts;
import org.ei.drishti.view.contract.WorkplanAlert;
import org.ei.drishti.view.contract.WorkplanDetailContext;

import java.util.ArrayList;
import java.util.List;

public class WorkplanDetailController {
    private String villageName;
    private AllAlerts allAlerts;

    public WorkplanDetailController(String villageName, AllAlerts allAlerts) {
        this.villageName = villageName;
        this.allAlerts = allAlerts;
    }

    public String get() {
        List<Alert> alerts = allAlerts.fetchAllFor(villageName);
        List<WorkplanAlert> workplanAlerts= new ArrayList<WorkplanAlert>();
        for (Alert alert : alerts) {
            workplanAlerts.add(new WorkplanAlert(alert.beneficiaryName(), alert.dueDate(), alert.visitCode()));
        }
        return new Gson().toJson(new WorkplanDetailContext(villageName, workplanAlerts));
    }
}
