package org.ei.drishti.view.dialog;

import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;

import static org.ei.drishti.Context.getInstance;

public class FPPrioritizationTwoPlusChildrenServiceMode extends FPPrioritizationAllECServiceMode {

    public FPPrioritizationTwoPlusChildrenServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_prioritization_two_plus_children_service_mode);
    }
}
