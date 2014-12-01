package org.ei.drishti.view.contract.pnc;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.util.IntegerUtil;
import org.ei.drishti.view.contract.AlertDTO;
import org.ei.drishti.view.contract.ChildClient;
import org.ei.drishti.view.contract.ServiceProvidedDTO;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;
import java.util.Locale;

import static org.ei.drishti.AllConstants.COMMA_WITH_SPACE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.*;
import static org.ei.drishti.AllConstants.SPACE;
import static org.ei.drishti.util.DateUtil.*;
import static org.ei.drishti.util.StringUtil.*;

public class PNCClient implements PNCSmartRegisterClient {
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
    private List<ServiceProvidedDTO> expectedVisits;

    @SerializedName("first_7_days")
    private PNCFirstSevenDaysVisits pncFirstSevenDaysVisits;
    private List<ServiceProvidedDTO> recentlyProvidedServices;

    public PNCClient(String entityId, String village, String name, String thayi, String deliveryDate) {
        this.entityId = entityId;
        this.village = village;
        this.name = name;
        this.thayi = thayi;
        this.deliveryDate = LocalDateTime.parse(deliveryDate).toString(ISODateTimeFormat.dateTime());
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
                || String.valueOf(ec_number).startsWith(filter);
    }

    @Override
    public int compareName(SmartRegisterClient client) {
        return this.name().compareToIgnoreCase(client.name());
    }

    @Override
    public String thayiNumber() {
        return thayi;
    }

    @Override
    public String deliveryDateForDisplay() {
        return formatFromISOString(deliveryDate, "dd/MM/YYYY");
    }

    @Override
    public String deliveryShortDate() {
        return formatFromISOString(deliveryDate, "dd/MM");
    }

    @Override
    public LocalDate deliveryDate() {
        return getLocalDateFromISOString(deliveryDate);
    }

    @Override
    public String deliveryPlace() {
        return humanizeAndDoUPPERCASE(deliveryPlace);
    }

    @Override
    public String deliveryType() {
        return humanize(deliveryType);
    }

    @Override
    public String deliveryComplications() {
        return replaceAndHumanize(deliveryComplications, SPACE, COMMA_WITH_SPACE);
    }

    @Override
    public FPMethod fpMethod() {
        return FPMethod.tryParse(this.fp_method, FPMethod.NONE);
    }

    @Override
    public String familyPlanningMethodChangeDate() {
        return formatDate(family_planning_method_change_date, "dd/MM/YYYY");
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
    public String womanDOB() {
        return formatDate(womanDOB, "dd/MM/YYYY");
    }

    public List<ChildClient> children() {
        return children;
    }

    public PNCClient withDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
        return this;
    }

    public PNCClient withDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
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

    public PNCClient withExpectedVisits(List<ServiceProvidedDTO> visits) {
        this.expectedVisits = visits;
        return this;
    }

    public void withFirstSevenDaysVisit(PNCFirstSevenDaysVisits pncFirstSevenDaysVisits) {
        this.pncFirstSevenDaysVisits = pncFirstSevenDaysVisits;
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

    public List<ServiceProvidedDTO> expectedVisits() {
        return expectedVisits;
    }

    public List<ServiceProvidedDTO> servicesProvided() {
        return services_provided;
    }

    @Override
    public List<PNCCircleDatum> pncCircleData() {
        return pncFirstSevenDaysVisits.pncCircleData();
    }

    @Override
    public List<PNCStatusDatum> pncStatusData() {
        return pncFirstSevenDaysVisits.pncStatusData();
    }

    @Override
    public PNCStatusColor pncVisitStatusColor() {
        return pncFirstSevenDaysVisits.pncVisitStatusColor();
    }

    @Override
    public List<PNCTickDatum> pncTickData() {
        return pncFirstSevenDaysVisits.pncTickData();
    }

    @Override
    public List<PNCLineDatum> pncLineData() {
        return pncFirstSevenDaysVisits.pncLineData();
    }

    @Override
    public List<PNCVisitDaysDatum> visitDaysData() {
        return pncFirstSevenDaysVisits.visitDaysData();
    }

    @Override
    public PNCFirstSevenDaysVisits firstSevenDaysVisits() {
        return pncFirstSevenDaysVisits;
    }

    @Override
    public List<ServiceProvidedDTO> recentlyProvidedServices(){
        return recentlyProvidedServices;
    }

    public void withRecentlyProvidedServices(List<ServiceProvidedDTO> recentlyProvidedServices) {
        this.recentlyProvidedServices = recentlyProvidedServices;
    }
}
