package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.AllConstants;
import org.ei.drishti.util.IntegerUtil;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.ECRegistrationFields.*;
import static org.ei.drishti.util.DateUtil.today;
import static org.ei.drishti.util.StringUtil.humanize;
import static org.joda.time.Days.daysBetween;
import static org.joda.time.LocalDateTime.parse;

public class ANCClient implements ANCSmartRegisterClient {
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
        this.edd = parse(edd, DateTimeFormat.forPattern(AllConstants.FORM_DATE_TIME_FORMAT)).toString(ISODateTimeFormat.dateTime());
        this.lmp = lmp;
    }

    @Override
    public String entityId() {
        return entityId;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String displayName() {
        return humanize(name);
    }

    @Override
    public String village() {
        return humanize(village);
    }

    public String wifeName() {
        return name;
    }

    @Override
    public String husbandName() {
        return humanize(husbandName);
    }

    @Override
    public int age() {
        return IntegerUtil.tryParse(age, 0);
    }

    @Override
    public int ageInDays() {
        return IntegerUtil.tryParse(age, 0) * 365;
    }

    @Override
    public String ageInString() {
        return age;
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
    public boolean isHighRisk() {
        return isHighRisk;
    }

    @Override
    public boolean isHighPriority() {
        return isHighPriority;
    }

    @Override
    public boolean isBPL() {
        return economicStatus != null && economicStatus.equalsIgnoreCase(BPL_VALUE);
    }

    @Override
    public String profilePhotoPath() {
        return photo_path;
    }

    @Override
    public String locationStatus() {
        return locationStatus;
    }

    @Override
    public boolean satisfiesFilter(String filterCriterion) {
        return name.toLowerCase(Locale.getDefault()).startsWith(filterCriterion.toLowerCase())
                || String.valueOf(thayi).startsWith(filterCriterion);
    }

    @Override
    public int compareName(SmartRegisterClient client) {
        return this.name().compareToIgnoreCase(client.name());
    }

    @Override
    public String edd() {
        return edd;
    }

    @Override
    public String eddInDays() {
        return isBlank(edd) ? "0"
                : Integer.toString(daysBetween(parse(edd).toLocalDate(), today()).getDays());
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
