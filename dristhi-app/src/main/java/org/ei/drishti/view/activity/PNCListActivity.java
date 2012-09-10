package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.PNCListViewController;

public class PNCListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new PNCListViewController(this, context.allBeneficiaries(), context.allEligibleCouples()), "context");
        webView.loadUrl("file:///android_asset/www/pnc_list.html");
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
