package org.ei.drishti.view.contract;

import java.util.List;

public class WorkplanDetailContext {
    private String village;
    private List<WorkplanAlert> alerts;

    public WorkplanDetailContext(String village, List<WorkplanAlert> alerts) {
        this.village = village;
        this.alerts = alerts;
    }
}
