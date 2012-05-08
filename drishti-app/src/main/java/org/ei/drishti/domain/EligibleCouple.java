package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EligibleCouple {
    private String caseId;
    private String wifeName;
    private String husbandName;
    private String ecNumber;

    public EligibleCouple(String caseId, String wifeName, String husbandName, String ecNumber) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.husbandName = husbandName;
        this.ecNumber = ecNumber;
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

    public String wifeName() {
        return wifeName;
    }

    public String husbandName() {
        return husbandName;
    }

    public String ecNumber() {
        return ecNumber;
    }
}
