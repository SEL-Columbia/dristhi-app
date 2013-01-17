package org.ei.drishti.service;

import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.EligibleCoupleRepository;

public class EligibleCoupleService {
    private EligibleCoupleRepository repository;

    public EligibleCoupleService(EligibleCoupleRepository eligibleCoupleRepository) {
        this.repository = eligibleCoupleRepository;
    }

    public void register(Action action) {
        repository.add(new EligibleCouple(action.caseID(), action.get("wife"), action.get("husband"), action.get("ecNumber"),
                action.get("village"), action.get("subcenter"), action.details()));
    }

    public void updateDetails(Action action) {
        repository.updateDetails(action.caseID(), action.details());
    }

    public void delete(Action action) {
        repository.close(action.caseID());
    }
}
