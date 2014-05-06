package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.drishti.view.contract.ChildSmartRegisterClient.CHILD_NAME_COMPARATOR;

public class ChildNameSort implements SortOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.sort_by_name_label);
    }

    @Override
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        Collections.sort(allClients, CHILD_NAME_COMPARATOR);
        return allClients;
    }
}
