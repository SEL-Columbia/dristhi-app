package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ANCDetail {
    private String caseId;
    private String thaayiCardNumber;
    private String womanName;

    private final LocationDetails location;
    private final PregnancyDetails pregnancyDetails;
    private final FacilityDetails facilityDetails;

    private ArrayList<ProfileTodo> alerts;
    private ArrayList<ProfileTodo> todos;
    private List<TimelineEvent> timelineEvents;

    public ANCDetail(String caseId, String thaayiCardNumber, String womanName, LocationDetails location, PregnancyDetails pregnancyDetails, FacilityDetails facilityDetails) {
        this.caseId = caseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.womanName = womanName;
        this.location = location;
        this.pregnancyDetails = pregnancyDetails;
        this.facilityDetails = facilityDetails;

        this.alerts = new ArrayList<ProfileTodo>();
        this.todos = new ArrayList<ProfileTodo>();
        this.timelineEvents = new ArrayList<TimelineEvent>();
    }

    public ANCDetail addTimelineEvents(List<TimelineEvent> events) {
        this.timelineEvents.addAll(events);
        return this;
    }

    public ANCDetail addUrgentTodos(List<ProfileTodo> todos) {
        this.alerts.addAll(todos);
        return this;
    }

    public ANCDetail addTodos(List<ProfileTodo> todos) {
        this.todos.addAll(todos);
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

