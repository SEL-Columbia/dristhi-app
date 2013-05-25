package org.ei.drishti.service;

import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.MotherRepository;

import static org.ei.drishti.AllConstants.ANCCloseFields.*;
import static org.ei.drishti.AllConstants.IFAFields.IFA_TABLETS_DATE;
import static org.ei.drishti.AllConstants.IFAFields.NUMBER_OF_IFA_TABLETS_GIVEN;
import static org.ei.drishti.domain.TimelineEvent.*;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.IntegerUtil.tryParse;
import static org.ei.drishti.util.Log.logWarn;

public class MotherService {
    public static final String MOTHER_ID = "motherId";
    public static final String REFERENCE_DATE = "referenceDate";
    public static final String THAYI_CARD_NUMBER = "thayiCardNumber";
    public static final String ANC_VISIT_NUMBER = "ancVisitNumber";
    public static final String ANC_VISIT_DATE = "ancVisitDate";
    public static final String BP_SYSTOLIC = "bpSystolic";
    public static final String BP_DIASTOLIC = "bpDiastolic";
    public static final String TEMPERATURE = "temperature";
    public static final String WEIGHT = "weight";
    public static final String TT_DOSE = "ttDose";
    public static final String TT_DATE = "ttDate";
    private MotherRepository motherRepository;
    private AllBeneficiaries allBeneficiaries;
    private AllTimelineEvents allTimelines;
    private AllEligibleCouples allEligibleCouples;

    public MotherService(MotherRepository motherRepository, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples, AllTimelineEvents allTimelineEvents) {
        this.motherRepository = motherRepository;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelines = allTimelineEvents;
        this.allEligibleCouples = allEligibleCouples;
    }

    public void registerANC(FormSubmission submission) {
        addTimelineEventsForMotherRegistration(submission);
    }

    public void registerOutOfAreaANC(FormSubmission submission) {
        addTimelineEventsForMotherRegistration(submission);
    }

    public void ancVisit(FormSubmission submission) {
        allTimelines.add(forANCCareProvided(submission.entityId(), submission.getFieldValue(ANC_VISIT_NUMBER), submission.getFieldValue(ANC_VISIT_DATE),
                create("bpSystolic", submission.getFieldValue(BP_SYSTOLIC))
                        .put("bpDiastolic", submission.getFieldValue(BP_DIASTOLIC))
                        .put("temperature", submission.getFieldValue(TEMPERATURE))
                        .put("weight", submission.getFieldValue(WEIGHT))
                        .map()));
    }

    public void close(FormSubmission submission) {
        Mother mother = allBeneficiaries.findMother(submission.entityId());
        if (mother == null) {
            logWarn("Tried to close non-existent mother. Submission: " + submission);
            return;
        }

        allBeneficiaries.closeMother(submission.entityId());
        if (DEATH_OF_WOMAN_FIELD_VALUE.equalsIgnoreCase(submission.getFieldValue(CLOSE_REASON_FIELD_NAME))
                || PERMANENT_RELOCATION_FIELD_VALUE.equalsIgnoreCase(submission.getFieldValue(CLOSE_REASON_FIELD_NAME))) {
            allEligibleCouples.close(mother.ecCaseId());
        }
    }

    public void ttProvided(FormSubmission submission) {
        allTimelines.add(forTTShotProvided(submission.entityId(), submission.getFieldValue(TT_DOSE), submission.getFieldValue(TT_DATE)));
    }

    public void ifaTabletsGiven(FormSubmission submission) {
        String numberOfIFATabletsProvided = submission.getFieldValue(NUMBER_OF_IFA_TABLETS_GIVEN);
        if (tryParse(numberOfIFATabletsProvided, 0) > 0) {
            allTimelines.add(forIFATabletsProvided(submission.entityId(), numberOfIFATabletsProvided, submission.getFieldValue(IFA_TABLETS_DATE)));
        }
    }

    private void addTimelineEventsForMotherRegistration(FormSubmission submission) {
        allTimelines.add(forStartOfPregnancy(submission.getFieldValue(MOTHER_ID), submission.getFieldValue(REFERENCE_DATE)));
        allTimelines.add(forStartOfPregnancyForEC(submission.entityId(), submission.getFieldValue(THAYI_CARD_NUMBER), submission.getFieldValue(REFERENCE_DATE)));
    }

    @Deprecated
    public void updateANCOutcome(Action action) {
        motherRepository.switchToPNC(action.caseID());
        motherRepository.updateDetails(action.caseID(), action.details());
    }

    @Deprecated
    public void pncVisitHappened(Action action) {
        allTimelines.add(forMotherPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
        motherRepository.updateDetails(action.caseID(), action.details());

        String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
        if (numberOfIFATabletsProvided != null && tryParse(numberOfIFATabletsProvided, 0) > 0) {
            allTimelines.add(forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
        }
    }
}
