package org.ei.drishti.service;

import org.ei.drishti.AllConstants;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.ServiceProvided;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.ChildRepository;
import org.ei.drishti.repository.MotherRepository;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.AllConstants.SPACE;
import static org.ei.drishti.domain.TimelineEvent.*;

public class ChildService {
    private AllBeneficiaries allBeneficiaries;
    private ChildRepository childRepository;
    private MotherRepository motherRepository;
    private AllTimelineEvents allTimelines;
    private ServiceProvidedService serviceProvidedService;

    public ChildService(AllBeneficiaries allBeneficiaries, MotherRepository motherRepository, ChildRepository childRepository,
                        AllTimelineEvents allTimelineEvents, ServiceProvidedService serviceProvidedService) {
        this.allBeneficiaries = allBeneficiaries;
        this.childRepository = childRepository;
        this.motherRepository = motherRepository;
        this.allTimelines = allTimelineEvents;
        this.serviceProvidedService = serviceProvidedService;
    }

    public void register(FormSubmission submission) {
        Mother mother = motherRepository.findById(submission.entityId());
        List<Child> children = childRepository.findByMotherCaseId(mother.caseId());

        for (Child child : children) {
            childRepository.update(child.setIsClosed(false).setThayiCardNumber(mother.thayiCardNumber()).setDateOfBirth(mother.referenceDate()));
            allTimelines.add(forChildBirthInChildProfile(child.caseId(), mother.referenceDate(),
                    child.getDetail(AllConstants.ChildRegistrationFields.WEIGHT), child.getDetail(AllConstants.ChildRegistrationFields.IMMUNIZATIONS_GIVEN)));
            allTimelines.add(forChildBirthInMotherProfile(mother.caseId(), mother.referenceDate(), child.gender(), mother.referenceDate(), mother.getDetail(AllConstants.ChildRegistrationFields.DELIVERY_PLACE)));
            allTimelines.add(forChildBirthInECProfile(mother.ecCaseId(), mother.referenceDate(), child.gender(), mother.referenceDate()));
            String immunizationsGiven = child.getDetail(AllConstants.ChildRegistrationFields.IMMUNIZATIONS_GIVEN);
            for (String immunization : immunizationsGiven.split(SPACE)) {
                serviceProvidedService.add(ServiceProvided.forChildImmunization(child.caseId(), immunization, mother.referenceDate()));
            }
        }
    }

    public void registerForEC(FormSubmission submission) {
        Map<String, String> immunizationDateFieldMap = createImmunizationDateFieldMap();

        allTimelines.add(forChildBirthInChildProfile(
                submission.getFieldValue(AllConstants.ChildRegistrationFields.CHILD_ID),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.DATE_OF_BIRTH),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.WEIGHT),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.IMMUNIZATIONS_GIVEN)));
        allTimelines.add(forChildBirthInMotherProfile(
                submission.getFieldValue(AllConstants.ChildRegistrationFields.MOTHER_ID),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.DATE_OF_BIRTH),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.GENDER),
                null, null));
        allTimelines.add(forChildBirthInECProfile(
                submission.entityId(),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.DATE_OF_BIRTH),
                submission.getFieldValue(AllConstants.ChildRegistrationFields.GENDER), null));
        String immunizationsGiven = submission.getFieldValue(AllConstants.ChildRegistrationFields.IMMUNIZATIONS_GIVEN);
        for (String immunization : immunizationsGiven.split(SPACE)) {
            serviceProvidedService.add(ServiceProvided.forChildImmunization(submission.entityId(), immunization, submission.getFieldValue(immunizationDateFieldMap.get(immunization))));
        }
    }

    private Map<String, String> createImmunizationDateFieldMap() {
        Map<String, String> immunizationDateFieldsMap = new HashMap<String, String>();
        immunizationDateFieldsMap.put("bcg", "bcgDate");
        immunizationDateFieldsMap.put("opv_0", "opv0Date");
        immunizationDateFieldsMap.put("opv_1", "opv1Date");
        immunizationDateFieldsMap.put("opv_2", "opv2Date");
        immunizationDateFieldsMap.put("opv_3", "opv3Date");
        immunizationDateFieldsMap.put("hepb_0", "hepb0Date");
        immunizationDateFieldsMap.put("pentavalent_1", "pentavalent1Date");
        immunizationDateFieldsMap.put("pentavalent_2", "pentavalent2Date");
        immunizationDateFieldsMap.put("pentavalent_3", "pentavalent3Date");
        immunizationDateFieldsMap.put("measles", "measlesDate");
        immunizationDateFieldsMap.put("mmr", "mmrDate");
        immunizationDateFieldsMap.put("dptbooster_1", "dptbooster1Date");
        immunizationDateFieldsMap.put("dptbooster_2", "dptbooster2Date");
        immunizationDateFieldsMap.put("opvbooster", "opvboosterDate");
        immunizationDateFieldsMap.put("je", "jeDate");
        immunizationDateFieldsMap.put("measlesbooster", "measlesboosterDate");

        return immunizationDateFieldsMap;
    }

    public void pncRegistrationOA(FormSubmission submission) {
        Mother mother = motherRepository.findAllCasesForEC(submission.entityId()).get(0);
        List<Child> children = childRepository.findByMotherCaseId(mother.caseId());

        for (Child child : children) {
            childRepository.update(child.setIsClosed(false).setThayiCardNumber(mother.thayiCardNumber()).setDateOfBirth(mother.referenceDate()));
            allTimelines.add(forChildBirthInChildProfile(child.caseId(), mother.referenceDate(),
                    child.getDetail(AllConstants.PNCRegistrationOAFields.WEIGHT), child.getDetail(AllConstants.PNCRegistrationOAFields.IMMUNIZATIONS_GIVEN)));
            allTimelines.add(forChildBirthInMotherProfile(mother.caseId(), mother.referenceDate(), child.gender(),
                    mother.referenceDate(), submission.getFieldValue(AllConstants.PNCRegistrationOAFields.DELIVERY_PLACE)));
            allTimelines.add(forChildBirthInECProfile(mother.ecCaseId(), mother.referenceDate(), child.gender(),
                    mother.referenceDate()));
            String immunizationsGiven = child.getDetail(AllConstants.PNCRegistrationOAFields.IMMUNIZATIONS_GIVEN);
            for (String immunization : immunizationsGiven.split(SPACE)) {
                serviceProvidedService.add(ServiceProvided.forChildImmunization(child.caseId(), immunization, mother.referenceDate()));
            }
        }
    }

    public void updateImmunizations(FormSubmission submission) {
        String immunizationDate = submission.getFieldValue(AllConstants.ChildImmunizationsFields.IMMUNIZATION_DATE);
        List<String> immunizationsGivenList = getListSplitBySpace(submission, AllConstants.ChildImmunizationsFields.IMMUNIZATIONS_GIVEN);
        List<String> previousImmunizationsList = getListSplitBySpace(submission, AllConstants.ChildImmunizationsFields.PREVIOUS_IMMUNIZATIONS_GIVEN);
        immunizationsGivenList.removeAll(previousImmunizationsList);
        for (String immunization : immunizationsGivenList) {
            allTimelines.add(forChildImmunization(submission.entityId(), immunization, immunizationDate));
            serviceProvidedService.add(ServiceProvided.forChildImmunization(submission.entityId(), immunization, immunizationDate));
        }
    }

    private ArrayList<String> getListSplitBySpace(FormSubmission submission, String fieldName) {
        return new ArrayList<String>(Arrays.asList(submission.getFieldValue(fieldName).split(SPACE)));
    }

    public void pncVisitHappened(FormSubmission submission) {
        List<Child> children = childRepository.findByMotherCaseId(submission.entityId());
        for (Child child : children) {
            allTimelines.add(forChildPNCVisit(child.caseId(), submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DAY),
                    submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DATE), child.getDetail(AllConstants.PNCVisitFields.WEIGHT), child.getDetail(AllConstants.PNCVisitFields.TEMPERATURE)));
            serviceProvidedService.add(
                    ServiceProvided.forChildPNCVisit(
                            child.caseId(),
                            submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DAY),
                            submission.getFieldValue(AllConstants.PNCVisitFields.PNC_VISIT_DATE)));
        }
    }

    public void close(FormSubmission submission) {
        allBeneficiaries.closeChild(submission.entityId());
    }

    public void updatePhotoPath(String entityId, String imagePath) {
        childRepository.updatePhotoPath(entityId, imagePath);
    }
}
