package org.ei.drishti.view.contract;

import java.util.List;

public class Children {
    private final List<Child> highRisk;
    private final List<Child> normalRisk;

    public Children(List<Child> highRisk, List<Child> normalRisk) {
        this.highRisk = highRisk;
        this.normalRisk = normalRisk;
    }

    public List<Child> highRisk() {
        return highRisk;
    }

    public List<Child> normalRisk() {
        return normalRisk;
    }
}
