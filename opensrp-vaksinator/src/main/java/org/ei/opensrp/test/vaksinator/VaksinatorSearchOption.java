package org.ei.opensrp.test.vaksinator;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.test.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

public class VaksinatorSearchOption implements FilterOption {
    private final String criteria;

    public VaksinatorSearchOption(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public String name() {
        return Context.getInstance().applicationContext().getResources().getString(R.string.hh_search_hint);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        boolean result = false;
        CommonPersonObjectClient currentclient = (CommonPersonObjectClient) client;
//        AllCommonsRepository allElcoRepository = new AllCommonsRepository("elco");
        if(!result) {
            if(currentclient.getDetails().get("nama_bayi") != null) {
                if (currentclient.getDetails().get("nama_bayi").toLowerCase().contains(criteria.toLowerCase())) {
                    result = true;
                }
            }
        }
        if(!result) {
            if(currentclient.getDetails().get("nama_orang_tua") != null) {
                if (currentclient.getDetails().get("nama_orang_tua").contains(criteria)) {
                    result = true;
                }
            }
        }

        return result;
    }
}
