package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.ChildService;

public class PNCRegistrationOAHandler implements FormSubmissionHandler {
    private ChildService childService;

    public PNCRegistrationOAHandler(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        childService.pncRegistrationOA(submission);
    }
}
