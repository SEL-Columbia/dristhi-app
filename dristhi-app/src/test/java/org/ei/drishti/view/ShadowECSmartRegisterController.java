package org.ei.drishti.view;


import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.controller.ECSmartRegisterController;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ECSmartRegisterController.class)
public class ShadowECSmartRegisterController {

    @Implementation
    public ECClients getClients() {
        return clients(10);
    }

    public static ECClients clients(int clientCount) {
        ECClients clients = new ECClients();
        for (int i = 0; i < clientCount; i++) {
            clients.add(new ECClient("CASE " + i, "Wife 1" + i, "Husband 1" + i, "Village 1" + i, 100 + i));
        }
        return clients;
    }
}

