package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClients;

public class OutOfAreaFilter implements DialogOption {
    @Override
    public String name() {
        return "O/A";
    }

    @Override
    public SmartRegisterClients apply(SmartRegisterClients allClients) {
        return allClients.outOfAreaECs();
    }
}
