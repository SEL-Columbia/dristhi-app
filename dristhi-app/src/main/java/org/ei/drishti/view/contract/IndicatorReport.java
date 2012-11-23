package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IndicatorReport {
    private String indicatorIdentifier;
    private String description;
    private String annualTarget;
    private String currentProgress;
    private String currentMonth;
    private String aggregatedProgress;
    private String percentageOfTargetAchieved;

    public IndicatorReport(String indicatorIdentifier, String description, String annualTarget, String currentProgress, String currentMonth,
                           String aggregatedProgress, String percentageOfTargetAchieved) {
        this.indicatorIdentifier = indicatorIdentifier;
        this.description = description;
        this.annualTarget = annualTarget;
        this.currentProgress = currentProgress;
        this.currentMonth = currentMonth;
        this.aggregatedProgress = aggregatedProgress;
        this.percentageOfTargetAchieved = percentageOfTargetAchieved;
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
