package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;

public class HRPSort extends HighRiskSort {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_high_risk_pregnancy_label);
    }

}
