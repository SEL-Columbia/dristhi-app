package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.drishti.view.contract.SmartRegisterClient.EC_NUMBER_COMPARATOR;

public class ECNumberSort implements DialogOption {
    @Override
    public String name() {
        return "EC Number";
    }

    @Override
    public SmartRegisterClients apply(SmartRegisterClients allClients) {
        Collections.sort(allClients, EC_NUMBER_COMPARATOR);
        return allClients;
    }
}
