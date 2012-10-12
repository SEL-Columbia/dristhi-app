package org.ei.drishti.repository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.dto.Action;

import java.util.List;

import static org.ei.drishti.domain.TimelineEvent.*;

public class AllBeneficiaries {
    private ChildRepository childRepository;
    private MotherRepository motherRepository;
    private AllTimelineEvents allTimelines;
    private EligibleCoupleRepository eligibleCoupleRepository;

    public AllBeneficiaries(MotherRepository motherRepository, ChildRepository childRepository, AllTimelineEvents allTimelineEvents, EligibleCoupleRepository eligibleCoupleRepository) {
        this.childRepository = childRepository;
        this.motherRepository = motherRepository;
        this.allTimelines = allTimelineEvents;
        this.eligibleCoupleRepository = eligibleCoupleRepository;
    }

    public void handleChildAction(Action action) {
        if (action.type().equals("register")) {
            Mother mother = motherRepository.find(action.get("motherCaseId"));
            if (mother == null) {
                return;
            }
            childRepository.addChild(new Child(action.caseID(), action.get("motherCaseId"), action.get("thaayiCardNumber"), action.get("dateOfBirth"), action.get("gender"), action.details()));
        } else if (action.type().equals("pncVisitHappened")) {
            allTimelines.add(forChildPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
            childRepository.updateDetails(action.caseID(), action.details());
        }
    }

    public void handleMotherAction(Action action) {
        if (action.type().equals("registerPregnancy")) {
            motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                    .withDetails(action.details()));
        } else if (action.type().equals("closeANC")) {
            // No action yet.
        } else if (action.type().equals("updateDetails")) {
            motherRepository.updateDetails(action.caseID(), action.details());
        } else if (action.type().equals("ancCareProvided")) {
            allTimelines.add(forANCCareProvided(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));

            String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
            if (numberOfIFATabletsProvided != null && Integer.parseInt(numberOfIFATabletsProvided) > 0) {
                allTimelines.add(forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
            }
            if (StringUtils.isNotBlank(action.get("ttDose"))) {
                allTimelines.add(forTTShotProvided(action.caseID(), action.get("ttDose"), action.get("visitDate")));
            }
        } else if (action.type().equals("registerOutOfAreaANC")) {
            eligibleCoupleRepository.add(new EligibleCouple(action.get("ecCaseId"), action.get("wife"), action.get("husband"), "", action.get("village"), action.get("subcenter"), action.details()).asOutOfArea());
            motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                    .withDetails(action.details()));
        } else if (action.type().equals("updateANCOutcome")) {
            motherRepository.switchToPNC(action.caseID());
            motherRepository.updateDetails(action.caseID(), action.details());
        } else if (action.type().equals("pncVisitHappened")) {
            allTimelines.add(forMotherPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
            motherRepository.updateDetails(action.caseID(), action.details());
        }
    }

    public List<Mother> allPNCs() {
        return motherRepository.allPNCs();
    }

    public List<Child> allChildren() {
        return childRepository.all();
    }

    public Mother findMother(String caseId) {
        return motherRepository.find(caseId);
    }

    public Child findChild(String caseId) {
        return childRepository.find(caseId);
    }

    public long ancCount() {
        return motherRepository.ancCount();
    }

    public long pncCount() {
        return motherRepository.pncCount();
    }

    public long childCount() {
        return childRepository.count();
    }

    public List<Pair<Mother, EligibleCouple>> allANCsWithEC() {
        return motherRepository.allANCsWithEC();
    }

    public Mother findMotherByECCaseId(String ecCaseId) {
        List<Mother> mothers = motherRepository.findAllCasesForEC(ecCaseId);
        if (mothers.isEmpty())
            return null;
        return mothers.get(0);
    }
}
