package org.ei.telemedicine.service;

import android.util.Log;

import org.ei.telemedicine.AllConstants;
import org.ei.telemedicine.domain.Mother;
import org.ei.telemedicine.domain.ServiceProvided;
import org.ei.telemedicine.domain.TimelineEvent;
import org.ei.telemedicine.domain.form.FormSubmission;
import org.ei.telemedicine.repository.AllBeneficiaries;
import org.ei.telemedicine.repository.AllEligibleCouples;
import org.ei.telemedicine.repository.AllTimelineEvents;
import org.joda.time.LocalDate;

import static org.ei.telemedicine.AllConstants.ANCVisitFields.*;
import static org.ei.telemedicine.AllConstants.DeliveryPlanFields.*;
import static org.ei.telemedicine.AllConstants.BOOLEAN_FALSE;
import static org.ei.telemedicine.AllConstants.ANCCloseFields.CLOSE_REASON_FIELD_NAME;
import static org.ei.telemedicine.AllConstants.ANCCloseFields.DEATH_OF_WOMAN_FIELD_VALUE;
import static org.ei.telemedicine.AllConstants.ANCRegistrationFields.REGISTRATION_DATE;
import static org.ei.telemedicine.AllConstants.CommonFormFields.SUBMISSION_DATE;
import static org.ei.telemedicine.AllConstants.DeliveryOutcomeFields.DID_MOTHER_SURVIVE;
import static org.ei.telemedicine.AllConstants.DeliveryOutcomeFields.DID_WOMAN_SURVIVE;
import static org.ei.telemedicine.AllConstants.HbTestFields.HB_LEVEL;
import static org.ei.telemedicine.AllConstants.HbTestFields.HB_TEST_DATE;
import static org.ei.telemedicine.AllConstants.IFAFields.IFA_TABLETS_DATE;
import static org.ei.telemedicine.AllConstants.IFAFields.NUMBER_OF_IFA_TABLETS_GIVEN;
import static org.ei.telemedicine.AllConstants.PNCCloseFields.DEATH_OF_MOTHER_FIELD_VALUE;
import static org.ei.telemedicine.AllConstants.TTFields.TT_DATE;
import static org.ei.telemedicine.AllConstants.TTFields.TT_DOSE;
import static org.ei.telemedicine.domain.ServiceProvided.forHBTest;
import static org.ei.telemedicine.domain.ServiceProvided.forTTDose;
import static org.ei.telemedicine.domain.TimelineEvent.*;
import static org.ei.telemedicine.util.EasyMap.create;
import static org.ei.telemedicine.util.IntegerUtil.tryParse;
import static org.ei.telemedicine.util.Log.logWarn;

public class MotherService {
    public static final String MOTHER_ID = "motherId";
    private AllBeneficiaries allBeneficiaries;
    private AllTimelineEvents allTimelines;
    private AllEligibleCouples allEligibleCouples;
    private ServiceProvidedService serviceProvidedService;
    public static String submissionDate = "submissionDate";


    public MotherService(AllBeneficiaries allBeneficiaries, AllEligibleCouples allEligibleCouples,
                         AllTimelineEvents allTimelineEvents, ServiceProvidedService serviceProvidedService) {
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
        String risks = submission.getFieldValue("riskObservedDuringANC");
        String riskObserved = "";
        if (risks != null && risks.trim().length() != 0) {
            riskObserved = risks.trim().replace(" ", ",").replace("_", " ");
        }
        allTimelines.add(
                forANCCareProvided(
                        submission.entityId(),
                        submission.getFieldValue(ANC_VISIT_NUMBER),
                        submission.getFieldValue(ANC_VISIT_DATE),
                        create(BP_SYSTOLIC, submission.getFieldValue(BP_SYSTOLIC))
                                .put(BP_DIASTOLIC, submission.getFieldValue(BP_DIASTOLIC))
                                .put(TEMPERATURE, submission.getFieldValue(TEMPERATURE))
                                .put(HB_LEVEL, submission.getFieldValue(HB_LEVEL))
                                .put(BLOODGLUCOSEDATA, submission.getFieldValue(BLOODGLUCOSEDATA))
                                .put(ISCONSULTDOCTOR, submission.getFieldValue(ISCONSULTDOCTOR))
                                .put(FETALDATA, submission.getFieldValue(FETALDATA))
                                .put(POC_INFO, submission.getFieldValue(POC_INFO))
                                .put(ANM_POC, submission.getFieldValue(ANM_POC))
                                .put(RISKS, riskObserved)
                                .map()));

        if (submission.getFieldValue(POC_INFO) != null && !submission.getFieldValue(POC_INFO).equals("")) {
            Log.e("Submission Form for poc", "POc fot visit-------------------" + submission.getFieldValue(POC_INFO));
            TimelineEvent ancVisitPoc = forPOCGiven(submission.entityId(),
                    submission.getFieldValue(ANC_VISIT_DATE),
                    "ANCVISIT", "Plan of care for ANC Visit" + "-" + submission.getFieldValue(ANC_VISIT_NUMBER), create(POC_INFO, submission.getFieldValue(POC_INFO)).map());
            if (ancVisitPoc != null)
                allTimelines.add(ancVisitPoc);
        }

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
        close(submission.entityId(), submission.getFieldValue(CLOSE_REASON_FIELD_NAME));
    }


    public void close(String entityId, String reason) {
        Mother mother = allBeneficiaries.findMotherWithOpenStatus(entityId);
        if (mother == null) {
            logWarn("Tried to close non-existent mother. Entity ID: " + entityId);
            return;
        }

        allBeneficiaries.closeMother(entityId);
        if (DEATH_OF_WOMAN_FIELD_VALUE.equalsIgnoreCase(reason)
                || DEATH_OF_MOTHER_FIELD_VALUE.equalsIgnoreCase(reason)
                || AllConstants.ANCCloseFields.PERMANENT_RELOCATION_FIELD_VALUE.equalsIgnoreCase(reason)
                || AllConstants.PNCCloseFields.PERMANENT_RELOCATION_FIELD_VALUE.equalsIgnoreCase(reason)) {
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
        allTimelines.add(forStartOfPregnancy(submission.getFieldValue(MOTHER_ID), submission.getFieldValue(REGISTRATION_DATE), submission.getFieldValue(REFERENCE_DATE)));
        allTimelines.add(forStartOfPregnancyForEC(submission.entityId(), submission.getFieldValue(THAYI_CARD_NUMBER), submission.getFieldValue(REGISTRATION_DATE), submission.getFieldValue(REFERENCE_DATE)));
    }

    public void hbTest(FormSubmission submission) {
        serviceProvidedService.add(
                forHBTest(submission.entityId(),
                        submission.getFieldValue(HB_LEVEL),
                        submission.getFieldValue(HB_TEST_DATE)));
    }

    public void deliveryOutcome(FormSubmission submission) {
        Mother mother = allBeneficiaries.findMotherWithOpenStatus(submission.entityId());
        if (mother == null) {
            logWarn("Failed to handle delivery outcome for mother. Entity ID: " + submission.entityId());
            return;
        }
        if (BOOLEAN_FALSE.equals(submission.getFieldValue(DID_WOMAN_SURVIVE)) || BOOLEAN_FALSE.equals(submission.getFieldValue(DID_MOTHER_SURVIVE))) {
            allBeneficiaries.closeMother(submission.entityId());
            allEligibleCouples.close(mother.ecCaseId());
            return;
        }

        allBeneficiaries.switchMotherToPNC(submission.entityId());
    }

    public void pncVisitHappened(FormSubmission submission) {
        allTimelines.add(
                forMotherPNCVisit(
                        submission.entityId(),
                        submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DAY),
                        submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DATE),
                        submission.getFieldValue(AllConstants.PNCVisitFields.BP_SYSTOLIC),
                        submission.getFieldValue(AllConstants.PNCVisitFields.BP_DIASTOLIC),
                        submission.getFieldValue(AllConstants.PNCVisitFields.TEMPERATURE),
                        submission.getFieldValue(AllConstants.PNCVisitFields.HB_LEVEL),
                        submission.getFieldValue(AllConstants.PNCVisitFields.BLOODGLUCOSEDATA), submission.getFieldValue(AllConstants.PNCVisitFields.ISCONSULTDOCTOR), submission.getFieldValue(AllConstants.ANCVisitFields.ANM_POC)));
        Log.e("Submission Form for poc", "POc fot visit" + submission.instance() + "");
        if (submission.getFieldValue(POC_INFO) != null && !submission.getFieldValue(POC_INFO).equals("")) {
            TimelineEvent pncVisitPoc = forPOCGiven(
                    submission.entityId(),
                    submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DATE),
                    "PNCVISIT", "Plan of care for PNC Visit" + "-" + submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DAY), create(POC_INFO, submission.getFieldValue(POC_INFO)).map());
            if (pncVisitPoc != null)
                allTimelines.add(pncVisitPoc);
        }
        serviceProvidedService.add(
                ServiceProvided.forMotherPNCVisit(
                        submission.entityId(),
                        submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DAY),
                        submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DATE)));

        String numberOfIFATabletsGiven = submission.getFieldValue(AllConstants.PNCVisitFields.NUMBER_OF_IFA_TABLETS_GIVEN);
        if (tryParse(numberOfIFATabletsGiven, 0) > 0) {
            allTimelines.add(forIFATabletsGiven(submission.entityId(), numberOfIFATabletsGiven, submission.getFieldValue(SUBMISSION_DATE)));
        }
    }

    public void deliveryPlan(FormSubmission submission) {
        allTimelines.add(
                forDeliveryPlan(
                        submission.entityId(),
                        submission.getFieldValue(DELIVERY_FACILITY_NAME),
                        submission.getFieldValue(TRANSPORTATION_PLAN),
                        submission.getFieldValue(BIRTH_COMPANION),
                        submission.getFieldValue(ASHA_PHONE_NUMBER),
                        submission.getFieldValue(PHONE_NUMBER),
                        submission.getFieldValue(REVIEWED_HRP_STATUS),
                        submission.getFieldValue(SUBMISSION_DATE)));

        serviceProvidedService.add(
                ServiceProvided.forDeliveryPlan(
                        submission.entityId(),
                        submission.getFieldValue(DELIVERY_FACILITY_NAME),
                        submission.getFieldValue(TRANSPORTATION_PLAN),
                        submission.getFieldValue(BIRTH_COMPANION),
                        submission.getFieldValue(ASHA_PHONE_NUMBER),
                        submission.getFieldValue(PHONE_NUMBER),
                        submission.getFieldValue(REVIEWED_HRP_STATUS),
                        submission.getFieldValue(SUBMISSION_DATE)
                ));
    }
}
