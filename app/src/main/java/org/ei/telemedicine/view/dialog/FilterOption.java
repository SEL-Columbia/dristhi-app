package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.view.contract.SmartRegisterClient;

public interface FilterOption extends DialogOption {
    public boolean filter(SmartRegisterClient client);
}
