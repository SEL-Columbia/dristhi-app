package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class FPClient {
    private String entityId;
    private String entityIdToSavePhoto;
    private String name;
    private String husband_name;
    private String age;
    private String thayi;
    private String ec_number;
    private String village;
    private String fp_method;
    private String num_pregnancies;
    private String parity;
    private String num_living_children;
    private String num_stillbirths;
    private String num_abortions;
    private boolean isHighPriority;
    private String family_planning_method_change_date;
    private String photo_path;
    private boolean is_youngest_child_under_two;
    private String youngest_child_age;
    private List<AlertDTO> alerts;
    private String complication_date;
    private String caste;
    private String economicStatus;
    private String fp_method_followup_date;
    private String iudPlace;
    private String iudPerson;
    private String numberOfCondomsSupplied;
    private String numberOfCentchromanPillsDelivered;
    private String numberOfOCPDelivered;
    private String condomSideEffect;
    private String iudSidEffect;
    private String ocpSideEffect;
    private String sterilizationSideEffect;
    private String injectableSideEffect;
    private String otherSideEffect;
    private String highPriorityReason;


    public FPClient(String entityId, String name, String husband_name, String village, String ecNumber) {
        this.entityId = entityId;
        this.entityIdToSavePhoto = entityId;
        this.name = name;
        this.husband_name = husband_name;
        this.village = village;
        this.ec_number = ecNumber;
    }

    public String wifeName() {
        return name;
    }

    public FPClient withAge(String age) {
        this.age = age;
        return this;
    }

    public FPClient withThayi(String thayi) {
        this.thayi = thayi;
        return this;
    }

    public FPClient withFPMethod(String fp_method) {
        this.fp_method = fp_method;
        return this;
    }

    public FPClient withNumberOfPregnancies(String num_pregnancies) {
        this.num_pregnancies = num_pregnancies;
        return this;
    }

    public FPClient withParity(String parity) {
        this.parity = parity;
        return this;
    }

    public FPClient withNumberOfLivingChildren(String num_living_children) {
        this.num_living_children = num_living_children;
        return this;
    }

    public FPClient withNumberOfStillBirths(String num_stillbirths) {
        this.num_stillbirths = num_stillbirths;
        return this;
    }

    public FPClient withNumberOfAbortions(String num_abortions) {
        this.num_abortions = num_abortions;
        return this;
    }

    public FPClient withIsHighPriority(boolean highPriority) {
        isHighPriority = highPriority;
        return this;
    }

    public FPClient withFamilyPlanningMethodChangeDate(String family_planning_method_change_date) {
        this.family_planning_method_change_date = family_planning_method_change_date;
        return this;
    }

    public FPClient withPhotoPath(String photo_path) {
        this.photo_path = photo_path;
        return this;
    }

    public FPClient withIsYoungestChildUnderTwo(boolean is_youngest_child_under_two) {
        this.is_youngest_child_under_two = is_youngest_child_under_two;
        return this;
    }

    public FPClient withYoungestChildAge(String youngest_child_age) {
        this.youngest_child_age = youngest_child_age;
        return this;
    }

    public FPClient withAlerts(List<AlertDTO> alerts) {
        this.alerts = alerts;
        return this;
    }

    public FPClient withComplicationDate(String complication_date) {
        this.complication_date = complication_date;
        return this;
    }

    public FPClient withCaste(String caste) {
        this.caste = caste;
        return this;
    }

    public FPClient withEconomicStatus(String economicStatus) {
        this.economicStatus = economicStatus;
        return this;
    }

    public FPClient withFPMethodFollowupDate(String fp_method_followup_date) {
        this.fp_method_followup_date = fp_method_followup_date;
        return this;
    }

    public FPClient withIUDPlace(String iudPlace) {
        this.iudPlace = iudPlace;
        return this;
    }

    public FPClient withIUDPerson(String iudPerson) {
        this.iudPerson = iudPerson;
        return this;
    }

    public FPClient withNumberOfCondomsSupplied(String numberOfCondomsSupplied) {
        this.numberOfCondomsSupplied = numberOfCondomsSupplied;
        return this;
    }

    public FPClient withNumberOfCentchromanPillsDelivered(String numberOfCentchromanPillsDelivered) {
        this.numberOfCentchromanPillsDelivered = numberOfCentchromanPillsDelivered;
        return this;
    }

    public FPClient withNumberOfOCPDelivered(String numberOfOCPDelivered) {
        this.numberOfOCPDelivered = numberOfOCPDelivered;
        return this;
    }

    public FPClient withCondomSideEffect(String condomSideEffect) {
        this.condomSideEffect = condomSideEffect;
        return this;
    }

    public FPClient withIUDSidEffect(String iudSidEffect) {
        this.iudSidEffect = iudSidEffect;
        return this;
    }

    public FPClient withOCPSideEffect(String ocpSideEffect) {
        this.ocpSideEffect = ocpSideEffect;
        return this;
    }

    public FPClient withSterilizationSideEffect(String sterilizationSideEffect) {
        this.sterilizationSideEffect = sterilizationSideEffect;
        return this;
    }

    public FPClient withInjectableSideEffect(String injectableSideEffect) {
        this.injectableSideEffect = injectableSideEffect;
        return this;
    }

    public FPClient withOtherSideEffect(String otherSideEffect) {
        this.otherSideEffect = otherSideEffect;
        return this;
    }

    public FPClient withHighPriorityReason(String highPriorityReason) {
        this.highPriorityReason = highPriorityReason;
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
