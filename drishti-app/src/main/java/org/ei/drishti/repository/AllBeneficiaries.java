package org.ei.drishti.repository;

import org.ei.drishti.domain.Mother;
import org.ei.drishti.dto.Action;

import java.util.List;

public class AllBeneficiaries {
    private ChildRepository childRepository;
    private MotherRepository motherRepository;

    public AllBeneficiaries(MotherRepository motherRepository, ChildRepository childRepository) {
        this.childRepository = childRepository;
        this.motherRepository = motherRepository;
    }

    public void handleAction(Action action) {
        if (action.type().equals("createBeneficiary")) {
            motherRepository.add(new Mother(action.caseID(), action.get("ecCaseId"), action.get("thaayiCardNumber"), action.get("referenceDate"))
                    .withExtraDetails(Boolean.parseBoolean(action.get("isHighRisk")), action.get("deliveryPlace")));
        } else if (action.type().equals("updateBeneficiary")) {
            childRepository.close(action.caseID());
        } else {
            Mother mother = motherRepository.find(action.get("motherCaseId"));
            motherRepository.switchToPNC(mother.caseId());
            childRepository.addChildForMother(mother, action.caseID(), action.get("referenceDate"), action.get("gender"));
        }
    }

    public List<Mother> allANCs() {
        return motherRepository.allANCs();
    }

    public Mother findMother(String caseId) {
        return motherRepository.find(caseId);
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
