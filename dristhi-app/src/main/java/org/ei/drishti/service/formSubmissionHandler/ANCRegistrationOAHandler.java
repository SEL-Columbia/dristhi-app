package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

public class ANCRegistrationOAHandler implements FormSubmissionHandler {
    private MotherService motherService;

    public ANCRegistrationOAHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.registerOutOfAreaANC(submission);
    }
}
