package org.ei.drishti.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;

public class AlertAction {
    private String caseID;
    private String alertType;
    private Map<String, String> data;

    public AlertAction(String caseID, String alertType, Map<String, String> data) {
        this.caseID = caseID;
        this.alertType = alertType;
        this.data = data;
    }

    public String caseID() {
        return caseID;
    }

    public String type() {
        return alertType;
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
