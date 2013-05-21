package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.dto.AlertStatus;

import static org.ei.drishti.dto.AlertStatus.complete;

public class Alert {
    private String caseID;
    private String visitCode;
    private AlertStatus status;
    private String startDate;
    private String expiryDate;
    private String completionDate;

    public Alert(String caseID, String visitCode, AlertStatus status, String startDate, String expiryDate) {
        this.caseID = caseID;
        this.visitCode = visitCode;
        this.status = status;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public Alert withCompletionDate(String completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public AlertStatus status() {
        return status;
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
        return complete.equals(status);
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
