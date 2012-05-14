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
    private String dueDate;

    public Alert(String caseID, String beneficiaryName, String village, String visitCode, String thaayiCardNumber, int priority, String dueDate) {
        this.caseID = caseID;
        this.beneficiaryName = beneficiaryName;
        this.village = village;
        this.visitCode = visitCode;
        this.thaayiCardNumber = thaayiCardNumber;
        this.priority = priority;
        this.dueDate = dueDate;
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

    public String dueDate() {
        return dueDate;
    }

    public String location() {
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
