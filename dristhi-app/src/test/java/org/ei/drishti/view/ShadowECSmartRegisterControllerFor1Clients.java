package org.ei.drishti.view;


import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.controller.ECSmartRegisterController;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import static org.ei.drishti.view.ShadowECSmartRegisterController.clients;

@Implements(ECSmartRegisterController.class)
public class ShadowECSmartRegisterControllerFor1Clients {

    @Implementation
    public ECClients getClients() {
        return clients(1);
    }
}

