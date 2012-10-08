package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.dto.AlertPriority;

import static org.ei.drishti.domain.AlertStatus.closed;

public class Alert {
    private String caseID;
    private String beneficiaryName;
    private String husbandName;
    private String village;
    private String visitCode;
    private String thaayiCardNumber;
    private AlertPriority priority;
    private String startDate;
    private String expiryDate;
    private String completionDate;
    private AlertStatus status;

    public Alert(String caseID, String beneficiaryName, String husbandName, String village, String visitCode, String thaayiCardNumber, AlertPriority priority, String startDate, String expiryDate, AlertStatus status) {
        this.caseID = caseID;
        this.beneficiaryName = beneficiaryName;
        this.husbandName = husbandName;
        this.village = village;
        this.visitCode = visitCode;
        this.thaayiCardNumber = thaayiCardNumber;
        this.priority = priority;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public Alert withCompletionDate(String completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public AlertPriority priority() {
        return priority;
    }

    public String beneficiaryName() {
        return beneficiaryName;
    }

    public String husbandName() {
        return husbandName;
    }

    public String thaayiCardNo() {
        return thaayiCardNumber;
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

    public String location() {
        return village;
    }

    public String caseId() {
        return caseID;
    }

    public String village() {
        return village;
    }

    public String completionDate() {
        return completionDate;
    }

    public boolean isClosed() {
        return closed.equals(status);
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
