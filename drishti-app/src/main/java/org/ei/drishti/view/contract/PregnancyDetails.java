package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PregnancyDetails {
    private boolean isHighRisk;
    private String riskDetail;
    private String monthsPregnant;
    private String edd;

    public PregnancyDetails(boolean isHighRisk, String riskDetail, String monthsPregnant, String edd) {
        this.isHighRisk = isHighRisk;
        this.riskDetail = riskDetail;
        this.monthsPregnant = monthsPregnant;
        this.edd = edd;
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
