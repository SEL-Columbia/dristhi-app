package org.ei.drishti.view.contract;

public class PregnancyDetails {
    private boolean isHighRisk;
    private String riskDetail;
    private String monthsPregnant;
    private String edd;

    public PregnancyDetails(boolean isHighRisk, String riskDetail, String monthsPregnant, String edd) {
        this.isHighRisk = isHighRisk;
        this.riskDetail = riskDetail;
        this.monthsPregnant = monthsPregnant;
        this.edd = edd;
    }
}
