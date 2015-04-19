package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClient;

public interface FilterOption extends DialogOption {
    public boolean filter(SmartRegisterClient client);
}
