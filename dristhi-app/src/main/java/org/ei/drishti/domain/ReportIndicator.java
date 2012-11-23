package org.ei.drishti.domain;

public enum ReportIndicator {
    IUD("IUD Adoption"), CONDOM("Condom Usage");
    private String description;

    ReportIndicator(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
