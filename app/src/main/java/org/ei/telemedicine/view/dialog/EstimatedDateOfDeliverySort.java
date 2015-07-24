package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.R;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.telemedicine.view.contract.ANCSmartRegisterClient.EDD_COMPARATOR;

public class EstimatedDateOfDeliverySort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_edd_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, EDD_COMPARATOR);
        return allClients;
    }
}
