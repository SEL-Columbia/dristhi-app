package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.view.contract.Beneficiary;

import java.util.List;

import static org.ei.drishti.view.contract.mapper.Mapper.mapFromEC;

public enum ReportIndicator {
    IUD("IUD Adoption") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return mapFromEC(Context.getInstance().allEligibleCouples().findByCaseIDs(caseIds));
        }
    }, CONDOM("Condom Usage") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return mapFromEC(Context.getInstance().allEligibleCouples().findByCaseIDs(caseIds));
        }
    };

    private String description;

    ReportIndicator(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public abstract List<Beneficiary> fetchCaseList(List<String> caseIds);
}
