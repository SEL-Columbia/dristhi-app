package org.ei.drishti.view.dialog;

import android.view.View;
import org.ei.drishti.R;
import org.ei.drishti.provider.SmartRegisterClientsProvider;
import org.ei.drishti.view.contract.*;
import org.ei.drishti.view.viewHolder.NativeChildSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.NativeFPSmartRegisterViewHolder;
import org.ei.drishti.view.viewHolder.OnClickFormLauncher;

import static org.ei.drishti.AllConstants.FormNames.FP_CHANGE;
import static org.ei.drishti.AllConstants.FormNames.FP_COMPLICATIONS;
import static org.ei.drishti.Context.getInstance;
import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class FPPrioritizationHighPriorityServiceMode extends FPPrioritizationAllECServiceMode {

    public FPPrioritizationHighPriorityServiceMode(SmartRegisterClientsProvider provider) {
        super(provider);
    }

    @Override
    public String name() {
        return getInstance().getStringResource(R.string.fp_prioritization_high_priority_service_mode);
    }
}
