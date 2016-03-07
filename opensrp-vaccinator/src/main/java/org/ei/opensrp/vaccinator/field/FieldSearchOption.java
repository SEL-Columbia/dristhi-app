package org.ei.opensrp.vaccinator.field;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.vaccinator.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

/**
 * Created by muhammad.ahmed@ihsinformatics.com on 16-Nov-15.
 */
public class FieldSearchOption implements FilterOption {
    private final String criteria;

    public FieldSearchOption(String s) {
        this.criteria=s;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        boolean result = false;
        CommonPersonObjectClient currentclient = (CommonPersonObjectClient) client;
        if(!result) {
            if(currentclient.getDetails().get("date_formatted") != null) {
                if (currentclient.getDetails().get("date_formatted").toLowerCase().contains(criteria.toLowerCase())) {
                    result = true;
                }
            }
        }

        return result;
    }

    @Override
    public String name() {
        return Context.getInstance().applicationContext().getResources().getString(R.string.str_field_search_hint);
    }
}
