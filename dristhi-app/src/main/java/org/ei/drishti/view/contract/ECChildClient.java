package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.Period;

public class ECChildClient {
    private final String entityId;
    private final String gender;
    private final String dateOfBirth;

    public ECChildClient(String entityId, String gender, String dateOfBirth) {
        this.entityId = entityId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMale() {
        return gender != null && gender.equalsIgnoreCase("Male");
    }

    public boolean isFemale() {
        return !isMale();
    }

    public int getAgeInMonths() {
        LocalDate dob = LocalDate.parse(dateOfBirth);
        LocalDate now = LocalDate.now();
        return new Period(dob, now).getMonths();
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
