package org.ei.drishti.view.dialog;

import android.view.View;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public abstract class ServiceModeOption implements DialogOption {

    private SmartRegisterClientsProvider clientsProvider;

    public ServiceModeOption(SmartRegisterClientsProvider clientsProvider) {
        this.clientsProvider = clientsProvider;
    }

    public void apply() {
        if (clientsProvider != null) {
            clientsProvider.onServiceModeSelected(this);
        }
    }

    public abstract ClientsHeaderProvider getHeaderProvider();

    public abstract void setupListView(ChildSmartRegisterClient client,
                                       NativeChildSmartRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);
}
