package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;

public class PNCClient {
    private String entityId;
    private String ec_number;
    private String village;
    private String name;
    private String thayi;
    private String age;
    private String womanDOB;
    private String husbandName;
    private String photo_path;
    private boolean isHighPriority;
    private boolean isHighRisk;
    private String locationStatus;
    private String economicStatus;
    private String caste;
    private String fp_method;
    private String iudPlace;
    private String iudPerson;
    private String family_planning_method_change_date;
    private String numberOfCondomsSupplied;
    private String numberOfOCPDelivered;
    private String numberOfCentchromanPillsDelivered;
    private String deliveryDate;
    private String deliveryPlace;
    private String deliveryType;
    private String deliveryComplications;
    private String pncComplications;
    private String otherDeliveryComplications;
    private List<AlertDTO> alerts;
    private List<ServiceProvidedDTO> services_provided;
    private List<ChildClient> children;
    private String entityIdToSavePhoto;

    public PNCClient(String entityId, String village, String name, String thayi, String deliveryDate) {
        this.entityId = entityId;
        this.village = village;
        this.name = name;
        this.thayi = thayi;
        this.deliveryDate = LocalDateTime.parse(deliveryDate).toString(ISODateTimeFormat.dateTime());
    }

    public String wifeName() {
        return name;
    }

    public PNCClient withHusbandName(String husbandName) {
        this.husbandName = husbandName;
        return this;
    }

    public PNCClient withAge(String age) {
        this.age = age;
        return this;
    }

    public PNCClient withWomanDOB(String womanDOB) {
        this.womanDOB = womanDOB;
        return this;
    }

    public PNCClient withECNumber(String ecNumber) {
        this.ec_number = ecNumber;
        return this;
    }

    public PNCClient withIsHighPriority(boolean highPriority) {
        this.isHighPriority = highPriority;
        return this;
    }

    public PNCClient withIsHighRisk(boolean highRisk) {
        this.isHighRisk = highRisk;
        return this;
    }

    public PNCClient withIsOutOfArea(boolean outOfArea) {
        this.locationStatus = outOfArea ? "out_of_area" : "in_area";
        return this;
    }

    public PNCClient withCaste(String caste) {
        this.caste = caste;
        return this;
    }

    public PNCClient withEconomicStatus(String economicStatus) {
        this.economicStatus = economicStatus;
        return this;
    }

    public PNCClient withFPMethod(String fpMethod) {
        this.fp_method = fpMethod;
        return this;
    }

    public PNCClient withPhotoPath(String photoPath) {
        this.photo_path = photoPath;
        return this;
    }

    public PNCClient withIUDPlace(String iudPlace) {
        this.iudPlace = iudPlace;
        return this;
    }

    public PNCClient withIUDPerson(String iudPerson) {
        this.iudPerson = iudPerson;
        return this;
    }

    public PNCClient withFamilyPlanningMethodChangeDate(String family_planning_method_change_date) {
        this.family_planning_method_change_date = family_planning_method_change_date;
        return this;
    }

    public PNCClient withNumberOfCondomsSupplied(String numberOfCondomsSupplied) {
        this.numberOfCondomsSupplied = numberOfCondomsSupplied;
        return this;
    }

    public PNCClient withNumberOfOCPDelivered(String numberOfOCPDelivered) {
        this.numberOfOCPDelivered = numberOfOCPDelivered;
        return this;
    }

    public PNCClient withNumberOfCentchromanPillsDelivered(String numberOfCentchromanPillsDelivered) {
        this.numberOfCentchromanPillsDelivered = numberOfCentchromanPillsDelivered;
        return this;
    }

    public PNCClient withDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
        return this;
    }

    public PNCClient withDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public PNCClient withDeliveryComplications(String deliveryComplications) {
        this.deliveryComplications = deliveryComplications;
        return this;
    }

    public PNCClient withPNCComplications(String pncComplications) {
        this.pncComplications = pncComplications;
        return this;
    }

    public PNCClient withAlerts(List<AlertDTO> alerts) {
        this.alerts = alerts;
        return this;
    }

    public PNCClient withServicesProvided(List<ServiceProvidedDTO> servicesProvided) {
        this.services_provided = servicesProvided;
        return this;
    }

    public PNCClient withOtherDeliveryComplications(String otherDeliveryComplications) {
        this.otherDeliveryComplications = otherDeliveryComplications;
        return this;
    }

    public PNCClient withChildren(List<ChildClient> children) {
        this.children = children;
        return this;
    }

    public PNCClient withEntityIdToSavePhoto(String entityIdToSavePhoto) {
        this.entityIdToSavePhoto = entityIdToSavePhoto;
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
