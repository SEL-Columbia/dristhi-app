package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.MotherService;

public class DeliveryPlanHandler implements FormSubmissionHandler {
    private final MotherService motherService;

    public DeliveryPlanHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.deliveryPlan(submission);
    }
}
