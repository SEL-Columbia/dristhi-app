package org.ei.drishti.repository;

import org.ei.drishti.dto.Action;
import org.ei.drishti.domain.Beneficiary;

import java.util.List;

public class AllBeneficiaries {
    private BeneficiaryRepository beneficiaryRepository;
    private MotherRepository motherRepository;

    public AllBeneficiaries(BeneficiaryRepository beneficiaryRepository, MotherRepository motherRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.motherRepository = motherRepository;
    }

    public void handleAction(Action action) {
        if (action.type().equals("createBeneficiary")) {
            motherRepository.add(new Beneficiary(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate")));
        } else if (action.type().equals("updateBeneficiary")) {
            beneficiaryRepository.close(action.caseID());
        } else {
            beneficiaryRepository.addChildForMother(motherRepository.find(action.get("motherCaseId")), action.caseID(), action.get("referenceDate"), action.get("gender"));
        }
    }

    public List<Beneficiary> allANCs() {
        return motherRepository.allANCs();
    }

    public Beneficiary findMother(String caseId) {
        return motherRepository.find(caseId);
    }

    public long ancCount() {
        return motherRepository.ancCount();
    }

    public long pncCount() {
        return motherRepository.pncCount();
    }

    public long childCount() {
        return beneficiaryRepository.childCount();
    }
}
