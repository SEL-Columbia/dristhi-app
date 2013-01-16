package org.ei.drishti.service;

import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.ChildRepository;
import org.ei.drishti.repository.MotherRepository;

import static org.ei.drishti.domain.TimelineEvent.*;

public class ChildService {
    private ChildRepository childRepository;
    private MotherRepository motherRepository;
    private AllTimelineEvents allTimelines;

    public ChildService(MotherRepository motherRepository, ChildRepository childRepository, AllTimelineEvents allTimelineEvents) {
        this.childRepository = childRepository;
        this.motherRepository = motherRepository;
        this.allTimelines = allTimelineEvents;
    }

    public void handleAction(Action action) {
        if (action.type().equals("register")) {
            Mother mother = motherRepository.find(action.get("motherCaseId"));
            if (mother == null) {
                return;
            }
            allTimelines.add(forChildBirthInMotherProfile(action.get("motherCaseId"), action.get("dateOfBirth"), action.get("gender"), action.details()));
            allTimelines.add(forChildBirthInECProfile(mother.ecCaseId(), action.get("dateOfBirth"), action.get("gender"), action.details()));
            childRepository.add(new Child(action.caseID(), action.get("motherCaseId"), action.get("thaayiCardNumber"), action.get("dateOfBirth"), action.get("gender"), action.details()));
        } else if (action.type().equals("pncVisitHappened")) {
            allTimelines.add(forChildPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
            childRepository.updateDetails(action.caseID(), action.details());
        } else if (action.type().equals("updateImmunizations")) {
            allTimelines.add(forChildImmunization(action.caseID(), action.get("immunizationsProvided"), action.get("immunizationsProvidedDate"), action.get("vitaminADose")));
            childRepository.updateDetails(action.caseID(), action.details());
        } else if (action.type().equals("deleteChild")) {
            childRepository.close(action.caseID());
        }
    }
}
