package org.ei.telemedicine.view.dialog;

import static org.ei.telemedicine.AllConstants.OUT_OF_AREA;

import org.ei.telemedicine.Context;
import org.ei.telemedicine.R;
import org.ei.telemedicine.view.contract.SmartRegisterClient;


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
