package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.WorkplanDetailController;

public class WorkplanDetailActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        String villageName = (String) getIntent().getExtras().get("villageName");

        webView.addJavascriptInterface(new WorkplanDetailController(villageName, context.allAlerts()), "context");
        webView.loadUrl("file:///android_asset/www/workplan_detail.html");
    }
}
