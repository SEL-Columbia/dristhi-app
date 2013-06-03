package org.ei.drishti.service;

import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.domain.form.FormSubmission;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.AllTimelineEvents;
import org.ei.drishti.repository.ChildRepository;
import org.ei.drishti.repository.MotherRepository;

import java.util.List;

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

    public void register(FormSubmission submission) {
        Mother mother = motherRepository.findById(submission.entityId());
        List<Child> children = childRepository.findByMotherCaseId(submission.entityId());

        for (Child child : children) {
            childRepository.update(child.setIsClosed(false).setThayiCardNumber(mother.thaayiCardNumber()));
        }
    }

    public void register(Action action) {
        Mother mother = motherRepository.findOpenCaseByCaseID(action.get("motherCaseId"));
        if (mother == null) {
            return;
        }
        allTimelines.add(forChildBirthInMotherProfile(action.get("motherCaseId"), action.get("dateOfBirth"), action.get("gender"), action.details()));
        allTimelines.add(forChildBirthInECProfile(mother.ecCaseId(), action.get("dateOfBirth"), action.get("gender"), action.details()));
        childRepository.add(new Child(action.caseID(), action.get("motherCaseId"), action.get("thaayiCardNumber"), action.get("dateOfBirth"), action.get("gender"), action.details()));
    }

    public void pncVisit(Action action) {
        allTimelines.add(forChildPNCVisit(action.caseID(), action.get("visitNumber"), action.get("visitDate"), action.details()));
        childRepository.updateDetails(action.caseID(), action.details());
    }

    public void updateImmunizations(Action action) {
        allTimelines.add(forChildImmunization(action.caseID(), action.get("immunizationsProvided"), action.get("immunizationsProvidedDate"), action.get("vitaminADose")));
        childRepository.updateDetails(action.caseID(), action.details());
    }

    public void delete(Action action) {
        childRepository.close(action.caseID());
    }
}
