package org.ei.drishti.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

public class TimelineEvent {
    private String ecCaseId;
    private String type;
    private LocalDate referenceDate;
    private String title;
    private String detail1;
    private String detail2;

    public TimelineEvent(String ecCaseId, String type, LocalDate referenceDate, String title, String detail1, String detail2) {
        this.ecCaseId = ecCaseId;
        this.type = type;
        this.referenceDate = referenceDate;
        this.title = title;
        this.detail1 = detail1;
        this.detail2 = detail2;
    }

    public static TimelineEvent forChildBirth(String ecCaseId, String dateOfBirth, String gender) {
        return new TimelineEvent(ecCaseId, "CHILD-BIRTH", LocalDate.parse(dateOfBirth), "Child Born", StringUtils.capitalize(gender), "DOB: " + dateOfBirth);
    }

    public static TimelineEvent forStartOfPregnancy(String ecCaseId, String referenceDate) {
        return new TimelineEvent(ecCaseId, "PREGNANCY", LocalDate.parse(referenceDate), "Got pregnant", "On: " + referenceDate, null);
    }

    public String type() {
        return type;
    }

    public LocalDate referenceDate() {
        return referenceDate;
    }

    public String ecCaseId() {
        return ecCaseId;
    }

    public String detail1() {
        return detail1;
    }

    public String detail2() {
        return detail2;
    }

    public String title() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
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
