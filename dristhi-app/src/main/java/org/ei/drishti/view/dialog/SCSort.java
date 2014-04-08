package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.SmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.drishti.view.contract.SmartRegisterClient.SC_COMPARATOR;

public class SCSort implements DialogOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_sc_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, SC_COMPARATOR);
        return allClients;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return true;
    }
}
