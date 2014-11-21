package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.ei.drishti.util.DateUtil.formatDate;

public class ExpectedVisit {
    private int day;
    private String date;

    public ExpectedVisit(int day, String date) {
        this.day = day;
        this.date = date;
    }

    public int day() {
        return day;
    }

    public String date() {
        return formatDate(date);
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
