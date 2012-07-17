package org.ei.drishti.view.domain;

import java.util.List;

public class ECContext {
    private final String wifeName;
    private final String village;
    private final String subcenter;
    private final String caseId;
    private final boolean highPriority;
    private final String address;
    private final String currentMethod;
    private final List<ECAlert> alerts;
    private final List<Child> children;
    private final List<ECTimeline> timeline;

    public ECContext(String wifeName, String village, String subcenter, String caseId, boolean isHighPriority, String address,
                     String currentMethod, List<ECAlert> alerts, List<Child> children, List<ECTimeline> ecTimeLines) {
        this.wifeName = wifeName;
        this.village = village;
        this.subcenter = subcenter;
        this.caseId = caseId;
        highPriority = isHighPriority;
        this.address = address;
        this.currentMethod = currentMethod;
        this.alerts = alerts;
        this.children = children;
        this.timeline = ecTimeLines;
    }
}
