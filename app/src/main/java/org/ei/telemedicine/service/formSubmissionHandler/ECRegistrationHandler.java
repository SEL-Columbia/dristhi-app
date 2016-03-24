package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.EligibleCoupleService;

public class ECRegistrationHandler implements FormSubmissionHandler {
    private EligibleCoupleService ecService;

    public ECRegistrationHandler(EligibleCoupleService ecService) {
        this.ecService = ecService;
    }

    @Override
    public void handle(FormSubmission submission) {
        ecService.register(submission);
    }
}
