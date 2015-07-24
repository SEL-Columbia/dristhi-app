package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.R;
import org.ei.telemedicine.Context;
import org.ei.telemedicine.view.contract.ECClient;
import org.ei.telemedicine.view.contract.ECSmartRegisterBaseClient;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

import java.util.Collections;

public class ECNumberSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_ec_number_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, ECSmartRegisterBaseClient.EC_NUMBER_COMPARATOR);
        return allClients;
    }
}
