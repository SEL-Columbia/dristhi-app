package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.R;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.telemedicine.view.contract.SmartRegisterClient.HIGH_PRIORITY_COMPARATOR;

public class HighPrioritySort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_high_priority_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, HIGH_PRIORITY_COMPARATOR);
        return allClients;
    }
}
