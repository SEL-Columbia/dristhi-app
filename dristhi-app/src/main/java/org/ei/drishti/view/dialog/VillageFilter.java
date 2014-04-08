package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClients;

public class VillageFilter implements DialogOption {
    private final String filter;

    public VillageFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String name() {
        return filter;
    }

    @Override
    public SmartRegisterClients apply(SmartRegisterClients allClients) {
        return allClients.ecsBelongingToVillage(filter);
    }
}
