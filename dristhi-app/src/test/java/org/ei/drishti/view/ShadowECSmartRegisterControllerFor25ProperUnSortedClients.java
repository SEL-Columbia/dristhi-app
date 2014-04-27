package org.ei.drishti.view;


import org.ei.drishti.view.contract.ECClient;
import org.ei.drishti.view.contract.ECClients;
import org.ei.drishti.view.controller.ECSmartRegisterController;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(ECSmartRegisterController.class)
public class ShadowECSmartRegisterControllerFor25ProperUnSortedClients {

    @Implementation
    public ECClients getClients() {
        ECClients clients = new ECClients();
        clients.add(new ECClient("abcd4", "Bhagya", "Ramesh", "Hosa agrahara", 140));
        clients.add(new ECClient("abcd5", "Chaitra", "Rams", "Somanahalli colony", 36));
        for (int i = 0; i < 20; i++) {
            clients.add(new ECClient("abcd2" + i , "wife" + i , "husband" + i , "Village" + i, 1001 + i));
        }
        clients.add(new ECClient("abcd1", "Adhiti", "Rama", "Battiganahalli", 69));
        clients.add(new ECClient("abcd2", "Akshara", "Rajesh", "Half bherya", 500));
        clients.add(new ECClient("abcd3", "Anitha", "Chandan", "Half bherya", 87));
        return clients;
    }
}

