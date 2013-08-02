package org.ei.drishti.view.controller;

import org.ei.drishti.util.Log;
import org.ei.drishti.view.activity.SecuredWebActivity;

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

    public void log(String text) {
        Log.logInfo(text);
    }
}

