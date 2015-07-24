package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;

public interface FormSubmissionHandler {
    public void handle(FormSubmission submission);
}
