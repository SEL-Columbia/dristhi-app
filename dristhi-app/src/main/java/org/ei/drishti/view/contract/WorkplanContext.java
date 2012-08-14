package org.ei.drishti.view.contract;

import java.util.List;

public class WorkplanContext {
    private int totalAlertCount;
    private int totalReminderCount;
    private List<WorkplanVillageSummary> villages;

    public WorkplanContext(int totalAlertCount, int totalReminderCount, List<WorkplanVillageSummary> villages) {
        this.totalAlertCount = totalAlertCount;
        this.totalReminderCount = totalReminderCount;
        this.villages = villages;
    }
}
