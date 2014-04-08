package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClient;
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
    public SmartRegisterClients sort(SmartRegisterClients allClients) {
        return allClients.ecsBelongingToVillage(filter);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return client.village().equalsIgnoreCase(filter);
    }
}
