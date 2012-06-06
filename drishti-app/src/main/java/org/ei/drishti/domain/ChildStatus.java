package org.ei.drishti.domain;

public enum ChildStatus {
    UNBORN;

    public static ChildStatus from(String value) {
        return valueOf(value);
    }

    public String value() {
        return toString();
    }
}
