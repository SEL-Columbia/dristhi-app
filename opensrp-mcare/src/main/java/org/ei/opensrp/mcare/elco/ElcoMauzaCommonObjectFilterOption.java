package org.ei.opensrp.mcare.elco;

import android.util.Log;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
<<<<<<< HEAD
=======
import org.ei.opensrp.cursoradapter.CursorFilterOption;
>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

import static org.ei.opensrp.util.StringUtil.humanize;

<<<<<<< HEAD
public class ElcoMauzaCommonObjectFilterOption implements FilterOption {
    private final String criteria;
    public final String fieldname;
    private final String filterOptionName;
    ByColumnAndByDetails byColumnAndByDetails;

    public enum ByColumnAndByDetails{
        byColumn,byDetails;
    }

    public ElcoMauzaCommonObjectFilterOption(String criteria, String fieldname, ByColumnAndByDetails byColumnAndByDetails, String filteroptionname) {
        this.criteria = criteria;
        this.fieldname = fieldname;
        this.byColumnAndByDetails= byColumnAndByDetails;
=======
public class ElcoMauzaCommonObjectFilterOption implements CursorFilterOption {
    public final String criteria;
    public final String fieldname;
    private final String filterOptionName;

    @Override
    public String filter() {
        return  " and details LIKE '%"+criteria+"%'";
    }

    public ElcoMauzaCommonObjectFilterOption(String criteria, String fieldname, String filteroptionname) {
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
                AllCommonsRepository allelcoRepository = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("elco");

                CommonPersonObject elcoobject = allelcoRepository.findByCaseID(((CommonPersonObjectClient)client).entityId());

                AllCommonsRepository householdrep = org.ei.opensrp.Context.getInstance().allCommonsRepositoryobjects("household");
                CommonPersonObject householdparent = householdrep.findByCaseID(elcoobject.getRelationalId());
                String location = "";
                if(householdparent.getDetails().get("existing_Mauzapara") != null) {
                    location = householdparent.getDetails().get("existing_Mauzapara");
                    location = humanize(location.replace("+", "_"));
                    Log.v("location", location);
                }
                return location.replace("+"," ").toLowerCase().contains(criteria.toLowerCase());
        }
=======

>>>>>>> fc57a485ae9e44237dc69626e10ad144281a146a
        return false;
    }
}
