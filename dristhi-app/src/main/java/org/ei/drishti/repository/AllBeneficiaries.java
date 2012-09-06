package org.ei.drishti.repository;

import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.Action;

import java.util.List;

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
            motherRepository.switchToPNC(mother.caseId());
            childRepository.addChildForMother(mother, action.caseID(), action.get("referenceDate"), action.get("gender"));
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
            allTimelines.add(TimelineEvent.forANCCareProvided(action.caseID(), action.get("visitNumber"), action.get("visitDate")));

            String numberOfIFATabletsProvided = action.get("numberOfIFATabletsProvided");
            if (numberOfIFATabletsProvided != null && Integer.parseInt(numberOfIFATabletsProvided) > 0) {
                allTimelines.add(TimelineEvent.forIFATabletsProvided(action.caseID(), numberOfIFATabletsProvided, action.get("visitDate")));
            }
        }
        else if(action.type().equals("registerOutOfAreaANC")){
            eligibleCoupleRepository.add(new EligibleCouple(action.get("ecCaseId"), action.get("wife"), action.get("husband"), "", action.get("village"), action.get("subcenter"), action.details()).asOutOfArea());
            motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                    .withDetails(action.details()));
        }
    }

    public List<Mother> allANCs() {
        return motherRepository.allANCs();
    }

    public List<Mother> allPNCs() {
        return motherRepository.allPNCs();
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
        return childRepository.childCount();
    }
}
