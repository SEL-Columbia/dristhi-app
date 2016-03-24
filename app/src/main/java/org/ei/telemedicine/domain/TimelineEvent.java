package org.ei.telemedicine.domain;

import android.util.Log;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.telemedicine.util.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.telemedicine.AllConstants.ANCVisitFields.ANM_POC;
import static org.ei.telemedicine.AllConstants.ANCVisitFields.POC_INFO;
import static org.ei.telemedicine.AllConstants.PNCVisitFields.BLOODGLUCOSEDATA;
import static org.ei.telemedicine.AllConstants.PNCVisitFields.BP_DIASTOLIC;
import static org.ei.telemedicine.AllConstants.PNCVisitFields.BP_SYSTOLIC;
import static org.ei.telemedicine.AllConstants.PNCVisitFields.HB_LEVEL;
import static org.ei.telemedicine.AllConstants.PNCVisitFields.ISCONSULTDOCTOR;
import static org.ei.telemedicine.AllConstants.PNCVisitFields.TEMPERATURE;
import static org.ei.telemedicine.util.EasyMap.create;
import static org.ei.telemedicine.util.EasyMap.mapOf;

public class TimelineEvent {
    private String caseId;
    private String type;
    private LocalDate referenceDate;
    private String title;
    private String detail1;
    private String detail2;

    public TimelineEvent(String caseId, String type, LocalDate referenceDate, String title, String detail1, String detail2) {
        this.caseId = caseId;
        this.type = type;
        this.referenceDate = referenceDate;
        this.title = title;
        this.detail1 = detail1;
        this.detail2 = detail2;
    }

    public static TimelineEvent forChildBirthInChildProfile(String caseId, String dateOfBirth, String weight, String immunizationsGiven) {
        Map<String, String> details = create("childWeight", weight).put("immunizationsGiven", immunizationsGiven).map();
        String detailsString = new DetailBuilder(details).withWeight("childWeight").withImmunizations("immunizationsGiven").value();
        return new TimelineEvent(caseId, "CHILD-BIRTH", LocalDate.parse(dateOfBirth), "Birth Date: " + DateUtil.formatDateForTimelineEvent(dateOfBirth), detailsString, null);
    }

    public static TimelineEvent forChildBirthInMotherProfile(String caseId, String dateOfBirth, String gender, String dateOfDelivery, String placeOfDelivery) {
        Map<String, String> details = create("dateOfDelivery", dateOfDelivery).put("placeOfDelivery", placeOfDelivery).map();
        String detailsString = new DetailBuilder(details).withDateOfDelivery("dateOfDelivery").withPlaceOfDelivery("placeOfDelivery").value();
        String title = (gender.equals("male") ? "Boy" : "Girl") + " Delivered";
        return new TimelineEvent(caseId, "CHILD-BIRTH", LocalDate.parse(dateOfBirth), title, detailsString, null);
    }

    public static TimelineEvent forChildBirthInECProfile(String caseId, String dateOfBirth, String gender, String dateOfDelivery) {
        String detailsString = new DetailBuilder(mapOf("dateOfDelivery", dateOfDelivery)).withDateOfDelivery("dateOfDelivery").value();
        String title = gender.equals("male") ? "Boy" : "Girl" + " Delivered";
        return new TimelineEvent(caseId, "CHILD-BIRTH", LocalDate.parse(dateOfBirth), title, detailsString, null);
    }

    public static TimelineEvent forStartOfPregnancy(String caseId, String registrationDate, String referenceDate) {
        return new TimelineEvent(caseId, "PREGNANCY", LocalDate.parse(registrationDate), "ANC Registered", "LMP Date: " + DateUtil.formatDateForTimelineEvent(referenceDate), null);
    }

    public static TimelineEvent forStartOfPregnancyForEC(String ecCaseId, String thayiCardNumber, String registrationDate, String referenceDate) {
        return new TimelineEvent(ecCaseId, "PREGNANCY", LocalDate.parse(registrationDate), "ANC Registered", "LMP Date: " + DateUtil.formatDateForTimelineEvent(referenceDate),
                "Thayi No: " + thayiCardNumber);
    }

    public static TimelineEvent forDeliveryPlan(String caseId, String deliveryFacilityName, String transportationPlan, String birthCompanion, String ashaPhoneNumber, String familyContactNumber, String highRiskReason, String referenceDate) {
        Map<String, String> map = create("deliveryFacilityName", deliveryFacilityName)
                .put("transportationPlan", transportationPlan)
                .put("birthCompanion", birthCompanion)
                .put("ashaPhoneNumber", ashaPhoneNumber)
                .put("phoneNumber", familyContactNumber)
                .put("reviewedHRPStatus", highRiskReason).map();

        String detailsString = new DetailBuilder(map)
                .withDeliveryFacilityName("deliveryFacilityName")
                .withTransportationPlan("transportationPlan")
                .withBirthCompanion("birthCompanion")
                .withAshaPhoneNumber("ashaPhoneNumber")
                .withFamilyContactNumber("phoneNumber")
                .withHighRiskReason("reviewedHRPStatus")
                .value();

        return new TimelineEvent(caseId, "DELIVERYPLAN", LocalDate.parse(referenceDate), "Delivery Plan", "Detail: " + detailsString, null);
    }

    public static TimelineEvent forChangeOfFPMethod(String caseId, String oldFPMethod, String newFPMethod, String dateOfFPChange) {
        return new TimelineEvent(caseId, "FPCHANGE", LocalDate.parse(dateOfFPChange), "Changed FP Method", "From: " + oldFPMethod, "To: " + newFPMethod);
    }

    public static TimelineEvent forANCCareProvided(String caseId, String visitNumber, String visitDate, Map<String, String> details) {
        String detailsString = new DetailBuilder(details).withBP("bpSystolic", "bpDiastolic").withTemperature("temperature").withHbLevel("hbLevel").withBloodGlucose("bloodGlucoseData").withDCReq("isConsultDoctor").withFetal("fetalData").withRisks("riskObservedDuringANC").withANMPOC(ANM_POC, "ANM Prescribed Drugs: ").value();
//        String pocData = new DetailBuilder(details).withPOC(POC_INFO).value();
        Log.e("Details String12", visitDate);
        return new TimelineEvent(caseId, "ANCVISIT", LocalDate.parse(visitDate), "ANC Visit " + visitNumber, detailsString, "");
    }

    public static TimelineEvent forPOCGiven(String caseId, String visitDate, String visitType, String title, Map<String, String> details) {
        String detailsString = new DetailBuilder(details).withPOC(POC_INFO).value();
        Log.e("Having POC", detailsString);
        try {
            JSONArray pocJsonArray = new JSONArray(detailsString);
            if (pocJsonArray.length() != 0) {
                JSONObject pocInfoData = pocJsonArray.getJSONObject(pocJsonArray.length() - 1);
                String pocDataStr = getDataFromJson(pocInfoData.toString(), "poc");
                String investigationsStr = getDataFromArray(getDataFromJson(pocDataStr, "investigations"), "Investigations: ");
                String diagnosisStr = getDataFromArray(getDataFromJson(pocDataStr, "diagnosis"), "Diagnosis: ");
                String drugsStr = getDatafromDrugsArray(getDataFromJson(pocDataStr, "drugs"));
                String adviceStr = !getDataFromJson(pocDataStr, "advice").equals("") ? "Advice:" + getDataFromJson(pocDataStr, "advice") : "";
                String detailStr = investigationsStr + diagnosisStr + (!drugsStr.equals("") ? "Drugs:" + drugsStr + ";" : "") + "\n" + adviceStr;
                return new TimelineEvent(caseId, visitType, LocalDate.parse(visitDate), title, detailStr, "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getDataFromJson(String jsonStr, String key) {
        try {
            if (jsonStr != null && !jsonStr.equals("")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                return jsonObject.has(key) ? jsonObject.getString(key) : "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getDataFromArray(String jsonArrayStr, String name) {
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayStr);
            String str = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                str = !str.equals("") ? str + ";" + jsonArray.getString(i) : jsonArray.getString(i);
            }
            return !str.equals("") ? name + str + "\n" : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDatafromDrugsArray(String jsonArray) {
        try {
            String result = "";
            if (jsonArray != null) {
                JSONArray jsonArray1 = new JSONArray(jsonArray);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    String data = jsonObject.getString("drugName") + "-" + jsonObject.getString("direction") + "-" + jsonObject.getString("dosage") + "-" + jsonObject.getString("frequency") + "- Days :" + jsonObject.getString("drugNoOfDays") + "- Qty :" + jsonObject.getString("drugQty");
                    result = !result.equals("") ? result + "\n" + data : data;
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static TimelineEvent forIFATabletsGiven(String caseId, String numberOfIFATabletsProvided, String visitDate) {
        return new TimelineEvent(caseId, "IFAPROVIDED", LocalDate.parse(visitDate), "IFA Provided", numberOfIFATabletsProvided + " tablets", null);
    }

    public static TimelineEvent forTTShotProvided(String caseId, String ttDose, String visitDate) {
        return new TimelineEvent(caseId, "TTSHOTPROVIDED", LocalDate.parse(visitDate), "TT Injection Given", ttDose, null);
    }

    public static TimelineEvent forECRegistered(String caseId, String registrationDate) {
        LocalDate registrationDate1 = DateTime.parse(registrationDate).toLocalDate();
        return new TimelineEvent(caseId, "ECREGISTERED", registrationDate1, "EC Registered", null, null);
    }

    public static TimelineEvent forMotherPNCVisit(String caseId, String visitNumber, String visitDate, String bpSystolic, String bpDiastolic, String temperature, String hbLevel, String bgmData, String isConsultDoctor, String anmPoc) {
        Map<String, String> details = create(BP_SYSTOLIC, bpSystolic).put(BP_DIASTOLIC, bpDiastolic).put(TEMPERATURE, temperature).put(HB_LEVEL, hbLevel).put(BLOODGLUCOSEDATA, bgmData).put(ISCONSULTDOCTOR, isConsultDoctor).put(ANM_POC, anmPoc).map();
        String detailsString = new DetailBuilder(details).withBP(BP_SYSTOLIC, BP_DIASTOLIC).withTemperature(TEMPERATURE).withHbLevel(HB_LEVEL).withBloodGlucose(BLOODGLUCOSEDATA).withDCReq(ISCONSULTDOCTOR).withANMPOC(ANM_POC, "ANM Prescribed Drugs: ").value();
        return new TimelineEvent(caseId, "PNCVISIT", LocalDate.parse(visitDate), "PNC Visit " + visitNumber, detailsString, null);
    }

    public static TimelineEvent forChildPNCVisit(String caseId, String visitNumber, String visitDate, String weight, String temperature) {
        Map<String, String> details = create("childWeight", weight).put("childTemperature", temperature).map();
        String detailsString = new DetailBuilder(details).withTemperature("childTemperature").withWeight("childWeight").value();
        return new TimelineEvent(caseId, "PNCVISIT", LocalDate.parse(visitDate), "PNC Visit " + visitNumber, detailsString, null);
    }

    public static TimelineEvent forChildIllness(String caseId, String visitDate, String temperature, String anmPoc) {
        Map<String, String> details = create("childTemperature", temperature).put(ANM_POC, anmPoc).map();
        String detailsString = new DetailBuilder(details).withTemperature("childTemperature").withWeight("childWeight").withANMPOC(ANM_POC, "ANM Prescribed Drugs: ").value();
        return new TimelineEvent(caseId, "CHILDILLNESS", LocalDate.parse(visitDate), "Child Illness", detailsString, null);
    }


    public static TimelineEvent forChildImmunization(String caseId, String immunizationsGiven, String immunizationsGivenDate) {
        String detailString = new DetailBuilder(null).withImmunizationsGiven(immunizationsGiven).value();
        return new TimelineEvent(caseId, "IMMUNIZATIONSGIVEN", LocalDate.parse(immunizationsGivenDate), "Immunization Date: " + DateUtil.formatDateForTimelineEvent(immunizationsGivenDate), detailString, null);
    }

    public static TimelineEvent forFPCondomRenew(String caseId, Map<String, String> details) {
        String detailString = new DetailBuilder(details).withNumberOfCondomsSupplied("numberOfCondomsSupplied").value();

        return new TimelineEvent(caseId, "FPRENEW", LocalDate.parse(details.get("familyPlanningMethodChangeDate")), "FP Renewed", detailString, null);
    }

    public static TimelineEvent forFPOCPRenew(String caseId, Map<String, String> details) {
        String detailString = new DetailBuilder(details).withNumberOfOCPDelivered("numberOfOCPDelivered").value();

        return new TimelineEvent(caseId, "FPRENEW", LocalDate.parse(details.get("familyPlanningMethodChangeDate")), "FP Renewed", detailString, null);
    }

    public static TimelineEvent forFPIUDRenew(String caseId, Map<String, String> details) {
        String detailString = new DetailBuilder(details).withNewIUDInsertionDate("familyPlanningMethodChangeDate").value();

        return new TimelineEvent(caseId, "FPRENEW", LocalDate.parse(details.get("familyPlanningMethodChangeDate")), "FP Renewed", detailString, null);
    }

    public static TimelineEvent forFPDMPARenew(String caseId, Map<String, String> details) {
        String detailString = new DetailBuilder(details).withDMPAInjectionDate("familyPlanningMethodChangeDate").value();

        return new TimelineEvent(caseId, "FPRENEW", LocalDate.parse(details.get("familyPlanningMethodChangeDate")), "FP Renewed", detailString, null);
    }

    public String type() {
        return type;
    }

    public LocalDate referenceDate() {
        return referenceDate;
    }

    public String caseId() {
        return caseId;
    }

    public String detail1() {
        return detail1;
    }

    public String detail2() {
        return detail2;
    }

    public String title() {
        return title;
    }

    private static class DetailBuilder {
        private Map<String, String> details;
        private final StringBuilder stringBuilder;

        private DetailBuilder(Map<String, String> details) {
            this.details = details;
            stringBuilder = new StringBuilder();
        }

        private DetailBuilder withTemperature(String temperature) {
            String temp = "";
            if (details.get(temperature) != null && !details.get(temperature).equals("") && details.get(temperature).contains("-")) {
                String[] tempData = details.get(temperature).split("-");
                temp = "Temp: " + tempData[0] + " °" + tempData[1] + "\n";
            }
            this.stringBuilder.append(checkEmptyField(temp, details.get(temperature)));
            return this;
        }


        private DetailBuilder withBloodGlucose(String bloodGlucoseData) {
            String bgm = "BGM: " + details.get(bloodGlucoseData) + " mmoI/L\n";
            this.stringBuilder.append(checkEmptyField(bgm, details.get(bloodGlucoseData)));
            return this;
        }

        private DetailBuilder withFetal(String fetalData) {
            String fetal = "Fetal Data : " + details.get(fetalData) + " bpm\n";
            this.stringBuilder.append(checkEmptyField(fetal, details.get(fetalData)));
            return this;
        }

        private DetailBuilder withRisks(String risksObserved) {
            String fetal = "Risks Observed : " + details.get(risksObserved) + "\n";
            this.stringBuilder.append(checkEmptyField(fetal, details.get(risksObserved)));
            return this;
        }

        private DetailBuilder withDCReq(String isConsultDoctor) {
            String dcReq = "Doctor Consultation: " + details.get(isConsultDoctor) + "\n";
            this.stringBuilder.append(checkEmptyField(dcReq, details.get(isConsultDoctor)));
            return this;
        }

        private DetailBuilder withDeliveryFacilityName(String deliveryFacilityName) {
            String deliveryFacility = "Delivery Facility Name: " + details.get(deliveryFacilityName);
            this.stringBuilder.append(checkEmptyField(deliveryFacility, details.get(deliveryFacilityName)));
            return this;
        }

        private DetailBuilder withTransportationPlan(String transportationPlan) {
            String plan = "Transportation Plan: " + details.get(transportationPlan);
            this.stringBuilder.append(checkEmptyField(plan, details.get(transportationPlan)));
            return this;
        }

        private DetailBuilder withBirthCompanion(String birthCompanion) {
            String companion = "Birth Companion: " + details.get(birthCompanion);
            this.stringBuilder.append(checkEmptyField(companion, details.get(birthCompanion)));
            return this;
        }

        private DetailBuilder withAshaPhoneNumber(String ashaPhoneNumber) {
            String ashaPhone = "Asha Phone Number: " + details.get(ashaPhoneNumber);
            this.stringBuilder.append(checkEmptyField(ashaPhone, details.get(ashaPhoneNumber)));
            return this;
        }

        private DetailBuilder withFamilyContactNumber(String phoneNumber) {
            String familyContact = "Phone Number: " + details.get(phoneNumber);
            this.stringBuilder.append(checkEmptyField(familyContact, details.get(phoneNumber)));
            return this;
        }

        private DetailBuilder withHighRiskReason(String highRiskReasons) {
            String hrpReason = "High Risk Reason: " + details.get(highRiskReasons);
            this.stringBuilder.append(checkEmptyField(hrpReason, details.get(highRiskReasons)));
            return this;
        }

        private DetailBuilder withWeight(String weight) {
            String wt = "Weight: " + details.get(weight) + " kg\n";
            this.stringBuilder.append(checkEmptyField(wt, details.get(weight)));
            return this;
        }

        private DetailBuilder withHbLevel(String hbLevel) {
            String hb = "Hb Level: " + details.get(hbLevel) + "\n";
            this.stringBuilder.append(checkEmptyField(hb, details.get(hbLevel)));
            return this;
        }

        private DetailBuilder withBP(String bpSystolic, String bpDiastolic) {
            String bp = "BP: " + details.get(bpSystolic) + "/" + details.get(bpDiastolic) + "\n";
            this.stringBuilder.append(checkEmptyField(bp, details.get(bpSystolic)));
            return this;
        }

        private DetailBuilder withANMPOC(String pocData, String title) {
            Log.e("Ssdfs", details.toString() + "-------------------------------" + pocData);
            String pocInfo = details.get(pocData) != null ? title + details.get(pocData).replace("[", "").replace("]", "").replace("\"", "") : "";
            this.stringBuilder.append(checkEmptyField(pocInfo, details.get(pocData)));
            return this;
        }

        private DetailBuilder withPOC(String pocData) {
            String pocInfo = details.get(pocData);
            this.stringBuilder.append(checkEmptyField(pocInfo, details.get("docPocInfo")));
            return this;
        }

        public DetailBuilder withDateOfDelivery(String dateOfDelivery) {
            if (isBlank(details.get(dateOfDelivery))) {
                return this;
            }
            this.stringBuilder.append("On: ").append(DateUtil.formatDateForTimelineEvent(details.get(dateOfDelivery))).append("\n");
            return this;
        }

        public DetailBuilder withPlaceOfDelivery(String placeOfDelivery) {
            String place = "At: " + details.get(placeOfDelivery) + "\n";
            this.stringBuilder.append(checkEmptyField(place, details.get(placeOfDelivery)));
            return this;
        }

        public DetailBuilder withImmunizations(String immunizationsGiven) {
            String immunizationString = details.get(immunizationsGiven);
            if (immunizationString == null) {
                return this;
            }
            String finalString = immunizationFormatter(immunizationString);
            String formattedImmunization = "Immunizations: " + finalString + "\n";
            this.stringBuilder.append(checkEmptyField(formattedImmunization, finalString));
            return this;
        }

        private String value() {
            return this.stringBuilder.toString();
        }

        private String checkEmptyField(String msg, String condition) {
            if (isBlank(condition)) {
                return "";
            }
            return msg;
        }

        public DetailBuilder withImmunizationsGiven(String immunizationsGiven) {
            if (isBlank(immunizationsGiven)) {
                return this;
            }
            this.stringBuilder.append(immunizationFormatter(immunizationsGiven));
            return this;
        }

        public DetailBuilder withNumberOfCondomsSupplied(String numberOfCondomsSupplied) {
            String condomsGiven = "Condoms given: " + details.get(numberOfCondomsSupplied) + "\n";
            this.stringBuilder.append(checkEmptyField(condomsGiven, details.get(numberOfCondomsSupplied)));
            return this;
        }

        public DetailBuilder withNumberOfOCPDelivered(String numberOfOCPDelivered) {
            String ocpCyclesGiven = "OCP cycles given: " + details.get(numberOfOCPDelivered) + "\n";
            this.stringBuilder.append(checkEmptyField(ocpCyclesGiven, details.get(numberOfOCPDelivered)));
            return this;
        }

        public DetailBuilder withNewIUDInsertionDate(String iudInsertionDate) {
            String newIUDInsertionDate = "New IUD insertion date: " + details.get(iudInsertionDate) + "\n";
            this.stringBuilder.append(checkEmptyField(newIUDInsertionDate, details.get(iudInsertionDate)));
            return this;
        }

        public DetailBuilder withDMPAInjectionDate(String dmpaInjectionDate) {
            String dmpaInjectionDateMessage = "DMPA injection date: " + details.get(dmpaInjectionDate) + "\n";
            this.stringBuilder.append(checkEmptyField(dmpaInjectionDateMessage, details.get(dmpaInjectionDate)));
            return this;
        }

        private String immunizationFormatter(String immunizationString) {
            String[] immunizations = immunizationString.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String immunization : immunizations) {
                Immunizations value = Immunizations.value(immunization);
                if (value == null) {
                    continue;
                }
                builder.append(value.displayValue()).append(", ");
            }
            return builder.toString().replaceAll(", $", "");
        }
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(o, this);
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
