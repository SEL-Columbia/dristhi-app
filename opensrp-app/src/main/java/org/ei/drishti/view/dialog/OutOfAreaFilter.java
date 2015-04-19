package org.ei.drishti.view.dialog;

import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.view.contract.SmartRegisterClient;

import static org.ei.drishti.AllConstants.OUT_OF_AREA;


public class OutOfAreaFilter implements FilterOption {
    @Override
    public String name() {
        return Context.getInstance().getStringResource(R.string.filter_by_out_of_area_label);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        return OUT_OF_AREA.equalsIgnoreCase(client.locationStatus());
    }
}
