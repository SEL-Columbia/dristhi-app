package org.ei.drishti.view.contract;


import org.ei.drishti.util.IntegerUtil;

import java.util.Comparator;

public interface ChildSmartRegisterClient extends SmartRegisterClient {

    Comparator<SmartRegisterClient> CHILD_NAME_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return ChildClient.compareChildNames(client, anotherClient);
        }
    };

    Comparator<SmartRegisterClient> AGE_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            return IntegerUtil.compare(client.ageInDays(), anotherClient.ageInDays());
        }
    };

    Comparator<SmartRegisterClient> HR_COMPARATOR = new Comparator<SmartRegisterClient>() {
        @Override
        public int compare(SmartRegisterClient client, SmartRegisterClient anotherClient) {
            if ((client.isHighRisk() && anotherClient.isHighRisk())
                    || (!client.isHighRisk() && !anotherClient.isHighRisk())) {
                return ChildClient.compareChildNames(client, anotherClient);
            } else {
                return anotherClient.isHighRisk() ? 1 : -1;
            }
        }
    };

    public String gender();

    public String thayiCardNumber();

    public String motherEcNumber();

    public String dateOfBirth();

    public String motherName();
}
