package org.ei.drishti.view.contract;

public class PNC {
    private String caseId;
    private String womanName;
    private String thaayiCardNumber;
    private String villageName;
    private boolean isHighRisk;

    public PNC(String caseId, String womanName, String thaayiCardNumber, String villageName, boolean highRisk) {
        this.caseId = caseId;
        this.womanName = womanName;
        this.thaayiCardNumber = thaayiCardNumber;
        this.villageName = villageName;
        isHighRisk = highRisk;
    }
}
