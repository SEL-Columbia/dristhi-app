package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.*;

public class PNCDetail {
    private final String caseId;
    private final String thaayiCardNumber;

    private final LocationDetails location;
    private final CoupleDetails coupleDetails;
    private final PregnancyOutcomeDetails pncDetails;

    private List<ProfileTodo> todos;
    private List<ProfileTodo> urgentTodos;
    private List<TimelineEvent> timelineEvents;
    private Map<String, String> details;

    public PNCDetail(String caseId, String thaayiCardNumber, CoupleDetails coupleDetails, LocationDetails location, PregnancyOutcomeDetails pncDetails) {
        this.caseId = caseId;
        this.thaayiCardNumber = thaayiCardNumber;

        this.coupleDetails = coupleDetails;
        this.location = location;
        this.pncDetails = pncDetails;

        this.todos = new ArrayList<ProfileTodo>();
        this.urgentTodos = new ArrayList<ProfileTodo>();
        this.timelineEvents = new ArrayList<TimelineEvent>();
    }

    public PNCDetail addTimelineEvents(List<TimelineEvent> events) {
        this.timelineEvents.addAll(events);
        return this;
    }

    public PNCDetail addUrgentTodos(List<ProfileTodo> todos) {
        this.urgentTodos.addAll(todos);
        return this;
    }

    public PNCDetail addTodos(List<ProfileTodo> todos) {
        this.todos.addAll(todos);
        return this;
    }

    public PNCDetail addExtraDetails(Map<String, String> details) {
        this.details = details;
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
