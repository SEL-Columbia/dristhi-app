package org.ei.opensrp.mcare.household;

import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
<<<<<<< HEAD
=======
import org.ei.opensrp.cursoradapter.CursorFilterOption;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

import static org.ei.opensrp.util.StringUtil.humanize;

<<<<<<< HEAD
public class HHMauzaCommonObjectFilterOption implements FilterOption {
    private final String criteria;
    public final String fieldname;
    private final String filterOptionName;
    ByColumnAndByDetails byColumnAndByDetails;

    public enum ByColumnAndByDetails{
        byColumn,byDetails;
    }

    public HHMauzaCommonObjectFilterOption(String criteria, String fieldname, ByColumnAndByDetails byColumnAndByDetails, String filteroptionname) {
        this.criteria = criteria;
        this.fieldname = fieldname;
        this.byColumnAndByDetails= byColumnAndByDetails;
=======
public class HHMauzaCommonObjectFilterOption implements CursorFilterOption {
    public final String criteria;
    public final String fieldname;
    private final String filterOptionName;

    @Override
    public String filter() {


        return " and details LIKE '%"+criteria+"%'";
    }



    public HHMauzaCommonObjectFilterOption(String criteria, String fieldname,String filteroptionname) {
        this.criteria = criteria;
        this.fieldname = fieldname;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
        this.filterOptionName = filteroptionname;
    }

    @Override
    public String name() {
        return filterOptionName;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {
<<<<<<< HEAD
        switch (byColumnAndByDetails){
            case byColumn:
                return ((CommonPersonObjectClient)client).getColumnmaps().get(fieldname).contains(criteria);
            case byDetails:
                return (humanize((((CommonPersonObjectClient)client).getDetails().get(fieldname)!=null?((CommonPersonObjectClient)client).getDetails().get(fieldname):"").replace("+","_"))).toLowerCase().contains(criteria.toLowerCase());
        }
=======

>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
        return false;
    }
}
