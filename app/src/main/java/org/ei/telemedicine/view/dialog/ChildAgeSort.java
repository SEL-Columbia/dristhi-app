package org.ei.telemedicine.view.dialog;

import static org.ei.telemedicine.view.contract.SmartRegisterClient.AGE_COMPARATOR;

import java.util.Collections;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

public class ChildAgeSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_child_age);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, AGE_COMPARATOR);
        return allClients;
    }
}
