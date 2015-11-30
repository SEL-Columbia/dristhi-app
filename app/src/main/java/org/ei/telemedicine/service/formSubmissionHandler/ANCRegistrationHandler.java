package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.MotherService;

public class ANCRegistrationHandler implements FormSubmissionHandler {
    private MotherService motherService;

    public ANCRegistrationHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.registerANC(submission);
    }
}
