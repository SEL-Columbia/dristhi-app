package org.ei.drishti.repository;

import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.util.Log;

import java.util.List;

public class AllEligibleCouples {
    private EligibleCoupleRepository repository;

    public AllEligibleCouples(EligibleCoupleRepository eligibleCoupleRepository) {
        this.repository = eligibleCoupleRepository;
    }

    public void handleAction(Action action) {
        if ("createEC".equals(action.type())) {
            repository.add(action);
        } else if ("deleteEC".equals(action.type())) {
            repository.delete(action);
        } else {
            Log.logWarn("Unknown type in eligible couple action: " + action);
        }
    }

    public List<EligibleCouple> fetchAll() {
        return repository.allEligibleCouples();
    }

    public void deleteAll() {
        repository.deleteAllEligibleCouples();
    }
}
