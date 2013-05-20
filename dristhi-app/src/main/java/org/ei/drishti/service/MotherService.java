package org.ei.drishti.service;

import org.apache.commons.lang3.StringUtils;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.repository.MotherRepository;
import org.ei.drishti.util.IntegerUtil;

import static org.ei.drishti.domain.TimelineEvent.*;

public class MotherService {
    public static final String MOTHER_ID = "motherId";
    public static final String REFERENCE_DATE = "referenceDate";
    public static final String THAYI_CARD_NUMBER = "thayiCardNumber";
    private MotherRepository motherRepository;
    private AllTimelineEvents allTimelines;
    private EligibleCoupleRepository eligibleCoupleRepository;

    public MotherService(MotherRepository motherRepository, AllTimelineEvents allTimelineEvents, EligibleCoupleRepository eligibleCoupleRepository) {
        this.motherRepository = motherRepository;
        this.allTimelines = allTimelineEvents;
        this.eligibleCoupleRepository = eligibleCoupleRepository;
    }

    public void registerANC(FormSubmission submission) {
        allTimelines.add(forStartOfPregnancy(submission.getFieldValue(MOTHER_ID), submission.getFieldValue(REFERENCE_DATE)));
        allTimelines.add(forStartOfPregnancyForEC(submission.entityId(), submission.getFieldValue(THAYI_CARD_NUMBER), submission.getFieldValue(REFERENCE_DATE)));
    }

    public void update(Action action) {
        motherRepository.updateDetails(action.caseID(), action.details());
    }

    public void ancCareProvided(Action action) {
        allTimelines.add(forANCCareProvided(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));

        String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
        if (numberOfIFATabletsProvided != null && Integer.parseInt(numberOfIFATabletsProvided) > 0) {
            allTimelines.add(forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
        }
        if (StringUtils.isNotBlank(action.get("ttDose"))) {
            allTimelines.add(forTTShotProvided(action.caseID(), action.get("ttDose"), action.get("visitDate")));
        }
    }

    public void registerOutOfAreaANC(Action action) {
        eligibleCoupleRepository.add(new EligibleCouple(action.get("ecCaseId"), action.get("wife"), action.get("husband"), "", action.get("village"), action.get("subcenter"), action.details()).asOutOfArea());
        motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                .withDetails(action.details()));
    }

    public void updateANCOutcome(Action action) {
        motherRepository.switchToPNC(action.caseID());
        motherRepository.updateDetails(action.caseID(), action.details());
    }

    public void pncVisitHappened(Action action) {
        allTimelines.add(forMotherPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
        motherRepository.updateDetails(action.caseID(), action.details());

        String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
        if (numberOfIFATabletsProvided != null && IntegerUtil.tryParse(numberOfIFATabletsProvided, 0) > 0) {
            allTimelines.add(forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
        }
    }

    public void close(Action action) {
        motherRepository.close(action.caseID());
    }
}
