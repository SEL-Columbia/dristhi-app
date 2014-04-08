package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;

public class AllEligibleCoupleServiceMode implements DialogOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.filter_by_all_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        return allClients;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return true;
    }
}
