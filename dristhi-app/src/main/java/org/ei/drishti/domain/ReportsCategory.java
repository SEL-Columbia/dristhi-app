package org.ei.drishti.domain;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ReportIndicator.*;

public enum ReportsCategory {
    FPS("Family Planning Services", asList(IUD, CONDOM, OCP, MALE_STERILIZATION, FEMALE_STERILIZATION)),
    ANC_SERVICES("ANC Services", asList(EARLY_ANC_REGISTRATIONS, ANC_REGISTRATIONS, SUB_TT, TT1, TT2, TTB, ANC4)),
    PREGNANCY_OUTCOMES("Pregnancy Outcomes", asList(LIVE_BIRTH, STILL_BIRTH, EARLY_ABORTIONS, LATE_ABORTIONS, SPONTANEOUS_ABORTION, DELIVERY, INSTITUTIONAL_DELIVERY)),
    CHILD_SERVICES("Child Services", asList(DPT1, DPT2, DPT2, DPT3, DPT_BOOSTER2, HEP, OPV, MEASLES, BCG, LBW, BF_POST_BIRTH, WEIGHED_AT_BIRTH, VIT_A_1, VIT_A_2)),
    MORTALITY("Mortality", asList(ENM, NM, LNM, INFANT_MORTALITY, CHILD_MORTALITY, MMA, MMD, MMP, MM));

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
