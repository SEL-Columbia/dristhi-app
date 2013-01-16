package org.ei.drishti.service;

import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.dto.Action;
import org.ei.drishti.repository.EligibleCoupleRepository;
import org.ei.drishti.util.Log;

public class EligibleCoupleService {
    private EligibleCoupleRepository repository;

    public EligibleCoupleService(EligibleCoupleRepository eligibleCoupleRepository) {
        this.repository = eligibleCoupleRepository;
    }

    public void handleAction(Action action) {
        if ("createEC".equals(action.type())) {
            repository.add(new EligibleCouple(action.caseID(), action.get("wife"), action.get("husband"), action.get("ecNumber"), action.get("village"), action.get("subcenter"), action.details()));
        } else if ("updateDetails".equals(action.type())) {
            repository.updateDetails(action.caseID(), action.details());
        } else if ("deleteEC".equals(action.type())) {
            repository.close(action.caseID());
        } else {
            Log.logWarn("Unknown type in eligible couple action: " + action);
        }
    }
}
