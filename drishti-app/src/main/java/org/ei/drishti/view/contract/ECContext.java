package org.ei.drishti.view.contract;

import java.util.List;

public class ECContext {
    private final String caseId;
    private final String wifeName;
    private final String village;
    private final String subcenter;
    private final String ecNumber;
    private final boolean highPriority;
    private final String address;
    private final String currentMethod;
    private final List<Reminder> alerts;
    private final List<Child> children;
    private final List<TimelineEvent> timelineEvents;

    public ECContext(String caseId, String wifeName, String village, String subcenter, String ecNumber, boolean isHighPriority, String address,
                     String currentMethod, List<Reminder> alerts, List<Child> children, List<TimelineEvent> timelineEvents) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.village = village;
        this.subcenter = subcenter;
        this.ecNumber = ecNumber;
        highPriority = isHighPriority;
        this.address = address;
        this.currentMethod = currentMethod;
        this.alerts = alerts;
        this.children = children;
        this.timelineEvents = timelineEvents;
    }
}
