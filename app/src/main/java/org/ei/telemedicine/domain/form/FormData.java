package org.ei.telemedicine.domain.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormData {
    private String bind_type;
    private String default_bind_path;
    private List<FormField> fields;
    private List<SubForm> sub_forms;
    private Map<String, String> mapOfFieldsByName;

    public FormData(String bind_type, String default_bind_path, List<FormField> fields, List<SubForm> subForms) {
        this.bind_type = bind_type;
        this.default_bind_path = default_bind_path;
        this.fields = fields;
        this.sub_forms = subForms;
    }

    public List<FormField> fields() {
        return fields;
    }

    public String getFieldValue(String name) {
        if (mapOfFieldsByName == null) {
            createFieldMapByName();
        }
        return mapOfFieldsByName.get(name);
    }

    public SubForm getSubFormByName(String name) {
        for (SubForm sub_form : sub_forms) {
            if (StringUtils.equalsIgnoreCase(name, sub_form.name()))
                return sub_form;
        }
        throw new RuntimeException(MessageFormat.format("No sub form with the given name: {0}, in formData: {1}", name, this));
    }

    private void createFieldMapByName() {
        mapOfFieldsByName = new HashMap<String, String>();
        for (FormField field : fields) {
            mapOfFieldsByName.put(field.name(), field.value());
        }
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
