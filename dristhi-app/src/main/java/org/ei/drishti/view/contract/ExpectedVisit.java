package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.ChildServiceType;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.util.DateUtil.formatDate;

public class ExpectedVisit {
    private int offset;
    private String date;

    public ExpectedVisit(int offset, String date) {
        this.offset = offset;
        this.date = date;
    }

    public int offset() {
        return offset;
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
