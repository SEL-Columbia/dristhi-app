package org.ei.drishti.view.contract;

import org.ei.drishti.Context;
import org.ei.drishti.R;

public enum PNCVisitStatus {

    DONE(Context.getInstance().getStringResource(R.string.str_pnc_circle_status_done)),
    MISSED(Context.getInstance().getStringResource(R.string.str_pnc_circle_status_missed));

    private String circleType;

    PNCVisitStatus(String circleType) {
        this.circleType = circleType;
    }

    @Override
    public String toString() {
        return this.circleType;
    }

    public static PNCVisitStatus from(String value) {
        return valueOf(value.toUpperCase());
    }

}
