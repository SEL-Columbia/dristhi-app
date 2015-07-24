package org.ei.telemedicine.view.activity;

import org.ei.telemedicine.view.controller.ReportsController;

public class ReportsActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new ReportsController(this), "context");
        webView.loadUrl("file:///android_asset/www/reports.html");
    }
}
