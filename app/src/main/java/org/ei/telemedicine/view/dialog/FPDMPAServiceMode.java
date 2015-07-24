package org.ei.telemedicine.view.dialog;

import org.ei.telemedicine.R;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;

import static org.ei.telemedicine.Context.getInstance;

public class FPDMPAServiceMode extends FPAllMethodsServiceMode {

    public FPDMPAServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_register_service_mode_dmpa);
    }
}
