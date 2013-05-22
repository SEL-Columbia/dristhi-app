package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.MotherService;

public class ANCVisitHandler implements FormSubmissionHandler {
    private MotherService motherService;

    public ANCVisitHandler(MotherService motherService) {
        this.motherService = motherService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.ancVisit(submission);
    }
}
