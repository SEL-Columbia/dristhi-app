package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.WorkplanController;

public class WorkplanActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new WorkplanController(context.allAlerts(), context.commCareClientService(), context.allEligibleCouples(), this), "context");
        webView.loadUrl("file:///android_asset/www/workplan.html");
    }
}
