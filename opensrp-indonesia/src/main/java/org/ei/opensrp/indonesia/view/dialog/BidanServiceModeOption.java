package org.ei.opensrp.indonesia.view.dialog;

import android.view.View;

import org.ei.opensrp.indonesia.view.contract.AnakClient;
import org.ei.opensrp.indonesia.view.contract.KIANCClient;
import org.ei.opensrp.indonesia.view.contract.KartuIbuClient;
import org.ei.opensrp.indonesia.view.viewHolder.NativeAnakRegisterViewHolder;
import org.ei.opensrp.indonesia.view.viewHolder.NativeKIANCRegisterViewHolder;
import org.ei.opensrp.indonesia.view.viewHolder.NativeKIRegisterViewHolder;
import org.ei.opensrp.provider.SmartRegisterClientsProvider;
import org.ei.opensrp.view.dialog.ServiceModeOption;

/**
 * Created by Dimas Ciputra on 3/5/15.
 */
public abstract class BidanServiceModeOption extends ServiceModeOption {

    public BidanServiceModeOption(SmartRegisterClientsProvider clientsProvider) {
        super(clientsProvider);
    }

    public abstract void setupListView(KartuIbuClient client,
                                       NativeKIRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

    public abstract void setupListView(KIANCClient client,
                                       NativeKIANCRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

    public abstract void setupListView(AnakClient client,
                                       NativeAnakRegisterViewHolder viewHolder,
                                       View.OnClickListener clientSectionClickListener);

}
