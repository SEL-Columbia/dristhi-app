package org.ei.drishti.provider;

import android.view.View;
import android.view.ViewGroup;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.DialogOption;
import org.ei.drishti.view.dialog.FilterOption;
import org.ei.drishti.view.dialog.ServiceModeOption;
import org.ei.drishti.view.dialog.SortOption;

public interface SmartRegisterClientsProvider {

    public View getView(ECClient client, View parentView, ViewGroup viewGroup);

    public SmartRegisterClients getClients();

    SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                       FilterOption searchFilter, SortOption sortOption);
}
