package org.ei.telemedicine.view.dialog;

import org.ei.drishti.R;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.telemedicine.view.contract.SmartRegisterClient.BPL_COMPARATOR;

public class BPLSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_bpl_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, BPL_COMPARATOR);
        return allClients;
    }
}
