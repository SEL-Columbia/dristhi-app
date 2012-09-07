package org.ei.drishti.view.contract;

import java.util.List;

public class PNCs {
    private final List<PNC> highRisk;
    private final List<PNC> normalRisk;

    public PNCs(List<PNC> highRisk, List<PNC> normalRisk) {
        this.highRisk = highRisk;
        this.normalRisk = normalRisk;
    }

    public List<PNC> highRisk() {
        return highRisk;
    }

    public List<PNC> normalRisk() {
        return normalRisk;
    }
}
