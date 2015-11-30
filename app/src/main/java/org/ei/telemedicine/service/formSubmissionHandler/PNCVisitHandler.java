package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.ChildService;
import org.ei.telemedicine.service.MotherService;

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
