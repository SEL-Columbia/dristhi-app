package org.ei.drishti.repository;

import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.BeneficiaryStatus;

import java.util.List;

public class AllBeneficiaries {
    private BeneficiaryRepository repository;

    public AllBeneficiaries(BeneficiaryRepository repository) {
        this.repository = repository;
    }

    public void handleAction(Action action) {
        if (action.type().equals("createBeneficiary")) {
            repository.addMother(new Beneficiary(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), BeneficiaryStatus.from(action.get("status")), action.get("referenceDate")));
        } else if (action.type().equals("updateBeneficiary")) {
            repository.updateDeliveryStatus(action.caseID(), action.get("status"));
        } else {
            repository.addChild(action.caseID(), action.get("referenceDate"), action.get("motherCaseId"));
        }
    }

    public List<Beneficiary> findByECCaseId(String caseId) {
        return repository.findByECCaseId(caseId);
    }
}
