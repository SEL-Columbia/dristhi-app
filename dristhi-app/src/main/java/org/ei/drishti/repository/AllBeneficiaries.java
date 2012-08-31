package org.ei.drishti.repository;

import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.TimelineEvent;
import org.ei.drishti.dto.Action;

import java.util.List;

public class AllBeneficiaries {
    private ChildRepository childRepository;
    private MotherRepository motherRepository;
    private AllTimelineEvents allTimelines;

    public AllBeneficiaries(MotherRepository motherRepository, ChildRepository childRepository, AllTimelineEvents allTimelineEvents) {
        this.childRepository = childRepository;
        this.motherRepository = motherRepository;
        this.allTimelines = allTimelineEvents;
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
        } else if (action.type().equals("updatePregnancyStatus")) {
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
