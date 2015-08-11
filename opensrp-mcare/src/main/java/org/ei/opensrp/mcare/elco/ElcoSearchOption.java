package org.ei.opensrp.mcare.elco;

import org.ei.opensrp.mcare.R;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

public class ElcoSearchOption implements FilterOption {
    private final String criteria;

    public ElcoSearchOption(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public String name() {
        return Context.getInstance().applicationContext().getResources().getString(R.string.elco_search_hint);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        boolean result = false;
        CommonPersonObjectClient currentclient = (CommonPersonObjectClient) client;
//        AllCommonsRepository allElcoRepository = new AllCommonsRepository("elco");
        if(!result) {
            if(currentclient.getDetails().get("FWWOMFNAME") != null) {
                if (currentclient.getDetails().get("FWWOMFNAME").toLowerCase().contains(criteria.toLowerCase())) {
                    result = true;
                }
            }
        }
        if(!result) {
            if(currentclient.getDetails().get("JiVitAHHID") != null) {
                if (currentclient.getDetails().get("JiVitAHHID").contains(criteria)) {
                    result = true;
                }
            }
        }
        if(!result) {
            if(currentclient.getDetails().get("GOBHHID") != null) {
                if (currentclient.getDetails().get("GOBHHID").contains(criteria)) {
                    result = true;
                }
            }
        }
        return result;
    }
}
