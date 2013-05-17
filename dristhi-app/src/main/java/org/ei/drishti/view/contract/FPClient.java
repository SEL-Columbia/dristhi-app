package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class FPClient {
    private final String entity_id;
    private final String name;
    private final String husband_name;
    private final String age;
    private final String thayi;
    private final String ec_number;
    private final String village;
    private final String fp_method;
    private final String side_effects;
    private final String num_pregnancies;
    private final String parity;
    private final String num_living_children;
    private final String num_stillbirths;
    private final String num_abortions;
    private final String days_due;
    private final String due_message;
    private final boolean isHighPriority;
    private final String family_planning_method_change_date;
    private final String photo_path;
    private final boolean is_youngest_child_under_two;
    private final String youngest_child_age;
    private final List<AlertDTO> alerts;
    private final String complication_date;
    private final String caste;
    private final String economicStatus;
    private final String fp_method_followup_date;
    private final String iudPlace;
    private final String iudPerson;
    private final String numberOfCondomsSupplied;
    private final String numberOfCentchromanPillsDelivered;
    private final String numberOfOCPDelivered;

    public FPClient(
            String entity_id, String ec_number, String thayi, String name, String husband_name, String age, String village,
            String fp_method, String family_planning_method_change_date, String side_effects, String complication_date, String iudPlace, String iudPerson, String numberOfCondomsSupplied, String numberOfCentchromanPillsDelivered, String numberOfOCPDelivered, String fp_method_followup_date, String caste, String economicStatus, String num_pregnancies,
            String parity, String num_living_children, String num_stillbirths, String num_abortions,
            boolean is_youngest_child_under_two, String youngest_child_age, String days_due, String due_message, boolean isHighPriority,
            String photoPath, List<AlertDTO> alerts) {
        this.entity_id = entity_id;
        this.name = name;
        this.husband_name = husband_name;
        this.age = age;
        this.thayi = thayi;
        this.ec_number = ec_number;
        this.village = village;
        this.fp_method = fp_method;
        this.side_effects = side_effects;
        this.num_pregnancies = num_pregnancies;
        this.parity = parity;
        this.num_living_children = num_living_children;
        this.num_stillbirths = num_stillbirths;
        this.num_abortions = num_abortions;
        this.days_due = days_due;
        this.due_message = due_message;
        this.isHighPriority = isHighPriority;
        this.family_planning_method_change_date = family_planning_method_change_date;
        this.photo_path = photoPath;
        this.is_youngest_child_under_two = is_youngest_child_under_two;
        this.youngest_child_age = youngest_child_age;
        this.alerts = alerts;
        this.complication_date = complication_date;
        this.caste = caste;
        this.economicStatus = economicStatus;
        this.fp_method_followup_date = fp_method_followup_date;
        this.iudPlace = iudPlace;
        this.iudPerson = iudPerson;
        this.numberOfCondomsSupplied = numberOfCondomsSupplied;
        this.numberOfCentchromanPillsDelivered = numberOfCentchromanPillsDelivered;
        this.numberOfOCPDelivered = numberOfOCPDelivered;
    }

    public String wifeName() {
        return name;
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
