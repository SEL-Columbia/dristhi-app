package org.ei.drishti.view.contract;

import java.util.List;

public class ECs {
    private final List<EC> priority;
    private final List<EC> normal;

    public ECs(List<EC> priority, List<EC> normal) {
        this.priority = priority;
        this.normal = normal;
    }

    public List<EC> priority() {
        return priority;
    }

    public List<EC> normal() {
        return normal;
    }
}
