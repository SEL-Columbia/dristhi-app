package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

public class EligibleCouple {
    private String caseId;
    private String wifeName;
    private String husbandName;
    private String ecNumber;
    private final String village;
    private final String subcenter;
    private Map<String, String> details;
    private Boolean isOutOfArea;

    public EligibleCouple(String caseId, String wifeName, String husbandName, String ecNumber, String village, String subcenter, Map<String, String> details) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.husbandName = husbandName;
        this.ecNumber = ecNumber;
        this.village = village;
        this.subcenter = subcenter;
        this.details = details;
        isOutOfArea = false;
    }

    public EligibleCouple asOutOfArea() {
        this.isOutOfArea = true;
        return this;
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

    public String caseId() {
        return caseId;
    }

    public String village() {
        return village;
    }

    public String subCenter() {
        return subcenter;
    }

    public Boolean isOutOfArea() {
        return isOutOfArea;
    }

    public Map<String, String> details() {
        return details;
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
