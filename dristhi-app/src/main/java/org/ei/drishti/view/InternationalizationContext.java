package org.ei.drishti.view;

import android.content.res.Resources;
import com.google.gson.Gson;
import org.ei.drishti.R;

import java.util.HashMap;
import java.util.Map;

public class InternationalizationContext {

    private final Resources resources;

    public InternationalizationContext(Resources resources) {
        this.resources = resources;
    }

    public String getInternationalizedLabels() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("home_ec_label", resources.getString(R.string.home_ec_label));
        map.put("home_anc_label", resources.getString(R.string.home_anc_label));
        map.put("home_pnc_label", resources.getString(R.string.home_pnc_label));
        map.put("home_child_label", resources.getString(R.string.home_child_label));
        map.put("home_report_label", resources.getString(R.string.home_report_label));
        map.put("home_workplan_label", resources.getString(R.string.home_workplan_label));

        return new Gson().toJson(map);
    }
}
