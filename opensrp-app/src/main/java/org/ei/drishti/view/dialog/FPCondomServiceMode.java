package org.ei.drishti.view.dialog;

import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;

import static org.ei.drishti.Context.getInstance;

public class FPCondomServiceMode extends FPAllMethodsServiceMode {

    public FPCondomServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_register_service_mode_condom);
    }
}
