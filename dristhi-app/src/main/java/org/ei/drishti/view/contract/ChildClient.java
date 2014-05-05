package org.ei.drishti.view.contract;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.AllConstants;
import org.ei.drishti.util.DateUtil;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.List;

import static org.ei.drishti.AllConstants.ECRegistrationFields.BPL_VALUE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.SC_VALUE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.ST_VALUE;

public class ChildClient implements ChildSmartRegisterClient {
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

    @Override
    public String village() {
        return village;
    }

    @Override
    public String wifeName() {
        return name();
    }

    @Override
    public String name() {
        return StringUtils.isBlank(name) ? "B/o " + motherName() : name;
    }

    @Override
    public String husbandName() {
        return motherName() + ", " + fatherName();
    }

    private String fatherName() {
        return fatherName;
    }

    @Override
    public int age() {
        return StringUtils.isBlank(dob) ? 0 : Years.yearsBetween(LocalDate.parse(dob), LocalDate.now()).getYears();
    }

    @Override
    public String ageInString() {
        return "(" + format(getAgeInDays()) + ", " + formatGender(gender() + ")");
    }

    private String formatGender(String gender) {
        return AllConstants.FEMALE_GENDER.equalsIgnoreCase(gender) ? "F" : "M";
    }

    @Override
    public String gender() {
        return gender;
    }

    public Integer ecNumber() {
        return Integer.parseInt(ecNumber);
    }

    public String locationStatus() {
        return locationStatus;
    }

    @Override
    public boolean isSC() {
        return caste != null && caste.equalsIgnoreCase(SC_VALUE);
    }

    @Override
    public boolean isST() {
        return caste != null && caste.equalsIgnoreCase(ST_VALUE);
    }

    @Override
    public boolean isHighPriority() {
        return false;
    }

    @Override
    public boolean isBPL() {
        return economicStatus != null && economicStatus.equalsIgnoreCase(BPL_VALUE);
    }


    @Override
    public String entityId() {
        return entityId;
    }

    @Override
    public String profilePhotoPath() {
        return photo_path;
    }

    @Override
    public boolean satisfiesFilter(String filterCriterion) {
        return (!StringUtils.isBlank(name) && name.toLowerCase().startsWith(filterCriterion.toLowerCase()))
                || (StringUtils.isBlank(motherName) && motherName.toLowerCase().startsWith(filterCriterion.toLowerCase()));
    }

    public int getAgeInDays() {
        return StringUtils.isBlank(dob) ? 0 : Days.daysBetween(LocalDate.parse(dob), DateUtil.today()).getDays();
    }

    public String format(int days_since) {
        int DAYS_THRESHOLD = 28;
        int WEEKS_THRESHOLD = 119;
        int MONTHS_THRESHOLD = 720;
        if (days_since < DAYS_THRESHOLD) {
            return (int) Math.floor(days_since) + "d";
        } else if (days_since < WEEKS_THRESHOLD) {
            return (int) Math.floor(days_since / 7) + "w";
        } else if (days_since < MONTHS_THRESHOLD) {
            return (int) Math.floor(days_since / 30) + "m";
        } else {
            return (int) Math.floor(days_since / 365) + "y";
        }
    }
}
