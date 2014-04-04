package org.ei.drishti.provider;

import android.view.View;
import android.view.ViewGroup;
import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;

import java.util.List;

public interface SmartRegisterClientsProvider {

    public View getView(ECClient client, View parentView, ViewGroup viewGroup);

    //#TODO: Very inefficient - Try some good algorithm
    List<String> getAllUniqueVillageNames();

    public ECClients getListItems();

    public void sort(String sortBy);

    public ECClients filter(CharSequence cs);

    public void showSection(String section);
}
