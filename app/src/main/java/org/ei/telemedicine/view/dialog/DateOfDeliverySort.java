package org.ei.telemedicine.view.dialog;

import java.util.Collections;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClients;
import org.ei.telemedicine.view.contract.pnc.PNCSmartRegisterClient;

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
