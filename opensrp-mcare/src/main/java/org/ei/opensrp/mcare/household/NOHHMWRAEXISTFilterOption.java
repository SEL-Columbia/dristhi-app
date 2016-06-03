package org.ei.opensrp.mcare.household;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.mcare.R;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

public class NOHHMWRAEXISTFilterOption implements FilterOption {
    private final String criteria;
    public final String fieldname;
    ByColumnAndByDetails byColumnAndByDetails;

    public enum ByColumnAndByDetails{
        byColumn,byDetails;
    }

    public NOHHMWRAEXISTFilterOption(String criteria, String fieldname, ByColumnAndByDetails byColumnAndByDetails) {
        this.criteria = criteria;
        this.fieldname = fieldname;
        this.byColumnAndByDetails= byColumnAndByDetails;
    }

    @Override
    public String name() {
        return Context.getInstance().applicationContext().getResources().getString(R.string.hh_no_mwra);
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        switch (byColumnAndByDetails){
            case byColumn:
                return ((CommonPersonObjectClient)client).getColumnmaps().get(fieldname).contains(criteria);
            case byDetails:
                return (((CommonPersonObjectClient)client).getDetails().get(fieldname)!=null?((CommonPersonObjectClient)client).getDetails().get(fieldname):"").contains(criteria);
        }
        return false;
    }
}
