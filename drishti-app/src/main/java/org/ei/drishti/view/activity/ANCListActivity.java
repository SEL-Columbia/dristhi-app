package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ANCListViewController;

public class ANCListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new ANCListViewController(context.allBeneficiaries(), this), "context");
        webView.loadUrl("file:///android_asset/www/anc_list.html");
    }
}
