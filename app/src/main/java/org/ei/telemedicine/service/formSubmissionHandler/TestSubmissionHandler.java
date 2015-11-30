package org.ei.telemedicine.service.formSubmissionHandler;

import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.service.TestService;

public class TestSubmissionHandler implements FormSubmissionHandler {
	private TestService testService;

	public TestSubmissionHandler(TestService testService) {
		this.testService = testService;
	}

	@Override
	public void handle(FormSubmission submission) {
		testService.register(submission);
	}
}
