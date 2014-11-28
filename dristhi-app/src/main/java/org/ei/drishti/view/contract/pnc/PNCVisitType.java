package org.ei.drishti.view.contract.pnc;

import org.ei.drishti.Context;
import org.ei.drishti.R;

public enum PNCVisitType {

    EXPECTED(Context.getInstance().getStringResource(R.string.str_pnc_circle_type_expected)),
    ACTUAL(Context.getInstance().getStringResource(R.string.str_pnc_circle_type_actual));

    private String circleType;

    PNCVisitType(String circleType) {
        this.circleType = circleType;
    }

    @Override
    public String toString() {
        return this.circleType;
    }

    public static PNCVisitType from(String value) {
        return valueOf(value.toUpperCase());
    }

}
