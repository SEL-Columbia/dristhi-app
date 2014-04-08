package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClients;

public interface DialogOption {
    public String name();

    public SmartRegisterClients apply(SmartRegisterClients allClients);
}
