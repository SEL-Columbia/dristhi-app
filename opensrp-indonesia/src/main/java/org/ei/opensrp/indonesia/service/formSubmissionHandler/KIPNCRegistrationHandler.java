package org.ei.opensrp.indonesia.service.formSubmissionHandler;

import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.indonesia.service.KartuIbuService;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

/**
 * Created by Dimas Ciputra on 10/26/15.
 */
public class KIPNCRegistrationHandler implements FormSubmissionHandler {
    private KartuIbuService kartuIbuService;

    public KIPNCRegistrationHandler(KartuIbuService kartuIbuService) {
        this.kartuIbuService = kartuIbuService;
    }

    @Override
    public void handle(FormSubmission submission) {
        kartuIbuService.registerPNC(submission);
    }
}
