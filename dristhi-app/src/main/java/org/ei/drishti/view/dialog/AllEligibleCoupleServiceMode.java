package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.SmartRegisterClient;

public class AllEligibleCoupleServiceMode implements ServiceModeOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.couple_selection);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return true;
    }
}
