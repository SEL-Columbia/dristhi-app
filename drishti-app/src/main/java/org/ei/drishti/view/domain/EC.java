package org.ei.drishti.view.domain;

public class EC {
    private String caseId;
    private String wifeName;
    private String village;
    private String ecNumber;
    private boolean isHighRisk;

    public EC(String caseId, String wifeName, String village, String ecNumber, boolean highRisk) {
        this.caseId = caseId;
        this.wifeName = wifeName;
        this.village = village;
        this.ecNumber = ecNumber;
        isHighRisk = highRisk;
    }
}
