package org.ei.telemedicine.view.dialog;

import static org.ei.telemedicine.Context.getInstance;

import org.ei.telemedicine.R;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;

public class FPOthersServiceMode extends FPAllMethodsServiceMode {

    public FPOthersServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_register_service_mode_others);
    }
}
