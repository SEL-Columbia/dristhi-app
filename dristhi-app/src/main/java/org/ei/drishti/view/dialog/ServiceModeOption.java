package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClient;

public interface ServiceModeOption extends DialogOption {
    public boolean filter(SmartRegisterClient client);
}
