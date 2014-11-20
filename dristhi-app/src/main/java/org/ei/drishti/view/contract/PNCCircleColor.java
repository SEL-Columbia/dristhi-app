package org.ei.drishti.view.contract;

import org.ei.drishti.Context;
import org.ei.drishti.R;

public enum PNCCircleColor {

    YELLOW(Context.getInstance().getStringResource(R.string.str_pnc_circle_color_yellow)) {
        @Override
        public int colorResourceID() {
            return R.color.pnc_circle_yellow;
        }
    },
    GREEN(Context.getInstance().getStringResource(R.string.str_pnc_circle_color_green)) {
        @Override
        public int colorResourceID() {
            return R.color.pnc_circle_green;
        }
    },
    RED(Context.getInstance().getStringResource(R.string.str_pnc_circle_color_red)) {
        @Override
        public int colorResourceID() {
            return R.color.pnc_circle_red;
        }
    };

    private String circleColor;

    PNCCircleColor(String circleColor) {
        this.circleColor = circleColor;
    }

    @Override
    public String toString() {
        return this.circleColor;
    }

    public abstract int colorResourceID();

    public static PNCCircleColor from(String value) {
        return valueOf(value.toUpperCase());
    }

}
