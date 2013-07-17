package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.ChildService;

public class VitaminAHandler implements FormSubmissionHandler {
    private final ChildService childService;

    public VitaminAHandler(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        childService.updateVitaminAProvided(submission);
    }
}
