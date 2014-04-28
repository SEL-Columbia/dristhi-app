package org.ei.drishti.view;


import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.controller.ECSmartRegisterController;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ECSmartRegisterController.class)
public class ShadowECSmartRegisterControllerWithZeroClients {

    @Implementation
    public ECClients getClients() {
        return new ECClients();
    }
}

