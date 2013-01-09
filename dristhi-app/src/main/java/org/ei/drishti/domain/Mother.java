package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class Mother {
    private final String caseId;
    private final String ecCaseId;
    private final String thaayiCardNumber;
    private String referenceDate;
    private Map<String, String> details;
    private boolean isClosed;

    public Mother(String caseId, String ecCaseId, String thaayiCardNumber, String referenceDate) {
        this.caseId = caseId;
        this.ecCaseId = ecCaseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.referenceDate = referenceDate;
        this.details = new HashMap<String, String>();
        this.isClosed = false;
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

    public Mother withDetails(Map<String, String> details) {
        this.details = details;
        return this;
    }

    public boolean isHighRisk() {
        return "yes".equals(details.get("isHighRisk"));
    }

    public Map<String, String> details() {
        return details;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Mother setIsClosed(boolean isClosed) {
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
