package org.ei.drishti.view.contract;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.ChildServiceType;

import java.util.Map;

import static org.ei.drishti.util.DateUtil.formatDate;

public class ServiceProvidedDTO {

    public static ServiceProvidedDTO emptyService = new ServiceProvidedDTO("", "", null);

    private String name;
    private String date;
    private Map<String, String> data;

    public ServiceProvidedDTO(String name, String date, Map<String, String> data) {
        this.name = name;
        this.date = date;
        this.data = data;
    }

    public ChildServiceType type() {
        return ChildServiceType.tryParse(name, ChildServiceType.EMPTY);
    }

    public String date() {
        return formatDate(date);
    }

    public Map<String, String> data() {
        return data;
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
