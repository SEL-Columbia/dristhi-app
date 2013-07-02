package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ChildClient {
    private final String entityId;
    private String gender;
    private String weight;
    private final String thayiCardNumber;
    private String name;
    private String motherName;
    private String dob;
    private String motherAge;
    private String fatherName;
    private String village;
    private String locationStatus;
    private String economicStatus;
    private String caste;
    private boolean isHighRisk;
    private String photo_path;
    private String ecNumber;
    private List<AlertDTO> alerts;
    private List<ServiceProvidedDTO> services_provided;
    private String entityIdToSavePhoto;

    public ChildClient(String entityId, String gender, String weight, String thayiCardNumber) {
        this.entityId = entityId;
        this.gender = gender;
        this.weight = weight;
        this.thayiCardNumber = thayiCardNumber;
    }

    public String motherName() {
        return motherName;
    }

    public ChildClient withEntityIdToSavePhoto(String entityIdToSavePhoto) {
        this.entityIdToSavePhoto = entityIdToSavePhoto;
        return this;
    }

    public ChildClient withName(String name) {
        this.name = name;
        return this;
    }

    public ChildClient withMotherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public ChildClient withDOB(String dob) {
        this.dob = dob;
        return this;
    }

    public ChildClient withMotherAge(String motherAge) {
        this.motherAge = motherAge;
        return this;
    }

    public ChildClient withFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public ChildClient withVillage(String village) {
        this.village = village;
        return this;
    }

    public ChildClient withOutOfArea(boolean outOfArea) {
        this.locationStatus = outOfArea ? "out_of_area" : "in_area";
        return this;
    }

    public ChildClient withEconomicStatus(String economicStatus) {
        this.economicStatus = economicStatus;
        return this;
    }

    public ChildClient withCaste(String caste) {
        this.caste = caste;
        return this;
    }

    public ChildClient withIsHighRisk(boolean isHighRisk) {
        this.isHighRisk = isHighRisk;
        return this;
    }

    public ChildClient withPhotoPath(String photoPath) {
        this.photo_path = photoPath;
        return this;
    }

    public ChildClient withECNumber(String ecNumber) {
        this.ecNumber = ecNumber;
        return this;
    }

    public ChildClient withAlerts(List<AlertDTO> alerts) {
        this.alerts = alerts;
        return this;
    }

    public ChildClient withServicesProvided(List<ServiceProvidedDTO> servicesProvided) {
        this.services_provided = servicesProvided;
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
}
