package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ei.drishti.domain.mapper.TTMapper;

import java.util.Map;

import static org.ei.drishti.util.EasyMap.mapOf;

public class ServiceProvided {
    public static final String IFA_SERVICE_PROVIDED_NAME = "IFA";
    public static final String TT_1_SERVICE_PROVIDED_NAME = "TT 1";
    public static final String TT_2_SERVICE_PROVIDED_NAME = "TT 2";
    public static final String TT_BOOSTER_SERVICE_PROVIDED_NAME = "TT Booster";
    public static final String HB_TEST_SERVICE_PROVIDED_NAME = "Hb Test";
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
        return new ServiceProvided(entityId, mappedTTDose, date, mapOf("dose", mappedTTDose));
    }

    public static ServiceProvided forHBTest(String entityId, String hbLevel, String date) {
        return new ServiceProvided(entityId, HB_TEST_SERVICE_PROVIDED_NAME, date, mapOf("hbLevel", hbLevel));
    }

    public static ServiceProvided forIFATabletsGiven(String entityId, String numberOfIFATabletsGiven, String date) {
        return new ServiceProvided(entityId, IFA_SERVICE_PROVIDED_NAME, date, mapOf("dose", numberOfIFATabletsGiven));
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
