package org.ei.drishti.service;

import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.repository.TimelineEventRepository;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EligibleCoupleService {
    private static final String SUBMISSION_DATE_FORM_FIELD_NAME = "submissionDate";
    private EligibleCoupleRepository repository;
    private TimelineEventRepository timelineEventRepository;

    public EligibleCoupleService(EligibleCoupleRepository eligibleCoupleRepository, TimelineEventRepository timelineEventRepository) {
        this.repository = eligibleCoupleRepository;
        this.timelineEventRepository = timelineEventRepository;
    }

    public void register(Action action) {
        repository.add(new EligibleCouple(action.caseID(), action.get("wife"), action.get("husband"), action.get("ecNumber"),
                action.get("village"), action.get("subcenter"), action.details()));
    }

    public void updateDetails(Action action) {
        repository.updateDetails(action.caseID(), action.details());
    }

    public void delete(Action action) {
        repository.close(action.caseID());
    }

    public void register(FormSubmission submission) {
        if (isNotBlank(submission.getFieldValue(SUBMISSION_DATE_FORM_FIELD_NAME))) {
            timelineEventRepository.add(TimelineEvent.forECRegistered(submission.entityId(), submission.getFieldValue(SUBMISSION_DATE_FORM_FIELD_NAME)));
        }
    }
}
