package org.ei.drishti.view.contract;

import android.util.Log;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.ChildServiceType;
import org.ei.drishti.util.DateUtil;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.ChildIllnessFields.*;
import static org.ei.drishti.AllConstants.ECRegistrationFields.*;
import static org.ei.drishti.domain.ChildServiceType.*;
import static org.ei.drishti.util.DateUtil.formatDate;
import static org.ei.drishti.util.StringUtil.humanize;
import static org.ei.drishti.view.contract.ServiceProvidedDTO.emptyService;

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

    private ServiceProvidedDTO lastService;
    private ServiceProvidedDTO illnessVisitServiceProvided;

    public ChildClient(String entityId, String gender, String weight, String thayiCardNumber) {
        this.entityId = entityId;
        this.gender = gender;
        this.weight = weight;
        this.thayiCardNumber = thayiCardNumber;
    }

    @Override
    public String motherName() {
        return humanize(motherName);
    }

    @Override
    public String village() {
        return humanize(village);
    }

    @Override
    public String wifeName() {
        return name();
    }

    @Override
    public String displayName() {
        return isBlank(name) ? "B/o " + motherName() : humanize(name);
    }

    @Override
    public String name() {
        return isBlank(name) ? "" : humanize(name);
    }

    @Override
    public String husbandName() {
        return motherName() + ", " + fatherName();
    }

    @Override
    public String fatherName() {
        return humanize(fatherName);
    }

    @Override
    public int age() {
        return isBlank(dob) ? 0 : Years.yearsBetween(LocalDate.parse(dob), LocalDate.now()).getYears();
    }

    @Override
    public String ageInString() {
        return "(" + format(ageInDays()) + ", " + formatGender(gender()) + ")";
    }

    private String formatGender(String gender) {
        return AllConstants.FEMALE_GENDER.equalsIgnoreCase(gender) ? "F" : "M";
    }

    @Override
    public String gender() {
        return gender;
    }

    @Override
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
    public boolean isHighRisk() {
        return isHighRisk;
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
        return (!isBlank(name) && name.toLowerCase().startsWith(filterCriterion.toLowerCase()))
                || (!isBlank(motherName) && motherName.toLowerCase().startsWith(filterCriterion.toLowerCase()));
    }

    @Override
    public int ageInDays() {
        return isBlank(dob) ? 0 : Days.daysBetween(LocalDate.parse(dob), DateUtil.today()).getDays();
    }

    @Override
    public int compareName(SmartRegisterClient anotherClient) {
        ChildSmartRegisterClient anotherChildClient = (ChildSmartRegisterClient) anotherClient;
        if (isBlank(this.name()) && isBlank(anotherChildClient.name())) {
            return this.motherName().compareTo(anotherChildClient.motherName());
        } else if (!isBlank(this.name()) && !isBlank(anotherChildClient.name())) {
            return this.name().compareTo(anotherChildClient.name());
        }
        return isBlank(this.name()) ? -1 : 1;
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

    @Override
    public String thayiCardNumber() {
        return thayiCardNumber;
    }

    @Override
    public String motherEcNumber() {
        return ecNumber;
    }

    @Override
    public String dateOfBirth() {
        return isBlank(dob) ? "" : formatDate(dob);
    }

    @Override
    public List<ServiceProvidedDTO> serviceProvided() {
        return services_provided;
    }

    @Override
    public ServiceProvidedDTO lastServiceProvided() {
        if (lastService == null) {
            lastService = serviceProvided().size() > 0
                    ? serviceProvided().get(serviceProvided().size() - 1)
                    : emptyService;
        }
        return lastService;
    }

    @Override
    public ServiceProvidedDTO illnessVisitServiceProvided() {
        if (illnessVisitServiceProvided == null) {
            illnessVisitServiceProvided = getIllnessVisitServiceProvided();
        }
        return illnessVisitServiceProvided;
    }

    private ServiceProvidedDTO getIllnessVisitServiceProvided() {
        for (ServiceProvidedDTO service : services_provided) {
            if (ILLNESS_VISIT.equals(service.type())) {
                return service;
            }
        }
        return emptyService;
    }

    @Override
    public ChildSickStatus sickStatus() {
        ServiceProvidedDTO service = illnessVisitServiceProvided();
        if (service == emptyService) {
            return ChildSickStatus.noDiseaseStatus;
        } else {
            final Map<String, String> data = service.data();
            String diseases;
            String otherDiseases;
            String date;
            if (data.containsKey(REPORT_CHILD_DISEASE)) {
                diseases = data.get(REPORT_CHILD_DISEASE);
                otherDiseases = data.get(REPORT_CHILD_DISEASE_OTHER);
                date = data.get(REPORT_CHILD_DISEASE_DATE);
            } else {
                diseases = data.get(AllConstants.ChildIllnessFields.CHILD_SIGNS);
                otherDiseases = data.get(AllConstants.ChildIllnessFields.CHILD_SIGNS_OTHER);
                date = data.get(AllConstants.ChildIllnessFields.SICK_VISIT_DATE);
            }
            return new ChildSickStatus(diseases, otherDiseases, date);
        }
    }

    @Override
    public boolean isBcgDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (BCG.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOpvDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (OPV_0.equals(service.type())
                    || OPV_1.equals(service.type())
                    || OPV_2.equals(service.type())
                    || OPV_3.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isHepBDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (HEPB_0.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPentavDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (PENTAVALENT_1.equals(service.type())
                    || PENTAVALENT_2.equals(service.type())
                    || PENTAVALENT_3.equals(service.type())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String bcgDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (BCG.equals(service.type())) {
                return service.shortDate();
            }
        }
        return "";
    }

    @Override
    public String opvDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (OPV_0.equals(service.type())) {
                return OPV_0.displayName() + ": " + service.shortDate();
            } else if (OPV_1.equals(service.type())) {
                return OPV_1.displayName() + ": " + service.shortDate();
            } else if (OPV_2.equals(service.type())) {
                return OPV_2.displayName() + ": " + service.shortDate();
            } else if (OPV_3.equals(service.type())) {
                return OPV_3.displayName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public String hepBDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (HEPB_0.equals(service.type())) {
                return HEPB_0.displayName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public String pentavDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (PENTAVALENT_1.equals(service.type())) {
                return PENTAVALENT_1.shortName() + ": " + service.shortDate();
            } else if (PENTAVALENT_2.equals(service.type())) {
                return PENTAVALENT_2.shortName() + ": " + service.shortDate();
            } else if (PENTAVALENT_3.equals(service.type())) {
                return PENTAVALENT_3.shortName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public boolean isMeaslesDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (MEASLES.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOpvBoosterDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (OPV_BOOSTER.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDptBoosterDone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (DPTBOOSTER_1.equals(service.type()) || DPTBOOSTER_2.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isVitaminADone() {
        for (ServiceProvidedDTO service : services_provided) {
            if (VITAMIN_A.equals(service.type())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String measlesDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (MEASLES.equals(service.type())) {
                return MEASLES.displayName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public String opvBoosterDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (OPV_BOOSTER.equals(service.type())) {
                return OPV_BOOSTER.displayName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public String dptBoosterDoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (DPTBOOSTER_1.equals(service.type())) {
                return DPTBOOSTER_1.shortName() + ": " + service.shortDate();
            } else if (DPTBOOSTER_2.equals(service.type())) {
                return DPTBOOSTER_2.shortName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public String vitaminADoneDate() {
        for (ServiceProvidedDTO service : services_provided) {
            if (VITAMIN_A.equals(service.type())) {
                return VITAMIN_A.shortName() + ": " + service.shortDate();
            }
        }
        return "";
    }

    @Override
    public List<AlertDTO> alerts() {
        return alerts;
    }

    public AlertDTO getOpvAlert() {
        AlertDTO opvAlert = null;
        for (AlertDTO alert : alerts) {
            if (ChildServiceType.OPV_0.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))
                    || ChildServiceType.OPV_1.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))
                    || ChildServiceType.OPV_2.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))
                    || ChildServiceType.OPV_3.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))) {
                if (opvAlert == null) {
                    opvAlert = alert;
                } else if (alert.isUrgent()) {
                    opvAlert = alert;
                }
            }
        }
        return opvAlert;
    }

    public AlertDTO getPentavAlert() {
        AlertDTO pentavAlert = null;
        for (AlertDTO alert : alerts) {
            if (ChildServiceType.PENTAVALENT_1.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))
                    || ChildServiceType.PENTAVALENT_2.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))
                    || ChildServiceType.PENTAVALENT_3.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))) {
                if (pentavAlert == null) {
                    pentavAlert = alert;
                } else if (alert.isUrgent()) {
                    pentavAlert = alert;
                }

            }
        }
        return pentavAlert;
    }

    public AlertDTO getAlert(ChildServiceType type) {
        AlertDTO requiredAlert = null;
        for (AlertDTO alert : alerts) {
            if (type.equals(ChildServiceType.tryParse(alert.name(), ChildServiceType.EMPTY))) {
                requiredAlert = alert;
                break;
            }
        }
        return requiredAlert;
    }

    public void PrintAlerts() {
        Log.d("GRTG", " client: " + displayName());
        for (AlertDTO alert : alerts) {
            Log.d("GRTG", "" + alert.name() + ", " + alert.status() + ", " + alert.date());
        }
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
