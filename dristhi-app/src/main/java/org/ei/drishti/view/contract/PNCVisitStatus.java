package org.ei.drishti.view.contract;

import org.ei.drishti.Context;
import org.ei.drishti.R;

public enum PNCVisitStatus {

    DONE(Context.getInstance().getStringResource(R.string.str_pnc_circle_status_done)),
    MISSED(Context.getInstance().getStringResource(R.string.str_pnc_circle_status_missed));

    private String statusType;

    PNCVisitStatus(String statusType) {
        this.statusType = statusType;
    }

    @Override
    public String toString() {
        return this.statusType;
    }

    public static PNCVisitStatus from(String value) {
        return valueOf(value.toUpperCase());
    }

}
