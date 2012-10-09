package org.ei.drishti.domain;

import com.google.gson.Gson;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

public class Child {
    private final String caseId;
    private final String ecCaseId;
    private final String thaayiCardNumber;
    private String dateOfBirth;
    private final String gender;
    private final Map<String, String> details;

    public Child(String caseId, String motherCaseId, String thaayiCardNumber, String dateOfBirth, String gender, Map<String, String> details) {
        this.caseId = caseId;
        this.ecCaseId = motherCaseId;
        this.thaayiCardNumber = thaayiCardNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.details = details;
    }

    public String caseId() {
        return caseId;
    }

    public String ecCaseId() {
        return ecCaseId;
    }

    public String thaayiCardNumber() {
        return thaayiCardNumber;
    }

    public String dateOfBirth() {
        return dateOfBirth;
    }

    public String gender() {
        return gender;
    }

    public String details() {
        return new Gson().toJson(details);
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
