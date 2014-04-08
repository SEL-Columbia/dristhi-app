package org.ei.drishti.view.contract;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.ArrayList;

import static com.google.common.collect.Iterables.filter;
import static org.ei.drishti.view.contract.ECClient.OUT_OF_AREA;

public class SmartRegisterClients extends ArrayList<SmartRegisterClient> {

    public SmartRegisterClients ecsBelongingToVillage(final String village) {
        SmartRegisterClients results = new SmartRegisterClients();
        Iterables.addAll(results, Iterables.filter(this, new Predicate<SmartRegisterClient>() {
            @Override
            public boolean apply(SmartRegisterClient client) {
                return client.village().equalsIgnoreCase(village);
            }
        }));
        return results;
    }

    public SmartRegisterClients outOfAreaECs() {
        SmartRegisterClients results = new SmartRegisterClients();
        Iterables.addAll(results, Iterables.filter(this, new Predicate<SmartRegisterClient>() {
            @Override
            public boolean apply(SmartRegisterClient client) {
                return OUT_OF_AREA.equalsIgnoreCase(client.locationStatus());
            }
        }));
        return results;

    }

    public SmartRegisterClients applyFilter(final String filterCriterion) {
        if (filterCriterion.isEmpty()) {
            return this;
        } else {
            ECClients results = new ECClients();
            Iterables.addAll(results, filter(this, new Predicate<SmartRegisterClient>() {
                @Override
                public boolean apply(SmartRegisterClient ecClient) {
                    return ecClient.satisfiesFilter(filterCriterion);
                }
            }));
            return results;
        }
    }
}
