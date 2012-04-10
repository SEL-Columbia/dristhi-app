package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

public class AlertAction {
    private String caseID;
    private String alertType;
    private Map<String, String> data;
    private String timeStamp;

    public AlertAction(String caseID, String alertType, Map<String, String> data, String timeStamp) {
        this.caseID = caseID;
        this.alertType = alertType;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public String caseID() {
        return caseID;
    }

    public String type() {
        return alertType;
    }

    public String index() {
        return timeStamp;
    }

    public String get(String key) {
        return data.get(key);
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
