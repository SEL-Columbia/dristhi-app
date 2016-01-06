package org.ei.opensrp.vaccinator.woman;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.vaccinator.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

/**
 * Created by Ahmed on 19-Oct-15.
 */
public class WomanSearchOption implements FilterOption {
    private final String criteria;
    public WomanSearchOption(String criteria){
        this.criteria=criteria;
    }
    @Override
    public boolean filter(SmartRegisterClient client) {
        boolean result = false;
        CommonPersonObjectClient currentclient = (CommonPersonObjectClient) client;
        if(!result) {
            if(currentclient.getDetails().get("first_name") != null) {
                if (currentclient.getDetails().get("first_name").toLowerCase().contains(criteria.toLowerCase())) {
                    result = true;
                }
            }
        }
        if(!result) {
            if(currentclient.getDetails().get("program_client_id") != null) {
                if (currentclient.getDetails().get("program_client_id").contains(criteria)) {
                    result = true;
                }
            }
        }
     /*   if(!result) {
            if(currentclient.getDetails().get("father_name") != null) {
                if (currentclient.getDetails().get("father_name").contains(criteria)) {
                    result = true;
                }
            }
        }*/
        return result;
    }

    @Override
    public String name() {
        return Context.getInstance().applicationContext().getResources().getString(R.string.str_woman_search_hint);
    }
}
