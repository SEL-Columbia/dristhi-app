package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;

import static org.ei.drishti.view.activity.SecuredNativeSmartRegisterActivity.ClientsHeaderProvider;

public class AllEligibleCoupleServiceMode extends ServiceModeOption {

    public AllEligibleCoupleServiceMode() {
        super(null, null);
    }

    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.couple_selection);
    }

    @Override
    public ClientsHeaderProvider getHeaderProvider() {
        return null;
    }
}
