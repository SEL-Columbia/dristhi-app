package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Mother {
    private final String caseId;
    private final String ecCaseId;
    private final String thaayiCardNumber;
    private String referenceDate;
    private boolean isHighRisk;
    private String deliveryPlace;

    public Mother(String caseId, String ecCaseId, String thaayiCardNumber, String referenceDate) {
        this.caseId = caseId;
        this.ecCaseId = ecCaseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.referenceDate = referenceDate;
        this.isHighRisk = false;
    }

    public String caseId() {
        return caseId;
    }

    public String ecCaseId() {
        return ecCaseId;
    }

    public String thaayiCardNumber() {
        return thaayiCardNumber;
    }

    public String referenceDate() {
        return referenceDate;
    }

    public Mother withExtraDetails(boolean isHighRisk, String deliveryPlace) {
        this.isHighRisk = isHighRisk;
        this.deliveryPlace = deliveryPlace;
        return this;
    }

    public boolean isHighRisk() {
        return isHighRisk;
    }

    public String deliveryPlace() {
        return deliveryPlace;
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
