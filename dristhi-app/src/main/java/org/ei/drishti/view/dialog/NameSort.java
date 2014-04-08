package org.ei.drishti.view.dialog;

import org.ei.drishti.view.contract.SmartRegisterClients;

import java.util.Collections;

import static org.ei.drishti.view.contract.SmartRegisterClient.NAME_COMPARATOR;

public class NameSort implements DialogOption {
    @Override
    public String name() {
        return "Name (A to Z)";
    }

    @Override
    public SmartRegisterClients apply(SmartRegisterClients allClients) {
        Collections.sort(allClients, NAME_COMPARATOR);
        return allClients;
    }
}
