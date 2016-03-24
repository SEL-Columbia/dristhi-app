package org.ei.telemedicine.domain.mapper;

import static org.ei.telemedicine.domain.ServiceProvided.*;

public enum TTMapper {
    ttbooster(TT_BOOSTER_SERVICE_PROVIDED_NAME), tt1(TT_1_SERVICE_PROVIDED_NAME), tt2(TT_2_SERVICE_PROVIDED_NAME);

    private String value;

    TTMapper(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
