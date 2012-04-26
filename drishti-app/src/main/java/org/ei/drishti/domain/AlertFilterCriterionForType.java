package org.ei.drishti.domain;

public enum AlertFilterCriterionForType {
    ANC("ANC", "ANC"), All("All", ""), BCG("BCG", "BCG"), HEP("Hepatitis", "HEP"), OPV("OPV", "OPV");

    private String display;
    private String visitCodePrefix;

    AlertFilterCriterionForType(String display, String visitCodePrefix) {
        this.display = display;
        this.visitCodePrefix = visitCodePrefix;
    }

    public String visitCodePrefix() {
        return visitCodePrefix;
    }

    public String toString() {
        return display;
    }
}
