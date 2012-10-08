package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TimelineEvent {
    private String type;
    private String title;
    private String[] details;
    private String date;

    public TimelineEvent(String type, String title, String[] details, String date) {
        this.type = type;
        this.title = title;
        this.details = details;
        this.date = date;
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
