package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.view.contract.Beneficiary;
import org.ei.drishti.view.contract.mapper.BeneficiaryMapper;

import java.util.List;

import static org.ei.drishti.view.contract.mapper.BeneficiaryMapper.mapFromEC;

public enum ReportIndicator {
    IUD("IUD Adoption") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    CONDOM("Condom Usage") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    OCP("Oral Pills") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    MALE_STERILIZATION("Male Sterilization") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    FEMALE_STERILIZATION("Female Sterilization") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    DPT("DPT") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    HEP("HEP") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    OPV("OPV") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    MEASLES("MEASLES") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    BCG("BCG") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    };

    private static List<Beneficiary> fetchChildCaseList(List<String> caseIds) {
        BeneficiaryMapper mapper = new BeneficiaryMapper(Context.getInstance().allEligibleCouples(), Context.getInstance().allBeneficiaries());
        return mapper.mapFromChild(Context.getInstance().allBeneficiaries().findAllChildrenByCaseIDs(caseIds));
    }

    private String description;

    ReportIndicator(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public abstract List<Beneficiary> fetchCaseList(List<String> caseIds);

    private static List<Beneficiary> fetchECCaseList(List<String> caseIds) {
        return mapFromEC(Context.getInstance().allEligibleCouples().findByCaseIDs(caseIds));
    }
}
