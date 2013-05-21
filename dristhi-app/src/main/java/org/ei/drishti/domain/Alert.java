package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.dto.AlertPriority;

import static org.ei.drishti.dto.AlertPriority.complete;

public class Alert {
    private String caseID;
    private String visitCode;
    private AlertPriority priority;
    private String startDate;
    private String expiryDate;
    private String completionDate;
    private AlertStatus status;

    public Alert(String caseID, String visitCode, AlertPriority priority, String startDate, String expiryDate, AlertStatus status) {
        this.caseID = caseID;
        this.visitCode = visitCode;
        this.priority = priority;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.status = null;
    }

    public Alert withCompletionDate(String completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public AlertPriority priority() {
        return priority;
    }

    public String visitCode() {
        return visitCode;
    }

    public String startDate() {
        return startDate;
    }

    public String expiryDate() {
        return expiryDate;
    }

    public String caseId() {
        return caseID;
    }

    public String completionDate() {
        return completionDate;
    }

    public boolean isClosed() {
        return complete.equals(priority);
    }

    public AlertStatus status() {
        return status;
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
