package org.ei.drishti.domain;

/**
 * Created by Dimas Ciputra on 3/23/15.
 */
public class FormDefinitionVersion {

    private String formName;
    private String version;
    private SyncStatus syncStatus;

    public FormDefinitionVersion(String formName, String version) {
        this.formName = formName;
        this.version = version;
    }

    public FormDefinitionVersion withSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
        return this;
    }

    public String getFormName() {
        return formName;
    }

    public String getVersion() {
        return version;
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }
}
