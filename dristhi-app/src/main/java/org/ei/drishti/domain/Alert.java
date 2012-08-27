package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.dto.AlertPriority;

import static org.ei.drishti.domain.AlertStatus.closed;

public class Alert {
    private String caseID;
    private String beneficiaryName;
    private String village;
    private String visitCode;
    private String thaayiCardNumber;
    private AlertPriority priority;
    private String startDate;
    private String expiryDate;
    private AlertStatus status;

    public Alert(String caseID, String beneficiaryName, String village, String visitCode, String thaayiCardNumber, AlertPriority priority, String startDate, String expiryDate, AlertStatus status) {
        this.caseID = caseID;
        this.beneficiaryName = beneficiaryName;
        this.village = village;
        this.visitCode = visitCode;
        this.thaayiCardNumber = thaayiCardNumber;
        this.priority = priority;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public AlertPriority priority() {
        return priority;
    }

    public String beneficiaryName() {
        return beneficiaryName;
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

    public boolean isCompleted() {
        return closed.equals(status);
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
