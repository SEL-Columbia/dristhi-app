package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClient;

public class AllClientsFilter implements FilterOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.filter_by_all_label);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return true;
    }
}
