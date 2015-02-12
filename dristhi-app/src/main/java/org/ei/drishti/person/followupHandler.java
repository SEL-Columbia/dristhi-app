package org.ei.drishti.person;

import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.service.formSubmissionHandler.FormSubmissionHandler;

/**
 * Created by user on 2/12/15.
 */
public class followupHandler implements FormSubmissionHandler {

    private PersonService personService;

    public followupHandler(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void handle(FormSubmission submission) {
        personService.register(submission);
    }
}
