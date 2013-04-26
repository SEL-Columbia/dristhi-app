package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ChildDetailController;

public class ChildDetailActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new ChildDetailController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allAlerts(), context.allTimelineEvents()), "context");
        webView.loadUrl("file:///android_asset/www/child_detail.html");
    }
}
