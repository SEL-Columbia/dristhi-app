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
    private Boolean isClosed;

    public EligibleCouple(String caseId, String wifeName, String husbandName, String ecNumber, String village, String subcenter, Map<String, String> details) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.husbandName = husbandName;
        this.ecNumber = ecNumber;
        this.village = village;
        this.subcenter = subcenter;
        this.details = details;
        this.isOutOfArea = false;
        this.isClosed = false;
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

    public boolean isOutOfArea() {
        return isOutOfArea;
    }

    public boolean isHighPriority() {
        String isHighPriority = details.get("isHighPriority");
        return "1".equals(isHighPriority) || "yes".equals(isHighPriority);
    }

    public Map<String, String> details() {
        return details;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public EligibleCouple setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
        return this;
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
