package org.ei.drishti.domain;

public enum AlertFilterCriterionForType implements Criterion {
    ANC("ANC", "ANC"), All("All", ""), BCG("BCG", "BCG"), HEP("Hepatitis", "HEP"), OPV("OPV", "OPV");

    private String display;
    private String visitCodePrefix;

    AlertFilterCriterionForType(String display, String visitCodePrefix) {
        this.display = display;
        this.visitCodePrefix = visitCodePrefix;
    }

    public String toString() {
        return display;
    }

    public String visitCodePrefix() {
        return visitCodePrefix;
    }

    public String displayValue() {
        return display;
    }
}
