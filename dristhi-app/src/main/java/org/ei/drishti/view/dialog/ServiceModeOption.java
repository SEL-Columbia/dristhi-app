package org.ei.drishti.view.dialog;

import org.ei.drishti.provider.SmartRegisterClientsProvider;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public abstract class ServiceModeOption implements DialogOption {

    private SmartRegisterClientsProvider clientsProvider;
    private ClientsHeaderProvider headerProvider;

    public ServiceModeOption(SmartRegisterClientsProvider clientsProvider, ClientsHeaderProvider headerProvider) {
        this.clientsProvider = clientsProvider;
        this.headerProvider = headerProvider;
    }

    public void apply() {
        headerProvider.onServiceModeSelected(this);
        clientsProvider.onServiceModeSelected(this);
    }

    public abstract ClientsHeaderProvider getHeaderProvider();
}
