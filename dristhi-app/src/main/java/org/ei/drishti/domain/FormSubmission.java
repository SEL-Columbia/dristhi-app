package org.ei.drishti.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FormSubmission {
    private String instanceId;
    private String entityId;
    private String formName;
    private String instance;
    private String version;
    private SyncStatus syncStatus;

    public FormSubmission(String instanceId, String entityId, String formName, String instance, String version, SyncStatus syncStatus) {
        this.instanceId = instanceId;
        this.entityId = entityId;
        this.formName = formName;
        this.instance = instance;
        this.version = version;
        this.syncStatus = syncStatus;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, "version");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "version");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
