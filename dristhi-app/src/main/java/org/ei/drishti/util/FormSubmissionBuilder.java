package org.ei.drishti.util;

import org.ei.drishti.domain.FormSubmission;
import org.ei.drishti.domain.SyncStatus;

import static org.ei.drishti.domain.SyncStatus.SYNCED;

public class FormSubmissionBuilder {
    private String instanceId = "instance id 1";
    private String entityId = "entity id 1";
    private String formName = "form name 1";
    private String formInstance = "{}";
    private String version = "0";
    private SyncStatus syncStatus = SYNCED;

    public static FormSubmissionBuilder create() {
        return new FormSubmissionBuilder();
    }

    public FormSubmission build() {
        return new FormSubmission(instanceId, entityId, formName, formInstance, version, syncStatus);
    }

    public FormSubmissionBuilder withInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public FormSubmissionBuilder withEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public FormSubmissionBuilder withFormName(String formName) {
        this.formName = formName;
        return this;
    }

    public FormSubmissionBuilder withFormInstance(String formInstance) {
        this.formInstance = formInstance;
        return this;
    }

    public FormSubmissionBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public FormSubmissionBuilder withSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        return this;
    }
}
