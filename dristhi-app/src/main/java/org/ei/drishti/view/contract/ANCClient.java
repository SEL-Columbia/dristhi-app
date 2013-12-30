package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.AllConstants;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

public class ANCClient {
    private String entityId;
    private String ec_number;
    private String village;
    private String name;
    private String thayi;
    private String ancNumber;
    private String age;
    private String husbandName;
    private String photo_path;
    private String edd;
    private String lmp;
    private boolean isHighPriority;
    private boolean isHighRisk;
    private String riskFactors;
    private String locationStatus;
    private String caste;
    private String economicStatus;
    private List<AlertDTO> alerts;
    private List<ServiceProvidedDTO> services_provided;
    private String entityIdToSavePhoto;
    private String ashaPhoneNumber;

    public ANCClient(String entityId, String village, String name, String thayi, String edd, String lmp) {
        this.entityId = entityId;
        this.village = village;
        this.name = name;
        this.thayi = thayi;
        this.edd = LocalDateTime.parse(edd, DateTimeFormat.forPattern(AllConstants.FORM_DATE_TIME_FORMAT)).toString(ISODateTimeFormat.dateTime());
        this.lmp = lmp;
    }

    public String wifeName() {
        return name;
    }

    public ANCClient withHusbandName(String husbandName) {
        this.husbandName = husbandName;
        return this;
    }

    public ANCClient withAge(String age) {
        this.age = age;
        return this;
    }

    public ANCClient withECNumber(String ecNumber) {
        this.ec_number = ecNumber;
        return this;
    }

    public ANCClient withANCNumber(String ancNumber) {
        this.ancNumber = ancNumber;
        return this;
    }

    public ANCClient withIsHighPriority(boolean highPriority) {
        this.isHighPriority = highPriority;
        return this;
    }

    public ANCClient withIsHighRisk(boolean highRisk) {
        this.isHighRisk = highRisk;
        return this;
    }

    public ANCClient withIsOutOfArea(boolean outOfArea) {
        this.locationStatus = outOfArea ? "out_of_area" : "in_area";
        return this;
    }

    public ANCClient withCaste(String caste) {
        this.caste = caste;
        return this;
    }

    public ANCClient withHighRiskReason(String highRiskReason) {
        this.riskFactors = highRiskReason;
        return this;
    }

    public ANCClient withEconomicStatus(String economicStatus) {
        this.economicStatus = economicStatus;
        return this;
    }

    public ANCClient withPhotoPath(String photoPath) {
        this.photo_path = photoPath;
        return this;
    }

    public ANCClient withAlerts(List<AlertDTO> alerts) {
        this.alerts = alerts;
        return this;
    }

    public ANCClient withServicesProvided(List<ServiceProvidedDTO> servicesProvided) {
        this.services_provided = servicesProvided;
        return this;
    }

    public ANCClient withEntityIdToSavePhoto(String entityIdToSavePhoto) {
        this.entityIdToSavePhoto = entityIdToSavePhoto;
        return this;
    }

    public ANCClient withAshaPhoneNumber(String ashaPhoneNumber) {
        this.ashaPhoneNumber = ashaPhoneNumber;
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
