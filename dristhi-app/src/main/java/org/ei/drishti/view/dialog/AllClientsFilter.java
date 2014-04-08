package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClients;

public class AllClientsFilter implements DialogOption {
    @Override
    public String name() {
        return "All";
    }

    @Override
    public SmartRegisterClients apply(SmartRegisterClients allClients) {
        return allClients;
    }
}
