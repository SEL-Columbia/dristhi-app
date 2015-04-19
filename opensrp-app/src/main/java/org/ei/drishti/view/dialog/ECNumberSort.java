package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECSmartRegisterBaseClient;
import org.ei.drishti.view.contract.SmartRegisterClients;

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
