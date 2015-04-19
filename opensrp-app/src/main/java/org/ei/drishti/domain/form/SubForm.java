package org.ei.drishti.domain.form;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

public class SubForm {
    private String name;
    private String bind_type;
    private String default_bind_path;
    private List<FormField> fields;
    private List<Map<String, String>> instances;

    public SubForm(String name) {
        this.name = name;
    }

    public List<Map<String, String>> instances() {
        return instances;
    }

    public String name() {
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
