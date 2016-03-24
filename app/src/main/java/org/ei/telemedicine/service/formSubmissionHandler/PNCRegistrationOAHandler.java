package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.ChildService;

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
