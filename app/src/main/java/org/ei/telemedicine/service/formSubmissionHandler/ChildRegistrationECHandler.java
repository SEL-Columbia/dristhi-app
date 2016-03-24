package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.ChildService;

public class ChildRegistrationECHandler implements FormSubmissionHandler {
    private final ChildService childService;

    public ChildRegistrationECHandler(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        childService.registerForEC(submission);
    }
}
