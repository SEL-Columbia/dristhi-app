package org.ei.drishti.domain;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ReportIndicator.*;

public enum ReportsCategory {
    FP("Family Planning", asList(IUD, CONDOM, OCP, MALE_STERILIZATION, FEMALE_STERILIZATION));

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
