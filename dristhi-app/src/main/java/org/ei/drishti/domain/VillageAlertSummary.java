package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VillageAlertSummary {
    private String name;
    private int alertsCount;

    public VillageAlertSummary(String name, int alertsCount) {
        this.name = name;
        this.alertsCount = alertsCount;
    }

    public int alertCount() {
        return alertsCount;
    }

    public String name() {
        return name;
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
