package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ECDetail {
    private String caseId;
    private CoupleDetails coupleDetails;
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

    public ECDetail(String caseId, String village, String subcenter, String ecNumber, boolean isHighPriority, String address, List<Child> children, CoupleDetails coupleDetails,
                    Map<String, String> details) {
        this.caseId = caseId;
        this.coupleDetails = coupleDetails;
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

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
