package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.ChildServiceType;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import static org.ei.drishti.util.DateUtil.formatDate;
import static org.ei.drishti.util.DateUtil.getDateFromISO8601DateString;

public class PNCCircleDatum {

    private int day;
    private PNCVisitType type;
    private boolean coloured;


    public PNCCircleDatum(int day, PNCVisitType type, boolean coloured) {
        this.day = day;
        this.type = type;
        this.coloured = coloured;
    }

    public int day() {
        return day;
    }

    public PNCVisitType type() {
        return type;
    }

    public boolean coloured() {
        return coloured;
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
