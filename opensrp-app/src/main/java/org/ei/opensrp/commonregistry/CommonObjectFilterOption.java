package org.ei.opensrp.commonregistry;

import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

public class CommonObjectFilterOption implements FilterOption {
    private final String criteria;
    public final String fieldname,sortOptionName;
    ByColumnAndByDetails byColumnAndByDetails;

    public enum ByColumnAndByDetails{
        byColumn,byDetails;
    }

    public CommonObjectFilterOption(String sortOptionName ,String criteria, String fieldname, ByColumnAndByDetails byColumnAndByDetails) {
        this.criteria = criteria;
        this.fieldname = fieldname;
        this.byColumnAndByDetails= byColumnAndByDetails;
        this.sortOptionName = sortOptionName;
    }

    @Override
    public String name() {
        return sortOptionName;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
        switch (byColumnAndByDetails){
            case byColumn:
                return ((CommonPersonObjectClient)client).getColumnmaps().get(fieldname).trim().toLowerCase().contains(criteria.trim().toLowerCase());
            case byDetails:
                return (((CommonPersonObjectClient)client).getDetails().get(fieldname)!=null?((CommonPersonObjectClient)client).getDetails().get(fieldname):"").trim().toLowerCase().contains(criteria.toLowerCase());
        }
        return false;
    }
}
