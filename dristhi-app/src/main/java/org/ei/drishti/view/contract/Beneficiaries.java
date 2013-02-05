package org.ei.drishti.view.contract;

import java.util.List;

public class Beneficiaries<T> {
    private final List<T> priority;
    private final List<T> normal;

    public Beneficiaries(List<T> priority, List<T> normal) {
        this.priority = priority;
        this.normal = normal;
    }

    public List<T> priority() {
        return priority;
    }

    public List<T> normal() {
        return normal;
    }
}
