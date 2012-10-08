package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PregnancyOutcomeDetails {
    private int daysPostpartum;
    private String dateOfDelivery;

    public PregnancyOutcomeDetails(String dateOfDelivery, int daysPostpartum) {
        this.dateOfDelivery = dateOfDelivery;
        this.daysPostpartum = daysPostpartum;
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
