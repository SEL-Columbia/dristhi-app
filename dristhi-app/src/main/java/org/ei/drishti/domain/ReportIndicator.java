package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.view.contract.Beneficiary;

import java.util.List;

public enum ReportIndicator {
    IUD("IUD", "IUD Adoption") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    CONDOM("CONDOM", "Condom Usage") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    OCP("OCP", "Oral Pills") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    MALE_STERILIZATION("MALE_STERILIZATION", "Male Sterilization") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    FEMALE_STERILIZATION("FEMALE_STERILIZATION", "Female Sterilization") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    DPT("DPT", "DPT") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    HEP("HEP", "HEP") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    OPV("OPV", "OPV") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    MEASLES("MEASLES", "MEASLES") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    BCG("BCG", "BCG") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    EARLY_ANC_REGISTRATIONS("ANC<12", "Early ANC Registration") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    LATE_ANC_REGISTRATIONS("ANC>12", "Late ANC Registration") {
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    TT("TT","TT"){
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    CHILD_MORTALITY("MORT_C","Infant Mortality"){
        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    };

    private String value;

    private String description;

    ReportIndicator(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String description() {
        return description;
    }

    public String value() {
        return value;
    }

    public abstract List<Beneficiary> fetchCaseList(List<String> caseIds);

    private static List<Beneficiary> fetchECCaseList(List<String> caseIds) {
        return Context.getInstance().beneficiaryService().fetchFromEcCaseIds(caseIds);
    }

    private static List<Beneficiary> fetchMotherCaseList(List<String> caseIds) {
        return Context.getInstance().beneficiaryService().fetchFromMotherCaseIds(caseIds);
    }

    private static List<Beneficiary> fetchChildCaseList(List<String> caseIds) {
        return Context.getInstance().beneficiaryService().fetchFromChildCaseIds(caseIds);
    }

    public static ReportIndicator parseToReportIndicator(String indicator) {
        for (ReportIndicator reportIndicator : values()) {
            if(reportIndicator.value().equalsIgnoreCase(indicator))
                return reportIndicator;
        }
        throw new IllegalArgumentException("Could not find ReportIndicator for value: " + indicator);
    }
}
