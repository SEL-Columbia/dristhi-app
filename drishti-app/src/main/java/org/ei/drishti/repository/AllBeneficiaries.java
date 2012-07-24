package org.ei.drishti.repository;

import org.ei.drishti.dto.Action;
import org.ei.drishti.domain.Beneficiary;

import java.util.List;

public class AllBeneficiaries {
    private BeneficiaryRepository repository;

    public AllBeneficiaries(BeneficiaryRepository repository) {
        this.repository = repository;
    }

    public void handleAction(Action action) {
        if (action.type().equals("createBeneficiary")) {
            repository.addMother(new Beneficiary(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate")));
        } else if (action.type().equals("updateBeneficiary")) {
            repository.close(action.caseID());
        } else {
            repository.addChild(action.caseID(), action.get("referenceDate"), action.get("motherCaseId"), action.get("gender"));
        }
    }

    public List<Beneficiary> findByECCaseId(String caseId) {
        return repository.findByECCaseId(caseId);
    }
}
