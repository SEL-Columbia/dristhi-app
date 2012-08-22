package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Alert {
    private String caseID;
    private String beneficiaryName;
    private String village;
    private String visitCode;
    private String thaayiCardNumber;
    private int priority;
    private String startDate;
    private String expiryDate;

    public Alert(String caseID, String beneficiaryName, String village, String visitCode, String thaayiCardNumber, int priority, String startDate, String expiryDate) {
        this.caseID = caseID;
        this.beneficiaryName = beneficiaryName;
        this.village = village;
        this.visitCode = visitCode;
        this.thaayiCardNumber = thaayiCardNumber;
        this.priority = priority;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public int priority() {
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
