package org.ei.opensrp.indonesia.service.formSubmissionHandler;

import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.indonesia.service.KartuIbuService;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

/**
 * Created by Dimas Ciputra on 10/28/15.
 */
public class KBRegistrationHandler implements FormSubmissionHandler {
    private KartuIbuService kartuIbuService;

    public KBRegistrationHandler(KartuIbuService kartuIbuService) {
        this.kartuIbuService = kartuIbuService;
    }

    @Override
    public void handle(FormSubmission submission) {
        kartuIbuService.registerKB(submission);
    }
}
