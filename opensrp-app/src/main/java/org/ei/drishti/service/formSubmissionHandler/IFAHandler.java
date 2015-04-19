package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

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
