package org.ei.opensrp.indonesia.service.formSubmissionHandler;

import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.indonesia.service.KartuIbuService;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

/**
 * Created by Dimas Ciputra on 2/16/15.
 */
public class KIRegistrationHandler implements FormSubmissionHandler{
    private KartuIbuService kartuIbuService;

    public KIRegistrationHandler(KartuIbuService kartuIbuService) {
        this.kartuIbuService = kartuIbuService;
    }

    @Override
    public void handle(FormSubmission submission) {
        kartuIbuService.register(submission);
    }
}
