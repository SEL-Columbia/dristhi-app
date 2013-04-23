package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.EligibleCoupleService;

public class FPChangeHandler implements FormSubmissionHandler {
    private EligibleCoupleService ecService;

    public FPChangeHandler(EligibleCoupleService ecService) {
        this.ecService = ecService;
    }

    @Override
    public void handle(FormSubmission submission) {
        ecService.fpChange(submission);
    }
}
