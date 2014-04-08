package org.ei.drishti.provider;

import android.view.View;
import android.view.ViewGroup;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.DialogOption;

public interface SmartRegisterClientsProvider {

    public View getView(ECClient client, View parentView, ViewGroup viewGroup);

    public SmartRegisterClients getListItems();

    SmartRegisterClients updateClients(DialogOption villageFilter, DialogOption serviceModeOption,
                                       DialogOption searchFilter, DialogOption sortOption);
}
