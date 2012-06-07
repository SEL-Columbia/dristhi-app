package org.ei.drishti.repository;

import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Beneficiary;

import java.util.List;

public class AllBeneficiaries {
    private BeneficiaryRepository repository;

    public AllBeneficiaries(BeneficiaryRepository repository) {
        this.repository = repository;
    }

    public void handleAction(Action action) {
        if (action.type().equals("createBeneficiary")) {
            repository.addMother(action);
        } else if (action.type().equals("updateBeneficiary")) {
            repository.updateDeliveryStatus(action);
        } else {
            repository.addChild(action);
        }
    }

    public List<Beneficiary> findByECCaseId(String caseId) {
        return repository.findByECCaseId(caseId);
    }
}
