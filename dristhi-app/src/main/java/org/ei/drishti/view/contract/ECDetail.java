package org.ei.drishti.view.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ECDetail {
    private String caseId;
    private String wifeName;
    private String village;
    private String subcenter;
    private String ecNumber;
    private boolean highPriority;
    private String address;
    private List<ProfileTodo> todos;
    private List<ProfileTodo> urgentTodos;
    private List<Child> children;
    private List<TimelineEvent> timelineEvents;
    private Map<String, String> details;

    public ECDetail(String caseId, String wifeName, String village, String subcenter, String ecNumber, boolean isHighPriority, String address,
                    List<Child> children, Map<String, String> details) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.village = village;
        this.subcenter = subcenter;
        this.ecNumber = ecNumber;
        highPriority = isHighPriority;
        this.address = address;
        this.children = children;
        this.details = details;

        this.todos = new ArrayList<ProfileTodo>();
        this.urgentTodos = new ArrayList<ProfileTodo>();
        this.timelineEvents = new ArrayList<TimelineEvent>();
    }

    public ECDetail addTodos(List<ProfileTodo> todos) {
        this.todos = todos;
        return this;
    }

    public ECDetail addUrgentTodos(List<ProfileTodo> urgentTodos) {
        this.urgentTodos = urgentTodos;
        return this;
    }

    public ECDetail addTimelineEvents(List<TimelineEvent> events) {
        this.timelineEvents = events;
        return this;
    }
}
