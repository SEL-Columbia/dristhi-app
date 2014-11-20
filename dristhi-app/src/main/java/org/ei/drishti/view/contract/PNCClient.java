package org.ei.drishti.view.contract;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.FPMethod;
import org.ei.drishti.util.DateUtil;
import org.ei.drishti.util.IntegerUtil;
import org.ei.drishti.view.dialog.PNCVisitClause;
import org.ei.drishti.view.dialog.PNCVisitDayClause;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.*;

import static org.ei.drishti.AllConstants.COMMA_WITH_SPACE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.BPL_VALUE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.SC_VALUE;
import static org.ei.drishti.AllConstants.ECRegistrationFields.ST_VALUE;
import static org.ei.drishti.AllConstants.SPACE;
import static org.ei.drishti.util.DateUtil.*;
import static org.ei.drishti.util.StringUtil.humanize;
import static org.ei.drishti.util.StringUtil.humanizeAndDoUPPERCASE;
import static org.ei.drishti.util.StringUtil.replaceAndHumanize;

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
    private List<Integer> defaultVisits = new ArrayList<Integer>(Arrays.asList(1, 3, 7));
    private List<ExpectedVisit> expectedVisits;
    private int VISIT_END_OFFSET_DAY_COUNT = 7;

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
    public String deliveryDate() {
        return formatISO8601Date(deliveryDate, "dd/MM/YYYY");
    }

    @Override
    public String deliveryShortDate() {
        return formatISO8601Date(deliveryDate, "dd/MM");
    }

    @Override
    public LocalDate deliveryDateISO8601() {
        return getDateFromISO8601DateString(deliveryDate);
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

    public List<ExpectedVisit> calculateExpectedVisitDates() {
        expectedVisits = new ArrayList<ExpectedVisit>();
        for (Integer offset : defaultVisits) {
            LocalDate expectedVisitDate = deliveryDateISO8601().plusDays(offset);
            // Check if the copy of the date object is returned by the plus days function. If else this code will modify the current delivery date
            ExpectedVisit expectedVisit = new ExpectedVisit(offset, formatDate(expectedVisitDate, "dd/MM/YYYY"));
            expectedVisits.add(expectedVisit);
        }
        return expectedVisits;
    }

    public void preProcess() {
        List<ServiceProvidedDTO> firstSevenDaysServices = getFirstSevenDaysVisit();
        int currentDay = DateUtil.dayDifference(DateUtil.today(), deliveryDateISO8601());
        List<PNCCircleDatum> circleData = new ArrayList<PNCCircleDatum>();
        List<PNCStatusDatum> statusData = new ArrayList<PNCStatusDatum>();
        List<ExpectedVisit> expectedVisits = calculateExpectedVisitDates();
        for (ExpectedVisit expectedVisit : expectedVisits) {
            LocalDate expectedVisitDate = getDateFromISO8601DateString(expectedVisit.date());
            int expectedVisitDay = dayDifference(expectedVisitDate, deliveryDateISO8601());
            int numberOfVisits = new Filter<ServiceProvidedDTO>().applyFilterWithClause(firstSevenDaysServices, new PNCVisitDayClause(expectedVisitDay)).size();

        }
    }

    private List<ServiceProvidedDTO> getFirstSevenDaysVisit() {
        LocalDate endDate = deliveryDateISO8601().plusDays(VISIT_END_OFFSET_DAY_COUNT);

        List<ServiceProvidedDTO> validServices = new Filter<ServiceProvidedDTO>().applyFilterWithClause(services_provided, new PNCVisitClause(endDate));
//        List<ServiceProvidedDTO> validServices = filterValidServicesWithInSevenDays(services_provided, endDate);
        validServices = removeDuplicateServicesAndReturnList(validServices);
        Collections.sort(validServices);//Ascending sort based on the date
        validServices = findAndSetTheVisitDayOfTheServicesWithRespectToDeliveryDate(validServices);//Find the day offset from the delivery date for the visits
        return validServices;
    }

    private List<ServiceProvidedDTO> removeDuplicateServicesAndReturnList(List<ServiceProvidedDTO> serviceList) {
        if (serviceList != null) {
            Set<ServiceProvidedDTO> serviceSet = new HashSet<ServiceProvidedDTO>(serviceList);
            //To function this correctly the hash code of the object and equals method should override and should return the proper values
            serviceList.clear();
            serviceList.addAll(serviceSet);
        }
        return serviceList;
    }

    private List<ServiceProvidedDTO> findAndSetTheVisitDayOfTheServicesWithRespectToDeliveryDate(List<ServiceProvidedDTO> services) {

        if (services != null) {
            for (ServiceProvidedDTO service : services) {
                service.withDay(dayDifference(service.localDate(), deliveryDateISO8601()));
            }
        }
        return services;
    }
}
