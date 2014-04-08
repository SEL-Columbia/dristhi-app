package org.ei.drishti.view.contract;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ei.drishti.view.dialog.DialogOption;

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
        Predicate<SmartRegisterClient> predicate = new Predicate<SmartRegisterClient>() {
            @Override
            public boolean apply(SmartRegisterClient client) {
                return OUT_OF_AREA.equalsIgnoreCase(client.locationStatus());
            }
        };
        Iterables.addAll(results, Iterables.filter(this, predicate));
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

    public SmartRegisterClients applyFilter(final DialogOption villageFilter, final DialogOption serviceModeOption,
                                            final DialogOption searchFilter, DialogOption sortOption) {

        SmartRegisterClients results = new SmartRegisterClients();
        Iterables.addAll(results, Iterables.filter(this, new Predicate<SmartRegisterClient>() {
            @Override
            public boolean apply(SmartRegisterClient client) {
                return villageFilter.filter(client)
                        && serviceModeOption.filter(client)
                        && searchFilter.filter(client);
            }
        }));
        return sortOption.sort(results);
    }
}
