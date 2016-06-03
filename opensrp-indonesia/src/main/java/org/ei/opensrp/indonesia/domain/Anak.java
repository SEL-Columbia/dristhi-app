package org.ei.opensrp.indonesia.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Created by Dimas Ciputra on 4/7/15.
 */
public class Anak {

    private final String caseId;
    private final String ibuCaseId;
    private String dateOfBirth;
    private final String gender;
    private final Map<String, String> details;
    private boolean isClosed;
    private Ibu ibu;
    private KartuIbu kartuIbu;
    private String photoPath;

    public Anak(String caseId, String ibuCaseId, String dateOfBirth, String gender, Map<String, String> details) {
        this.caseId = caseId;
        this.ibuCaseId = ibuCaseId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.details = details;
        this.isClosed = false;
    }

    public Anak(String caseId, String ibuCaseId, String gender, Map<String, String> details) {
        this.caseId = caseId;
        this.ibuCaseId = ibuCaseId;
        this.gender = gender;
        this.details = details;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getIbuCaseId() {
        return ibuCaseId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public String getDetail(String object) {
        return details.get(object);
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Ibu getIbu() {
        return ibu;
    }

    public KartuIbu getKartuIbu() {
        return kartuIbu;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Anak setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    public Anak setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
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

    public Anak withIbu(Ibu ibu) {
        this.ibu = ibu;
        return this;
    }

    public Anak withKI(KartuIbu kartuIbu) {
        this.kartuIbu = kartuIbu;
        return this;
    }

    public Anak withPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }

    public Anak withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }
}
