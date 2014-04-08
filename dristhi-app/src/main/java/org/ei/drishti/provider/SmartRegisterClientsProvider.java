package org.ei.drishti.provider;

import android.view.View;
import android.view.ViewGroup;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.contract.SmartRegisterClients;
import org.ei.drishti.view.dialog.DialogOption;

public interface SmartRegisterClientsProvider {

    public View getView(ECClient client, View parentView, ViewGroup viewGroup);

    public ECClients getListItems();

    public SmartRegisterClients sortBy(DialogOption sortBy);

    public SmartRegisterClients filter(CharSequence cs, SmartRegisterClients currentClientsList);

    public void showSection(String section);

    SmartRegisterClients filterBy(DialogOption filterBy);
}
