package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

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
