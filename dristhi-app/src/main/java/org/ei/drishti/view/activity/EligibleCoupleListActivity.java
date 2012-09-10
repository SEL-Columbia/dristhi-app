package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.EligibleCoupleListViewController;

public class EligibleCoupleListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new EligibleCoupleListViewController(context.allEligibleCouples(), context.listCache(), this, context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/ec_list.html");
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.destroy();
        webView = null;
    }

    @Override
    protected void onResumption() {
        if (webView != null) return;

        recreateWebView();
    }

    private void recreateWebView() {
        initializeWebView();
        onInitialization();
    }
}
