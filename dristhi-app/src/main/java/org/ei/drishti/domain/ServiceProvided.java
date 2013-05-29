package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.mapper.TTMapper;

import java.util.Map;

import static org.ei.drishti.util.EasyMap.mapOf;

public class ServiceProvided {
    private final String entityId;
    private final String name;
    private final String date;
    private final Map<String, String> data;

    public ServiceProvided(String entityId, String name, String date, Map<String, String> data) {
        this.name = name;
        this.date = date;
        this.data = data;
        this.entityId = entityId;
    }

    public static ServiceProvided forTTDose(String entityId, String ttDose, String date) {
        String mappedTTDose = TTMapper.valueOf(ttDose).value();
        return new ServiceProvided(entityId, mappedTTDose, date, mapOf("ttDose", mappedTTDose));
    }

    public static ServiceProvided forHBTest(String entityId, String hbLevel, String date) {
        return new ServiceProvided(entityId, "HB Test", date, mapOf("hbLevel", hbLevel));
    }

    public String name() {
        return name;
    }

    public String date() {
        return date;
    }

    public Map<String, String> data() {
        return data;
    }

    public String entityId() {
        return entityId;
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
