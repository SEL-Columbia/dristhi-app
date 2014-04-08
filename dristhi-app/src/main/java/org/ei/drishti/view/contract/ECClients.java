package org.ei.drishti.view.contract;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import static com.google.common.collect.Iterables.filter;

public class ECClients extends SmartRegisterClients {

    public ECClients applyFilter(final String filterCriterion) {
        if (filterCriterion.length() <= 0) {
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
