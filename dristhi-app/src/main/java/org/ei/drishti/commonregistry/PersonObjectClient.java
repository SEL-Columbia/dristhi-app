package org.ei.drishti.commonregistry;

import org.ei.drishti.view.contract.SmartRegisterClient;

import java.util.Map;

/**
 * Created by user on 2/12/15.
 */
public class PersonObjectClient implements SmartRegisterClient{
    private String caseId;
    private Map<String, String> details;
    public String name = "";

    public PersonObjectClient(String caseId, Map<String, String> details,String name) {
        this.caseId = caseId;
        this.details = details;
        if(name != null) {
            this.name = name;
        }
//        this.name =  details.get(name)!=null?details.get(name):"";
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String entityId() {
        return caseId;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String displayName() {
        return null;
    }

    @Override
    public String village() {
        return null;
    }

    @Override
    public String wifeName() {
        return null;
    }

    @Override
    public String husbandName() {
        return null;
    }

    @Override
    public int age() {
        return 0;
    }

    @Override
    public int ageInDays() {
        return 0;
    }

    @Override
    public String ageInString() {
        return null;
    }

    @Override
    public boolean isSC() {
        return false;
    }

    @Override
    public boolean isST() {
        return false;
    }

    @Override
    public boolean isHighRisk() {
        return false;
    }

    @Override
    public boolean isHighPriority() {
        return false;
    }

    @Override
    public boolean isBPL() {
        return false;
    }

    @Override
    public String profilePhotoPath() {
        return null;
    }

    @Override
    public String locationStatus() {
        return null;
    }

    @Override
    public boolean satisfiesFilter(String filterCriterion) {
        return false;
    }

    @Override
    public int compareName(SmartRegisterClient client) {
        return 0;
    }
}
