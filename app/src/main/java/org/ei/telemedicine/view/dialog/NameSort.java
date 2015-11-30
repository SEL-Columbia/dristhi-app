package org.ei.telemedicine.view.dialog;

import static org.ei.telemedicine.view.contract.SmartRegisterClient.NAME_COMPARATOR;

import java.util.Collections;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

public class NameSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_name_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, NAME_COMPARATOR);
        return allClients;
    }
}
