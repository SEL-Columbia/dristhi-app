package org.ei.drishti.domain;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ReportIndicator.*;

public enum ReportsCategory {
    FPS("Family Planning Services", asList(IUD, CONDOM, OCP, MALE_STERILIZATION, FEMALE_STERILIZATION)),

    ANC_SERVICES("ANC Services", asList(EARLY_ANC_REGISTRATIONS, ANC_REGISTRATIONS, SUB_TT, TT1, TT2, TTB, ANC4)),

    PREGNANCY_OUTCOMES("Pregnancy Outcomes", asList(LIVE_BIRTH, STILL_BIRTH, EARLY_ABORTIONS, LATE_ABORTIONS,
            SPONTANEOUS_ABORTION, DELIVERY, INSTITUTIONAL_DELIVERY, D_HOM, D_SC, D_PHC, D_CHC, D_SDH, D_DH, D_PRI)),

    PNC_SERVICES("PNC Services", asList(PNC3)),

    CHILD_SERVICES("Child Services", asList(DPT3_OR_OPV3, DPT_BOOSTER_OR_OPV_BOOSTER, DPT_BOOSTER2, HEP, OPV, MEASLES,
            BCG, LBW, BF_POST_BIRTH, WEIGHED_AT_BIRTH, VIT_A_1, VIT_A_1_FOR_FEMALE_CHILD, VIT_A_1_FOR_MALE_CHILD,
            VIT_A_2, VIT_A_2_FOR_FEMALE_CHILD, VIT_A_2_FOR_MALE_CHILD, CHILD_DIARRHEA, INFANT_MORTALITY,
            CHILD_MORTALITY_DUE_TO_DIARRHEA)),

    MORTALITY("Mortality", asList(ENM, NM, LNM, INFANT_MORTALITY, CHILD_MORTALITY, MMA, MMD, MMP, MM)),;

    private String description;
    private List<ReportIndicator> indicators;

    ReportsCategory(String description, List<ReportIndicator> indicators) {
        this.description = description;
        this.indicators = indicators;
    }

    public List<ReportIndicator> indicators() {
        return indicators;
    }

    public String description() {
        return description;
    }

    public String value() {
        return name();
    }
}
