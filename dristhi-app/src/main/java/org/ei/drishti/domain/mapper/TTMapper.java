package org.ei.drishti.domain.mapper;

public enum TTMapper {
    ttbooster("TT Booster"), tt1("TT 1"), tt2("TT 2");

    private String value;

    TTMapper(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
