package org.ei.drishti.service;

import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.MotherRepository;

import static org.ei.drishti.AllConstants.ANCCloseFields.*;
import static org.ei.drishti.AllConstants.ANCVisitFields.*;
import static org.ei.drishti.AllConstants.BOOLEAN_FALSE;
import static org.ei.drishti.AllConstants.DeliveryOutcomeFields.DID_MOTHER_SURVIVE;
import static org.ei.drishti.AllConstants.DeliveryOutcomeFields.DID_WOMAN_SURVIVE;
import static org.ei.drishti.AllConstants.HbTestFields.HB_LEVEL;
import static org.ei.drishti.AllConstants.HbTestFields.HB_TEST_DATE;
import static org.ei.drishti.AllConstants.IFAFields.IFA_TABLETS_DATE;
import static org.ei.drishti.AllConstants.IFAFields.NUMBER_OF_IFA_TABLETS_GIVEN;
import static org.ei.drishti.AllConstants.TTFields.TT_DATE;
import static org.ei.drishti.AllConstants.TTFields.TT_DOSE;
import static org.ei.drishti.domain.ServiceProvided.forHBTest;
import static org.ei.drishti.domain.ServiceProvided.forTTDose;
import static org.ei.drishti.domain.TimelineEvent.*;
import static org.ei.drishti.util.EasyMap.create;
import static org.ei.drishti.util.IntegerUtil.tryParse;
import static org.ei.drishti.util.Log.logWarn;

public class MotherService {
    public static final String MOTHER_ID = "motherId";
    private MotherRepository motherRepository;
    private AllBeneficiaries allBeneficiaries;
    private AllTimelineEvents allTimelines;
    private AllEligibleCouples allEligibleCouples;
    private ServiceProvidedService serviceProvidedService;

    public MotherService(MotherRepository motherRepository, AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples,
                         AllTimelineEvents allTimelineEvents, ServiceProvidedService serviceProvidedService) {
        this.motherRepository = motherRepository;
        this.allBeneficiaries = allBeneficiaries;
        this.allTimelines = allTimelineEvents;
        this.allEligibleCouples = allEligibleCouples;
        this.serviceProvidedService = serviceProvidedService;
    }

    public void registerANC(FormSubmission submission) {
        addTimelineEventsForMotherRegistration(submission);
    }

    public void registerOutOfAreaANC(FormSubmission submission) {
        addTimelineEventsForMotherRegistration(submission);
    }

    public void ancVisit(FormSubmission submission) {
        allTimelines.add(
                forANCCareProvided(
                        submission.entityId(),
                        submission.getFieldValue(ANC_VISIT_NUMBER),
                        submission.getFieldValue(ANC_VISIT_DATE),
                        create(BP_SYSTOLIC, submission.getFieldValue(BP_SYSTOLIC))
                                .put(BP_DIASTOLIC, submission.getFieldValue(BP_DIASTOLIC))
                                .put(TEMPERATURE, submission.getFieldValue(TEMPERATURE))
                                .put(WEIGHT, submission.getFieldValue(WEIGHT))
                                .map()));
        serviceProvidedService.add(
                ServiceProvided.forANCCareProvided(
                        submission.entityId(),
                        submission.getFieldValue(ANC_VISIT_NUMBER),
                        submission.getFieldValue(ANC_VISIT_DATE),
                        submission.getFieldValue(BP_SYSTOLIC),
                        submission.getFieldValue(BP_DIASTOLIC),
                        submission.getFieldValue(WEIGHT)));
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
        serviceProvidedService.add(forTTDose(submission.entityId(), submission.getFieldValue(TT_DOSE), submission.getFieldValue(TT_DATE)));
    }

    public void ifaTabletsGiven(FormSubmission submission) {
        String numberOfIFATabletsGiven = submission.getFieldValue(NUMBER_OF_IFA_TABLETS_GIVEN);
        if (tryParse(numberOfIFATabletsGiven, 0) > 0) {
            allTimelines.add(forIFATabletsGiven(submission.entityId(), numberOfIFATabletsGiven, submission.getFieldValue(IFA_TABLETS_DATE)));
            serviceProvidedService.add(ServiceProvided.forIFATabletsGiven(submission.entityId(), numberOfIFATabletsGiven, submission.getFieldValue(IFA_TABLETS_DATE)));
        }
    }

    private void addTimelineEventsForMotherRegistration(FormSubmission submission) {
        allTimelines.add(forStartOfPregnancy(submission.getFieldValue(MOTHER_ID), submission.getFieldValue(REFERENCE_DATE)));
        allTimelines.add(forStartOfPregnancyForEC(submission.entityId(), submission.getFieldValue(THAYI_CARD_NUMBER), submission.getFieldValue(REFERENCE_DATE)));
    }

    public void hbTest(FormSubmission submission) {
        serviceProvidedService.add(
                forHBTest(submission.entityId(),
                        submission.getFieldValue(HB_LEVEL),
                        submission.getFieldValue(HB_TEST_DATE)));
    }

    public void deliveryOutcome(FormSubmission submission) {
        if (BOOLEAN_FALSE.equals(submission.getFieldValue(DID_WOMAN_SURVIVE)) || BOOLEAN_FALSE.equals(submission.getFieldValue(DID_MOTHER_SURVIVE))) {
            allBeneficiaries.closeMother(submission.entityId());
            return;
        }

        allBeneficiaries.switchMotherToPNC(submission.entityId());
    }

    @Deprecated
    public void pncVisitHappened(Action action) {
        allTimelines.add(forMotherPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
        motherRepository.updateDetails(action.caseID(), action.details());

        String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
        if (numberOfIFATabletsProvided != null && tryParse(numberOfIFATabletsProvided, 0) > 0) {
            allTimelines.add(forIFATabletsGiven(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
        }
    }
}
