package org.ei.drishti.domain;

public enum AlertStatus {
    closed, open;

    public static AlertStatus from(String value) {
        return valueOf(value);
    }

    public String value() {
        return toString();
    }
}
