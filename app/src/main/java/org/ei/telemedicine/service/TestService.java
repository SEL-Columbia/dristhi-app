package org.ei.telemedicine.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.domain.TimelineEvent;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.repository.AllBeneficiaries;
import org.ei.telemedicine.repository.AllTestRepository;
import org.ei.telemedicine.repository.AllTimelineEvents;

public class TestService {
	private final AllTestRepository allTest;
	private final AllTimelineEvents allTimelineEvents;
	private final AllBeneficiaries allBeneficiaries;

	public TestService(AllTestRepository allTest,
			AllTimelineEvents allTimelineEvents,
			AllBeneficiaries allBeneficiaries) {
		this.allTest = allTest;
		this.allTimelineEvents = allTimelineEvents;
		this.allBeneficiaries = allBeneficiaries;
	}

	public void register(FormSubmission submission) {
		if (isNotBlank(submission
				.getFieldValue(AllConstants.CommonFormFields.SUBMISSION_DATE))) {
			allTimelineEvents
					.add(TimelineEvent.forECRegistered(
							submission.entityId(),
							submission
									.getFieldValue(AllConstants.CommonFormFields.SUBMISSION_DATE)));
		}
	}

//	public void closePregnantwoman(FormSubmission submission) {
//		allTest.close(submission.entityId());
//		allBeneficiaries.closeAllMothersForEC(submission.entityId());
//	}
}