package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PregnancyDetails {
    private String monthsPregnant;
    private String edd;
    private boolean isEDDPassed;
    private boolean isLastMonthOfPregnancy;

    public PregnancyDetails(String monthsPregnant, String edd) {
        this.monthsPregnant = monthsPregnant;
        this.edd = edd;
        isEDDPassed = (Integer.valueOf(monthsPregnant) >= 9) ? true : false;
        isLastMonthOfPregnancy = Integer.valueOf(monthsPregnant) >= 8;
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

    public boolean isLastMonthOfPregnancy() {
        return isLastMonthOfPregnancy;
    }
}
