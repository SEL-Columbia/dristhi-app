package org.ei.drishti.domain;

public enum Immunizations implements Displayable {
    bcg("BCG"), opv_0("OPV 0"), hepb_0("HepB 0");
    private String displayValue;

    Immunizations(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String displayValue() {
        return displayValue;
    }

    public static Immunizations value(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
