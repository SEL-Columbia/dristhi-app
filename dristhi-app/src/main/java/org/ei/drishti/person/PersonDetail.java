package org.ei.drishti.person;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.view.contract.TimelineEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2/13/15.
 */
public class PersonDetail {
    private String caseId;
    private Map<String, String> details;
    private List<TimelineEvent> timelineEvents;

    public PersonDetail(String caseId, Map<String, String> details) {
        this.caseId = caseId;
        this.details = details;
        this.timelineEvents = new ArrayList<TimelineEvent>();
    }

    public PersonDetail addTimelineEvents(List<TimelineEvent> events) {
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
