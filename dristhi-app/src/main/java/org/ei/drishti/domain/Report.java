package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Report {
    private final String indicator;
    private final String annualTarget;
    private final String monthlySummaries;

    public Report(String indicator, String annualTarget, String monthlySummaries) {
        this.indicator = indicator;
        this.annualTarget = annualTarget;
        this.monthlySummaries = monthlySummaries;
    }

    public String indicator() {
        return indicator;
    }

    public String annualTarget() {
        return annualTarget;
    }

    public String monthlySummaries() {
        return monthlySummaries;
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
