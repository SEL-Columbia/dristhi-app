package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

public class DeliveryOutcomeHandler implements FormSubmissionHandler {
    private final MotherService motherService;

    public DeliveryOutcomeHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.deliveryOutcome(submission);
    }
}
