package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.ChildService;

public class ChildImmunizationsHandler implements FormSubmissionHandler {
    private ChildService childService;

    public ChildImmunizationsHandler(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        childService.updateImmunizations(submission);
    }
}
