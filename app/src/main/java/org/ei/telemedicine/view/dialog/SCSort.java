package org.ei.telemedicine.view.dialog;

import static org.ei.telemedicine.view.contract.SmartRegisterClient.SC_COMPARATOR;

import java.util.Collections;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

public class SCSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_sc_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, SC_COMPARATOR);
        return allClients;
    }
}
