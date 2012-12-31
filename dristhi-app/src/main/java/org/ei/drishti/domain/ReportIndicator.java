package org.ei.drishti.domain;

import org.ei.drishti.Context;
import org.ei.drishti.view.contract.Beneficiary;

import java.util.List;

import static org.ei.drishti.view.controller.ProfileNavigationController.*;

public enum ReportIndicator {
    IUD("IUD", "IUD Adoption") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigateToECProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    CONDOM("CONDOM", "Condom Usage") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigateToECProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    OCP("OCP", "Oral Pills") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigateToECProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    MALE_STERILIZATION("MALE_STERILIZATION", "Male Sterilization") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigateToECProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    FEMALE_STERILIZATION("FEMALE_STERILIZATION", "Female Sterilization") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigateToECProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchECCaseList(caseIds);
        }
    },
    DPT1("DPT_1", "DPT 1") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    DPT2("DPT_2", "DPT 2") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    DPT3("DPT_3", "DPT 3") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    DPT_BOOSTER2("DPT_BOOSTER_2", "DPT Booster 2") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    HEP("HEP", "HEP") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    OPV("OPV", "OPV") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }


        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    MEASLES("MEASLES", "MEASLES") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    WEIGHED_AT_BIRTH("WEIGHED_AT_BIRTH", "Number of infants weighed at birth") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    BF_POST_BIRTH("BF_POST_BIRTH", "Exclusively BF within 1 hr of birth") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    BCG("BCG", "BCG") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    EARLY_ANC_REGISTRATIONS("ANC<12", "Early ANC Registration") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    ANC_REGISTRATIONS("ANC", "Late ANC Registration") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    TT("TT", "TT") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    INFANT_MORTALITY("IM", "Infant Mortality") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    ENM("ENM", "Early Neonatal mortality") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    NM("NM", "Neonatal mortality") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    LNM("LNM", "29 days to 1 year of birth") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    CHILD_MORTALITY("UFM", "Under 5 mortality") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    LIVE_BIRTH("LIVE_BIRTH", "Live Birth") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    STILL_BIRTH("STILL_BIRTH", "Still Birth") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    LBW("LBW", "Low Birth Weight") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToChildProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchChildCaseList(caseIds);
        }
    },
    EARLY_ABORTIONS("MTP<12", "Abortions before 12 weeks") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    LATE_ABORTIONS("MTP>12", "Abortions after 12 weeks") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    SPONTANEOUS_ABORTION("SPONTANEOUS_ABORTION", "Spontaneous abortions") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToANCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    DELIVERY("DELIVERY", "Total Deliveries") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToPNCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    MMA("MMA", "Mother mortality (during ANC)") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToPNCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    MMD("MMD", "Mother mortality (during Delivery") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToPNCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
        }
    },
    INSTITUTIONAL_DELIVERY("INSTITUTIONAL_DELIVERY", "Institutional Deliveries") {
        @Override
        public void startCaseDetailActivity(android.content.Context context, String caseId) {
            navigationToPNCProfile(context, caseId);
        }

        @Override
        public List<Beneficiary> fetchCaseList(List<String> caseIds) {
            return fetchMotherCaseList(caseIds);
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

    public abstract void startCaseDetailActivity(android.content.Context context, String caseId);

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
            if (reportIndicator.value().equalsIgnoreCase(indicator))
                return reportIndicator;
        }
        throw new IllegalArgumentException("Could not find ReportIndicator for value: " + indicator);
    }
}
