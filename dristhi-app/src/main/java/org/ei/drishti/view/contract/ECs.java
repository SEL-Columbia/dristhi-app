package org.ei.drishti.view.contract;

import java.util.List;

public class ECs {
    private final List<EC> highPriority;
    private final List<EC> normalPriority;

    public ECs(List<EC> highPriority, List<EC> normalPriority) {
        this.highPriority = highPriority;
        this.normalPriority = normalPriority;
    }

    public List<EC> highPriority() {
        return highPriority;
    }

    public List<EC> normalPriority() {
        return normalPriority;
    }
}
