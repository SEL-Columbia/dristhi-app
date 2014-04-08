package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClient;

public class VillageFilter implements FilterOption {
    private final String filter;

    public VillageFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String name() {
        return filter;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return client.village().equalsIgnoreCase(filter);
    }
}
