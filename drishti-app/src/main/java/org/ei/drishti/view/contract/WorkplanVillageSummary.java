package org.ei.drishti.view.contract;

public class WorkplanVillageSummary {
    private String name;
    private int reminderCount;
    private int alertCount;

    public WorkplanVillageSummary(String name, int reminderCount, int alertCount) {
        this.name = name;
        this.reminderCount = reminderCount;
        this.alertCount = alertCount;
    }
}
