package org.ei.drishti.view.contract.mapper;

import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.view.contract.Beneficiary;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryMapper {
    private final AllEligibleCouples ecRepository;
    private final AllBeneficiaries motherRepository;

    public BeneficiaryMapper(AllEligibleCouples ecRepository, AllBeneficiaries motherRepository) {
        this.ecRepository = ecRepository;
        this.motherRepository = motherRepository;
    }

    public static List<Beneficiary> mapFromECs(List<EligibleCouple> ecs) {
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        for (EligibleCouple ec : ecs) {
            beneficiaries.add(new Beneficiary(ec.caseId(), ec.wifeName(), ec.husbandName(), "", ec.ecNumber(), ec.village(), ec.isHighPriority()));
        }
        return beneficiaries;
    }

    public List<Beneficiary> mapFromChildren(List<Child> children) {
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        for (Child child : children) {
            Mother mother = motherRepository.findMother(child.motherCaseId());
            EligibleCouple parents = ecRepository.findByCaseID(mother.ecCaseId());
            beneficiaries.add(new Beneficiary(child.caseId(), parents.wifeName(), parents.husbandName(), child.thaayiCardNumber(), parents.ecNumber(), parents.village(), child.isHighRisk()));
        }
        return beneficiaries;
    }

    public List<Beneficiary> mapFromMothers(List<Mother> mothers) {
        List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
        for (Mother mother : mothers) {
            EligibleCouple ec = ecRepository.findByCaseID(mother.ecCaseId());
            beneficiaries.add(new Beneficiary(mother.caseId(), ec.wifeName(), ec.husbandName(), mother.thaayiCardNumber(), ec.ecNumber(), ec.village(), mother.isHighRisk()));
        }
        return beneficiaries;
    }
}
