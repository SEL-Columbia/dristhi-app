package org.ei.telemedicine.view.dialog;

import android.view.View;

import org.ei.drishti.R;
import org.ei.telemedicine.provider.SmartRegisterClientsProvider;
import org.ei.telemedicine.view.contract.*;
import org.ei.telemedicine.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.telemedicine.view.viewHolder.OnClickFormLauncher;

import static org.ei.telemedicine.AllConstants.FormNames.FP_CHANGE;
import static org.ei.telemedicine.AllConstants.FormNames.FP_COMPLICATIONS;
import static org.ei.telemedicine.Context.getInstance;
import static org.ei.telemedicine.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class FPPrioritizationHighPriorityServiceMode extends FPPrioritizationAllECServiceMode {

    public FPPrioritizationHighPriorityServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_prioritization_high_priority_service_mode);
    }
}
