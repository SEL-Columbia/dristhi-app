package org.ei.drishti.view.contract;

import java.util.List;

public class PregnancyOutcomeDetails {
    private boolean isHighRisk;
    private String riskDetail;
    private int daysPostpartum;
    private String dateOfDelivery;
    private List<String> deliveryComplications;

    public PregnancyOutcomeDetails(String dateOfDelivery, int daysPostpartum, List<String> deliveryComplications, boolean highRisk, String riskDetail) {
        this.dateOfDelivery = dateOfDelivery;
        this.daysPostpartum = daysPostpartum;
        this.deliveryComplications = deliveryComplications;
        isHighRisk = highRisk;
        this.riskDetail = riskDetail;
    }
}
