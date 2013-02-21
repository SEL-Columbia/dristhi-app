package org.ei.drishti.view.controller;

import android.content.res.Resources;
import com.google.gson.Gson;
import org.ei.drishti.R;
import org.ei.drishti.util.Log;
import org.ei.drishti.view.activity.SecuredWebActivity;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    private UpdateController updateController;
    private final SecuredWebActivity activity;

    public HomeController(SecuredWebActivity activity, UpdateController updateController) {
        this.updateController = updateController;
        this.activity = activity;
    }

    public void pageHasFinishedLoading() {
        updateController.pageHasFinishedLoading();
    }

    public void startManualSync() {
        activity.updateFromServer();
    }

    public void log(String text) {
        Log.logInfo(text);
    }

    public String getResourceStrings() {
        Resources resources = activity.getResources();

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
