package org.ei.telemedicine.view.dialog;

import static org.ei.telemedicine.view.contract.SmartRegisterClient.HR_COMPARATOR;

import java.util.Collections;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClients;

public class ChildHighRiskSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_child_hr);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, HR_COMPARATOR);
        return allClients;
    }
}
