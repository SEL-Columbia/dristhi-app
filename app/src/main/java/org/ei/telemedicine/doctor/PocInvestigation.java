package org.ei.telemedicine.doctor;

/**
 * Created by naveen on 6/2/15.
 */
public class PocInvestigation {
    String service_group_name;
    String investigation_name;

    public String getService_group_name() {
        return service_group_name;
    }

    public void setService_group_name(String service_group_name) {
        this.service_group_name = service_group_name;
    }

    public String getInvestigation_name() {
        return investigation_name;
    }

    public void setInvestigation_name(String investigation_name) {
        this.investigation_name = investigation_name;
    }
}
