package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.drishti.view.contract.SmartRegisterClients;

import java.util.Collections;

public class DateOfDeliverySort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_date_of_delivery_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, PNCSmartRegisterClient.DATE_OF_DELIVERY_COMPARATOR);
        return allClients;
    }
}
