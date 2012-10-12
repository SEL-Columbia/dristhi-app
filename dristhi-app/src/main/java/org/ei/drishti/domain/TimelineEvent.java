package org.ei.drishti.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Map;

import static org.ei.drishti.util.DateUtil.formatDate;

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

    public static TimelineEvent forChildBirth(String caseId, String dateOfBirth, String gender) {
        return new TimelineEvent(caseId, "CHILD-BIRTH", LocalDate.parse(dateOfBirth), "Child Born", StringUtils.capitalize(gender), "DOB: " + formatDate(dateOfBirth));
    }

    public static TimelineEvent forStartOfPregnancy(String caseId, String referenceDate) {
        return new TimelineEvent(caseId, "PREGNANCY", LocalDate.parse(referenceDate), "ANC Registered", "LMP: " + formatDate(referenceDate), null);
    }

    public static TimelineEvent forStartOfPregnancyForEC(String ecCaseId, String thaayiCardNumber, String referenceDate) {
        return new TimelineEvent(ecCaseId, "PREGNANCY", LocalDate.parse(referenceDate), "ANC Registered", "LMP: " + formatDate(referenceDate),
                "Thayi No: " + thaayiCardNumber);
    }

    public static TimelineEvent forChangeOfFPMethod(String caseId, String oldFPMethod, String newFPMethod, String dateOfFPChange) {
        return new TimelineEvent(caseId, "FPCHANGE", LocalDate.parse(dateOfFPChange), "Changed FP Method", "From: " + oldFPMethod, "To: " + newFPMethod);
    }

    public static TimelineEvent forANCCareProvided(String caseId, String visitNumber, String visitDate, Map<String, String> details) {
        String detailsString = new DetailBuilder(details).withBP("bpSystolic", "bpDiastolic").withTemperature("temperature").withWeight("weight").withHbLevel("hbLevel").value();
        return new TimelineEvent(caseId, "ANCVISIT", LocalDate.parse(visitDate), "ANC Visit " + visitNumber, detailsString, null);
    }

    public static TimelineEvent forIFATabletsProvided(String caseId, String numberOfIFATabletsProvided, String visitDate) {
        return new TimelineEvent(caseId, "IFAPROVIDED", LocalDate.parse(visitDate), "IFA Provided", numberOfIFATabletsProvided + " tablets", null);
    }


    public static TimelineEvent forTTShotProvided(String caseId, String ttDose, String visitDate) {
        return new TimelineEvent(caseId, "TTSHOTPROVIDED", LocalDate.parse(visitDate), "TT Injection Given", ttDose, null);
    }

    public static TimelineEvent forECRegistered(String caseId, String registrationDate) {
        LocalDate registrationDate1 = DateTime.parse(registrationDate).toLocalDate();
        return new TimelineEvent(caseId, "ECREGISTERED", registrationDate1, "EC Registered", null, null);
    }

    public static TimelineEvent forMotherPNCVisit(String caseId, String visitNumber, String visitDate, Map<String, String> details) {
        String detailsString = new DetailBuilder(details).withBP("bpSystolic", "bpDiastolic").withTemperature("motherTemperature").withHbLevel("hbLevel").value();
        return new TimelineEvent(caseId, "PNCVISIT", LocalDate.parse(visitDate), "PNC Visit " + visitNumber, detailsString, null);
    }

    public static TimelineEvent forChildPNCVisit(String caseId, String visitNumber, String visitDate, Map<String, String> details) {
        String detailsString = new DetailBuilder(details).withTemperature("childTemperature").withWeight("childWeight").value();

        return new TimelineEvent(caseId, "PNCVISIT", LocalDate.parse(visitDate), "PNC Visit " + visitNumber, detailsString, null);
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
            String temp = "Temp: " + details.get(temperature) + " °F<br />";
            this.stringBuilder.append(checkEmptyField(temp, details.get(temperature)));
            return this;
        }

        private DetailBuilder withWeight(String weight) {
            String wt = "Weight: " + details.get(weight) + " kg<br />";
            this.stringBuilder.append(checkEmptyField(wt, details.get(weight)));
            return this;
        }

        private DetailBuilder withHbLevel(String hbLevel) {
            String hb = "Hb Level: " + details.get(hbLevel) + "<br />";
            this.stringBuilder.append(checkEmptyField(hb, details.get(hbLevel)));
            return this;
        }

        private DetailBuilder withBP(String bpSystolic, String bpDiastolic) {
            String bp = "BP: " + details.get(bpSystolic) + "/" + details.get(bpDiastolic) + "<br />";
            this.stringBuilder.append(checkEmptyField(bp, details.get(bpSystolic)));
            return this;
        }

        private String value() {
            return this.stringBuilder.toString();
        }

        private String checkEmptyField(String msg, String condition) {
            if (StringUtils.isBlank(condition)) {
                return "";
            }
            return msg;
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
