package org.ei.drishti.view.contract;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.ChildServiceType;

import org.ei.drishti.util.DateUtil;

public class AlertDTO {
    public static final AlertDTO emptyAlert = new AlertDTO("", "", "");

    private final String name;
    private final String status;
    private final String date;

    public AlertDTO(String name, String status, String date) {
        this.name = name;
        this.status = status;
        this.date = date;
    }

    public String name() {
        return name;
    }

    public String status() {
        return status;
    }

    public ChildAlertStatus alertStatus() {
        return StringUtils.isBlank(status) ? ChildAlertStatus.EMPTY : ChildAlertStatus.from(status);
    }

    public ChildServiceType type() {
        return ChildServiceType.tryParse(name, ChildServiceType.EMPTY);
    }

    public String date() {
        return DateUtil.formatDate(date);
    }

    public String shortDate() {
        return DateUtil.formatDate(date, "dd/MM");
    }

    public boolean isUrgent() {
        return ChildAlertStatus.from(status).equals(ChildAlertStatus.URGENT);
    }

    public boolean isCompleted() {
        return ChildAlertStatus.from(status).equals(ChildAlertStatus.COMPLETE);
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
