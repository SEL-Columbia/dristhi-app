package org.ei.drishti.view.contract;

import java.util.List;

public class ANCs {
    private final List<ANC> priority;
    private final List<ANC> normal;

    public ANCs(List<ANC> priorityANCs, List<ANC> normalANCs) {
        this.priority = priorityANCs;
        this.normal = normalANCs;
    }

    public List<ANC> priority() {
        return priority;
    }

    public List<ANC> normal() {
        return normal;
    }
}
