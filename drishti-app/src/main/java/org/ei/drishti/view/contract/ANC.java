package org.ei.drishti.view.contract;

public class ANC {
    private  String caseId;
    private String womanName;
    private String villageName;
    private String thaayiCardNumber;
    private boolean isHighRisk;

    public ANC(String caseId, String womanName, String villageName, String thaayiCardNumber, boolean highRisk) {
        this.caseId = caseId;
        this.womanName = womanName;
        this.villageName = villageName;
        this.thaayiCardNumber = thaayiCardNumber;
        isHighRisk = highRisk;
    }
}
