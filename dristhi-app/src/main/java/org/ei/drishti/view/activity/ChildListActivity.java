package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ChildListViewController;

public class ChildListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new ChildListViewController(this, context.allBeneficiaries(), context.allEligibleCouples(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/child_list.html");
    }
}
