package org.ei.drishti.person;

import java.util.Map;

/**
 * Created by user on 2/12/15.
 */
public class Person {
    private String caseId;
    private Map<String, String> details;

    public Person( String caseId,Map<String, String> details) {
        this.details = details;
        this.caseId = caseId;
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
}
