package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.MotherService;

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
