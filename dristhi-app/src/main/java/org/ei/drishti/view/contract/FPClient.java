package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.util.IntegerUtil;

import java.util.List;
import java.util.Locale;

import static org.ei.drishti.AllConstants.ECRegistrationFields.*;
import static org.ei.drishti.util.StringUtil.humanize;

public class FPClient implements FPSmartRegisterClient {
    private String entityId;
    private String entityIdToSavePhoto;
    private String name;
    private String husbandName;
    private String age;
    private String ecNumber;
    private String village;
    private String fpMethod;
    private String num_pregnancies;
    private String parity;
    private String num_living_children;
    private String num_stillbirths;
    private String num_abortions;
    private boolean isHighPriority;
    private String familyPlanningMethodChangeDate;
    private String photo_path;
    private boolean is_youngest_child_under_two;
    private String youngestChildAge;
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


    public FPClient(String entityId, String name, String husbandName, String village, String ecNumber) {
        this.entityId = entityId;
        this.entityIdToSavePhoto = entityId;
        this.name = name;
        this.husbandName = husbandName;
        this.village = village;
        this.ecNumber = ecNumber;
    }

    @Override
    public String entityId() {
        return entityId;
    }

    @Override
    public String name() {
        return humanize(name);
    }

    @Override
    public String displayName() {
        return name();
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
        return "(" + age + ")";
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
        return false;
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
        return null;
    }

    @Override
    public boolean satisfiesFilter(String filter) {
        return name.toLowerCase(Locale.getDefault()).startsWith(filter.toLowerCase())
                || String.valueOf(ecNumber).startsWith(filter);
    }


    @Override
    public int compareName(SmartRegisterClient client) {
        return this.name().compareToIgnoreCase(client.name());
    }

    public FPClient withAge(String age) {
        this.age = age;
        return this;
    }

    public FPClient withFPMethod(String fp_method) {
        this.fpMethod = fp_method;
        return this;
    }

    public FPClient withNumberOfPregnancies(String num_pregnancies) {
        Integer value = IntegerUtil.tryParse(num_pregnancies, 0);
        this.num_pregnancies = value > 8 ? "8+" : value.toString();
        return this;
    }

    public FPClient withParity(String parity) {
        Integer value = IntegerUtil.tryParse(parity, 0);
        this.parity = value > 8 ? "8+" : value.toString();
        return this;
    }

    public FPClient withNumberOfLivingChildren(String num_living_children) {
        Integer value = IntegerUtil.tryParse(num_living_children, 0);
        this.num_living_children = value > 8 ? "8+" : value.toString();
        return this;
    }

    public FPClient withNumberOfStillBirths(String num_stillbirths) {
        Integer value = IntegerUtil.tryParse(num_stillbirths, 0);
        this.num_stillbirths = value > 8 ? "8+" : value.toString();
        return this;
    }

    public FPClient withNumberOfAbortions(String num_abortions) {
        Integer value = IntegerUtil.tryParse(num_abortions, 0);
        this.num_abortions = value > 8 ? "8+" : value.toString();
        return this;
    }

    public FPClient withIsHighPriority(boolean highPriority) {
        isHighPriority = highPriority;
        return this;
    }

    public FPClient withFamilyPlanningMethodChangeDate(String family_planning_method_change_date) {
        this.familyPlanningMethodChangeDate = family_planning_method_change_date;
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
        this.youngestChildAge = youngest_child_age;
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

    public String numberOfPregnancies() {
        Integer value = IntegerUtil.tryParse(num_pregnancies, 0);
        return value > 8 ? "8+" : value.toString();
    }

    public String parity() {
        Integer value = IntegerUtil.tryParse(parity, 0);
        return value > 8 ? "8+" : value.toString();
    }

    public String numberOfLivingChildren() {
        Integer value = IntegerUtil.tryParse(num_living_children, 0);
        return value > 8 ? "8+" : value.toString();
    }

    public String numberOfStillbirths() {
        Integer value = IntegerUtil.tryParse(num_stillbirths, 0);
        return value > 8 ? "8+" : value.toString();
    }

    public String numberOfAbortions() {
        Integer value = IntegerUtil.tryParse(num_abortions, 0);
        return value > 8 ? "8+" : value.toString();
    }

    @Override
    public String familyPlanningMethodChangeDate() {
        return familyPlanningMethodChangeDate;
    }

    @Override
    public String numberOfOCPDelivered() {
        return numberOfOCPDelivered;
    }

    @Override
    public String numberOfCondomsSupplied() {
        return numberOfCondomsSupplied;
    }

    @Override
    public String numberOfCentchromanPillsDelivered() {
        return numberOfCentchromanPillsDelivered;
    }

    @Override
    public String iudPerson() {
        return iudPerson;
    }

    @Override
    public String iudPlace() {
        return iudPlace;
    }

    @Override
    public Integer ecNumber() {
        return IntegerUtil.tryParse(ecNumber, 0);
    }

    @Override
    public FPMethod fpMethod() {
        return FPMethod.tryParse(this.fpMethod, FPMethod.NONE);
    }

    @Override
    public String youngestChildAge() {
        return youngestChildAge + "m";
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
