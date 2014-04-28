package org.ei.drishti.view.contract;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;

import java.util.ArrayList;

public class SmartRegisterClients extends ArrayList<SmartRegisterClient> {

    public SmartRegisterClients applyFilter(final FilterOption villageFilter, final ServiceModeOption serviceModeOption,
                                            final FilterOption searchFilter, SortOption sortOption) {
        SmartRegisterClients results = new SmartRegisterClients();
        Iterables.addAll(results, Iterables.filter(this, new Predicate<SmartRegisterClient>() {
            @Override
            public boolean apply(SmartRegisterClient client) {
                return
                        villageFilter.filter(client)
                                && serviceModeOption.filter(client)
                                && searchFilter.filter(client);
            }
        }));
        return sortOption.sort(results);
    }
}
