package org.ei.opensrp.indonesia.service.formSubmissionHandler;

import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.indonesia.service.KartuAnakService;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

/**
 * Created by Dimas Ciputra on 10/28/15.
 */
public class AnakRegistrationHandler implements FormSubmissionHandler{
    private KartuAnakService kartuAnakService;

    public AnakRegistrationHandler(KartuAnakService kartuAnakService) {
        this.kartuAnakService = kartuAnakService;
    }

    @Override
    public void handle(FormSubmission submission) {
        kartuAnakService.register(submission);
    }
}
