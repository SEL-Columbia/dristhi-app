package org.ei.drishti.view.contract;

import java.util.List;

public class ANCs {
    private final List<ANC> highRisk;
    private final List<ANC> normalRisk;

    public ANCs(List<ANC> highRiskAncs, List<ANC> normalRiskAncs) {
        this.highRisk = highRiskAncs;
        this.normalRisk = normalRiskAncs;
    }

    public List<ANC> highRisk() {
        return highRisk;
    }

    public List<ANC> normalRisk() {
        return normalRisk;
    }
}
