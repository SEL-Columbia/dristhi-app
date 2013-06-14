package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

public class PNCVisitHandler implements FormSubmissionHandler {
    private final MotherService motherService;

    public PNCVisitHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.pncVisitHappened(submission);
    }
}
