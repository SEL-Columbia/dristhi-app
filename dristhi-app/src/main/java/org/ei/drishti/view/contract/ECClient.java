package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ECClient {
    private String entityId;
    private String entityIdToSavePhoto;
    private String name;
    private String husbandName;
    private String dateOfBirth;
    private Integer ecNumber;
    private String village;
    private String fpMethod;
    private String numPregnancies;
    private String parity;
    private String numLivingChildren;
    private String numStillbirths;
    private String numAbortions;
    private boolean isHighPriority;
    private String familyPlanningMethodChangeDate;
    private String photo_path;
    private String caste;
    private String economicStatus;
    private String iudPlace;
    private String iudPerson;
    private String numberOfCondomsSupplied;
    private String numberOfCentchromanPillsDelivered;
    private String numberOfOCPDelivered;
    private String highPriorityReason;
    private String locationStatus;
    private List<ECChildClient> children;
    private Map<String, String> status;

    public ECClient(String entityId, String name, String husbandName, String village, Integer ecNumber) {
        this.entityId = entityId;
        this.entityIdToSavePhoto = entityId;
        this.name = name;
        this.husbandName = husbandName;
        this.village = village;
        this.ecNumber = ecNumber;
        this.children = new ArrayList<ECChildClient>();
    }

    public String wifeName() {
        return name;
    }

    public String village() {
        return village;
    }

    public String name() {
        return name;
    }

    public String husbandName() {
        return husbandName;
    }

    //#TODO: Write unit test
    public int age() {
        LocalDate dob = LocalDate.parse(dateOfBirth);
        LocalDate now = LocalDate.now();
        return new Period(dob, now).getYears();
    }

    public Integer ecNumber() {
        return ecNumber;
    }

    public ECClient withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public ECClient withIsOutOfArea(boolean outOfArea) {
        this.locationStatus = outOfArea ? "out_of_area" : "in_area";
        return this;
    }


    public ECClient withFPMethod(String fp_method) {
        this.fpMethod = fp_method;
        return this;
    }

    public ECClient withNumberOfPregnancies(String num_pregnancies) {
        this.numPregnancies = num_pregnancies;
        return this;
    }

    public ECClient withParity(String parity) {
        this.parity = parity;
        return this;
    }

    public ECClient withNumberOfLivingChildren(String num_living_children) {
        this.numLivingChildren = num_living_children;
        return this;
    }

    public ECClient withNumberOfStillBirths(String num_stillbirths) {
        this.numStillbirths = num_stillbirths;
        return this;
    }

    public ECClient withNumberOfAbortions(String num_abortions) {
        this.numAbortions = num_abortions;
        return this;
    }

    public ECClient withIsHighPriority(boolean highPriority) {
        isHighPriority = highPriority;
        return this;
    }

    public ECClient withFamilyPlanningMethodChangeDate(String family_planning_method_change_date) {
        this.familyPlanningMethodChangeDate = family_planning_method_change_date;
        return this;
    }

    public ECClient withPhotoPath(String photo_path) {
        this.photo_path = photo_path;
        return this;
    }

    public ECClient withCaste(String caste) {
        this.caste = caste;
        return this;
    }

    public ECClient withEconomicStatus(String economicStatus) {
        this.economicStatus = economicStatus;
        return this;
    }

    public ECClient withIUDPlace(String iudPlace) {
        this.iudPlace = iudPlace;
        return this;
    }

    public ECClient withIUDPerson(String iudPerson) {
        this.iudPerson = iudPerson;
        return this;
    }

    public ECClient withNumberOfCondomsSupplied(String numberOfCondomsSupplied) {
        this.numberOfCondomsSupplied = numberOfCondomsSupplied;
        return this;
    }

    public ECClient withNumberOfCentchromanPillsDelivered(String numberOfCentchromanPillsDelivered) {
        this.numberOfCentchromanPillsDelivered = numberOfCentchromanPillsDelivered;
        return this;
    }

    public ECClient withNumberOfOCPDelivered(String numberOfOCPDelivered) {
        this.numberOfOCPDelivered = numberOfOCPDelivered;
        return this;
    }

    public ECClient withHighPriorityReason(String highPriorityReason) {
        this.highPriorityReason = highPriorityReason;
        return this;
    }

    public ECClient withChildren(List<ECChildClient> children) {
        this.children = children;
        return this;
    }

    public ECClient addChild(ECChildClient childClient) {
        children.add(childClient);
        return this;
    }

    public String entityId() {
        return entityId;
    }

    public ECClient withStatus(Map<String, String> status) {
        this.status = status;
        return this;
    }

    public boolean willFilterMatches(String filter) {
        return name.toLowerCase().startsWith(filter)
                || String.valueOf(ecNumber).startsWith(filter)
                || village.toLowerCase().startsWith(filter);
    }

    //#TODO: Write unit test
    public static final Comparator<ECClient> NAME_COMPARATOR = new Comparator<ECClient>() {
        @Override
        public int compare(ECClient person, ECClient anotherPerson) {
            return person.name.compareToIgnoreCase(anotherPerson.name);
        }
    };

    //#TODO: Write unit test
    public static final Comparator<ECClient> EC_NUMBER_COMPARATOR = new Comparator<ECClient>() {
        @Override
        public int compare(ECClient person, ECClient anotherPerson) {
            return person.ecNumber.compareTo(anotherPerson.ecNumber);
        }
    };


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
