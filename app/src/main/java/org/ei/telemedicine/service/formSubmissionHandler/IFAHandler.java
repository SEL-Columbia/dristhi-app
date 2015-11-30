package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.MotherService;

public class IFAHandler implements FormSubmissionHandler {
    private final MotherService motherService;

    public IFAHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.ifaTabletsGiven(submission);
    }
}
