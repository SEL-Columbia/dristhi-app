package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

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
