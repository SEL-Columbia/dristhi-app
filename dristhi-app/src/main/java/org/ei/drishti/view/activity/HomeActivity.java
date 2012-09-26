package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.HomeController;

public class HomeActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.loadUrl("file:///android_asset/www/home.html");
        webView.addJavascriptInterface(new HomeController(this, updateController), "context");
    }

    @Override
    protected void onResumption() {
        updateFromServer();
    }
}
