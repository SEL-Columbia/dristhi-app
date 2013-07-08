package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.ChildService;
import org.ei.drishti.service.MotherService;

public class PNCVisitHandler implements FormSubmissionHandler {
    private final MotherService motherService;
    private ChildService childService;

    public PNCVisitHandler(MotherService motherService, ChildService childService) {
        this.motherService = motherService;
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        motherService.pncVisitHappened(submission);
        childService.pncVisitHappened(submission);
    }
}
