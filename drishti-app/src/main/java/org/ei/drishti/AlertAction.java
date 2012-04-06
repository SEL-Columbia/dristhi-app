package org.ei.drishti;

import java.util.HashMap;

public class AlertAction {
    private String caseId;
    private String alertType;
    private HashMap<String, String> data;

    public AlertAction(String caseId, String alertType, HashMap<String, String> data) {
        this.caseId = caseId;
        this.alertType = alertType;
        this.data = data;
    }

    public CharSequence caseID() {
        return caseId;
    }

    public String type() {
        return alertType;
    }
}
