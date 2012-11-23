package org.ei.drishti.view.contract;

public class Beneficiary {
    private String caseId;
    private String womanName;
    private String husbandName;
    private String thaayiCardNumber;
    private String ecNumber;
    private String villageName;
    private boolean isHighRisk;

    public Beneficiary(String caseId, String womanName, String husbandName, String thaayiCardNumber, String ecNumber, String villageName, boolean highRisk) {
        this.caseId = caseId;
        this.womanName = womanName;
        this.husbandName = husbandName;
        this.thaayiCardNumber = thaayiCardNumber;
        this.ecNumber = ecNumber;
        this.villageName = villageName;
        isHighRisk = highRisk;
    }
}
