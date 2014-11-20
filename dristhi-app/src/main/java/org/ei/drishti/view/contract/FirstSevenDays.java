package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.ChildServiceType;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ei.drishti.util.DateUtil.formatDate;
import static org.ei.drishti.util.DateUtil.getDateFromISO8601DateString;

public class FirstSevenDays {

    private final String name;
    private final String date;
    private List<PNCCircleDatum> circleData;
    private List<PNCStatusDatum> statusData;
    private int day;

    public FirstSevenDays(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String name() {
        return name;
    }

    public String date() {
        return formatDate(date);
    }

    public int day() {
        return day;
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
