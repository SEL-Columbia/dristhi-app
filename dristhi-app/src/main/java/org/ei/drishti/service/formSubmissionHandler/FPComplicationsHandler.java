package org.ei.drishti.service.formSubmissionHandler;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.EligibleCoupleService;

public class FPComplicationsHandler implements FormSubmissionHandler {
    private EligibleCoupleService ecService;

    public FPComplicationsHandler(EligibleCoupleService ecService) {
        this.ecService = ecService;
    }

    @Override
    public void handle(FormSubmission submission) {
        ecService.fpComplications(submission);
    }
}
