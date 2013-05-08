package org.ei.drishti.service;

import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.repository.TimelineEventRepository;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.domain.TimelineEvent.forChangeOfFPMethod;
import static org.ei.drishti.util.EasyMap.mapOf;

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

    public void fpComplications(FormSubmission submission) {
    }

    public void fpChange(FormSubmission submission) {
        timelineEventRepository.add(forChangeOfFPMethod(submission.entityId(), submission.getFieldValue(CURRENT_FP_METHOD_FIELD_NAME),
                submission.getFieldValue(NEW_FP_METHOD_FIELD_NAME), submission.getFieldValue(FAMILY_PLANNING_METHOD_CHANGE_DATE_FIELD_NAME)));
        repository.mergeDetails(submission.entityId(), mapOf(CURRENT_FP_METHOD_FIELD_NAME, submission.getFieldValue(NEW_FP_METHOD_FIELD_NAME)));
    }

    public void renewFPProduct(FormSubmission submission) {

    }
}
