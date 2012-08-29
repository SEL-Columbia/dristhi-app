package org.ei.drishti.view.contract;

import java.util.List;
import java.util.Map;

public class ECDetail {
    private final String caseId;
    private final String wifeName;
    private final String village;
    private final String subcenter;
    private final String ecNumber;
    private final boolean highPriority;
    private final String address;
    private final List<ProfileTodo> alerts;
    private final List<Child> children;
    private final List<TimelineEvent> timelineEvents;
    private Map<String, String> details;

    public ECDetail(String caseId, String wifeName, String village, String subcenter, String ecNumber, boolean isHighPriority, String address,
                    List<ProfileTodo> alerts, List<Child> children, List<TimelineEvent> timelineEvents, Map<String, String> details) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.village = village;
        this.subcenter = subcenter;
        this.ecNumber = ecNumber;
        highPriority = isHighPriority;
        this.address = address;
        this.alerts = alerts;
        this.children = children;
        this.timelineEvents = timelineEvents;
        this.details = details;
    }
}
