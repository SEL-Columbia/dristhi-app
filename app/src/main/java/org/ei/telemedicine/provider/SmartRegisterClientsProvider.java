package org.ei.telemedicine.provider;

import android.view.View;
import android.view.ViewGroup;

import org.ei.telemedicine.view.contract.SmartRegisterClient;
import org.ei.telemedicine.view.contract.SmartRegisterClients;
import org.ei.telemedicine.view.dialog.FilterOption;
import org.ei.telemedicine.view.dialog.ServiceModeOption;
import org.ei.telemedicine.view.dialog.SortOption;
import org.ei.telemedicine.view.viewHolder.OnClickFormLauncher;

public interface SmartRegisterClientsProvider {

    public View getView(SmartRegisterClient client, View parentView, ViewGroup viewGroup);

    public SmartRegisterClients getClients();

    SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption,
                                       FilterOption searchFilter, SortOption sortOption);

    void onServiceModeSelected(ServiceModeOption serviceModeOption);

    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData);
}
