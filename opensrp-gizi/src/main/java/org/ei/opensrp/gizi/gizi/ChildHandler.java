package org.ei.opensrp.gizi.gizi;

import org.ei.opensrp.domain.form.FormSubmission;
import org.ei.opensrp.service.formSubmissionHandler.FormSubmissionHandler;

/**
 * Created by Iq on 21/11/16.
 */
public class ChildHandler implements FormSubmissionHandler {
    private ChildService childService;

    public ChildHandler(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public void handle(FormSubmission submission) {
        childService.followup(submission);
    }
}


