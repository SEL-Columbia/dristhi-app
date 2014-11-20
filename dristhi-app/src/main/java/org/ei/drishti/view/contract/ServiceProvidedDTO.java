package org.ei.drishti.view.contract;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.ANCServiceType;
import org.ei.drishti.domain.ChildServiceType;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ei.drishti.util.DateUtil.formatDate;
import static org.ei.drishti.util.DateUtil.getDateFromISO8601DateString;

public class ServiceProvidedDTO implements Comparable<ServiceProvidedDTO> {

    public static ServiceProvidedDTO emptyService = new ServiceProvidedDTO("", "", null);

    private String name;
    private String date;
    private int day;
    private Map<String, String> data = new HashMap<String, String>();

    public ServiceProvidedDTO(String name, String date, Map<String, String> data) {
        this.name = name;
        this.date = date;
        this.data = data;
    }

    public ChildServiceType type() {
        return ChildServiceType.tryParse(name, ChildServiceType.EMPTY);
    }

    public ANCServiceType ancServiceType() {
        return ANCServiceType.tryParse(name, ANCServiceType.EMPTY);
    }

    public String name() {
        return name;
    }

    public String date() {
        return formatDate(date);
    }

    public int day() {
        return day;
    }

    public LocalDate localDate() {
        return getDateFromISO8601DateString(date);
    }

    public String shortDate() {
        return formatDate(date, "dd/MM");
    }

    public String servicedOn() {
        return type().shortName() + ": " + shortDate();
    }

    public String ancServicedOn() {
        return ancServiceType().onServiceCompleteDisplayName() + ": " + shortDate();
    }

    public Map<String, String> data() {
        return data == null ? new HashMap<String, String>() : data;
    }

    public ServiceProvidedDTO withDay(int day) {
        this.day = day;
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
    public int compareTo(ServiceProvidedDTO another) {
        return this.localDate().isAfter(another.localDate()) ? 1 : 0;
    }
}
