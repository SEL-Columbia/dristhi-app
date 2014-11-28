package org.ei.drishti.view.contract.pnc;

import org.ei.drishti.Context;
import org.ei.drishti.R;

public enum PNCStatusColor {
    YELLOW(Context.getInstance().getStringResource(R.string.str_pnc_circle_color_yellow)),
    GREEN(Context.getInstance().getStringResource(R.string.str_pnc_circle_color_green)),
    RED(Context.getInstance().getStringResource(R.string.str_pnc_circle_color_red));

    private String statusColor;

    PNCStatusColor(String circleType) {
        this.statusColor = circleType;
    }

    @Override
    public String toString() {
        return this.statusColor;
    }

    public static PNCStatusColor from(String value) {
        return valueOf(value.toUpperCase());
    }
}
