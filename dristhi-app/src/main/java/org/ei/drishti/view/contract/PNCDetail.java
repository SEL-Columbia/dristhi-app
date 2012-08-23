package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class PNCDetail {
    private final String caseId;
    private final String thaayiCardNumber;
    private final String womanName;
    private final LocationDetails location;
    private final PregnancyOutcomeDetails pncDetails;

    private ArrayList<ProfileTodo> alerts;
    private ArrayList<ProfileTodo> todos;
    private List<TimelineEvent> timelineEvents;

    public PNCDetail(String caseId, String thaayiCardNumber, String womanName, LocationDetails location, PregnancyOutcomeDetails pncDetails) {
        this.caseId = caseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.womanName = womanName;
        this.location = location;
        this.pncDetails = pncDetails;

        this.alerts = new ArrayList<ProfileTodo>();
        this.todos = new ArrayList<ProfileTodo>();
        this.timelineEvents = new ArrayList<TimelineEvent>();
    }

    public PNCDetail addTimelineEvents(List<TimelineEvent> events) {
        this.timelineEvents.addAll(events);
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
