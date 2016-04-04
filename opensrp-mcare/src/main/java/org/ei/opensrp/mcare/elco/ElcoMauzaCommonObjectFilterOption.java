package org.ei.opensrp.mcare.elco;

import android.util.Log;

import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.cursoradapter.CursorFilterOption;
import org.ei.opensrp.view.contract.SmartRegisterClient;
import org.ei.opensrp.view.dialog.FilterOption;

import static org.ei.opensrp.util.StringUtil.humanize;

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
        this.filterOptionName = filteroptionname;
    }

    @Override
    public String name() {
        return filterOptionName;
    }

    @Override
    public boolean filter(SmartRegisterClient client) {

        return false;
    }
}
