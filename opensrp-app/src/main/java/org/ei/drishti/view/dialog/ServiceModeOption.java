package org.ei.drishti.view.dialog;

import android.view.View;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.ANCSmartRegisterClient;
import org.ei.drishti.view.contract.ChildSmartRegisterClient;
import org.ei.drishti.view.contract.FPSmartRegisterClient;
import org.ei.drishti.view.contract.pnc.PNCSmartRegisterClient;
import org.ei.drishti.view.viewHolder.NativeANCSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativePNCSmartRegisterViewHolder;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public abstract class ServiceModeOption implements DialogOption {

    private SmartRegisterClientsProvider clientsProvider;

    public ServiceModeOption(SmartRegisterClientsProvider clientsProvider) {
        this.clientsProvider = clientsProvider;
    }

    public void apply() {
        clientsProvider.onServiceModeSelected(this);
    }

    public SmartRegisterClientsProvider provider() {
        return clientsProvider;
    }

    public abstract ClientsHeaderProvider getHeaderProvider();

    public abstract void setupListView(ChildSmartRegisterClient client,
                                       NativeChildSmartRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

    public abstract void setupListView(ANCSmartRegisterClient client,
                                       NativeANCSmartRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

    public abstract void setupListView(FPSmartRegisterClient client,
                                       NativeFPSmartRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

    public abstract void setupListView(PNCSmartRegisterClient client,
                                       NativePNCSmartRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

}
