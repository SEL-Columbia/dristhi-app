package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.ChildService;
import org.ei.telemedicine.service.MotherService;

public class DeliveryOutcomeHandler implements FormSubmissionHandler {
    private final MotherService motherService;
    private ChildService childService;

    public DeliveryOutcomeHandler(MotherService motherService, ChildService childService) {
        this.motherService = motherService;
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.deliveryOutcome(submission);
        childService.register(submission);
    }
}
