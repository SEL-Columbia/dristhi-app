package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.EligibleCoupleService;

public class RenewFPProductHandler implements FormSubmissionHandler {
    private EligibleCoupleService ecService;

    public RenewFPProductHandler(EligibleCoupleService ecService) {
        this.ecService = ecService;
    }

    @Override
    public void handle(FormSubmission submission) {
        ecService.renewFPProduct(submission);
    }
}
