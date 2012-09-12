package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ANCListViewController;

public class ANCListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new ANCListViewController(this, context.allBeneficiaries(), context.allEligibleCouples(), context.listCache(), context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/anc_list.html");
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
