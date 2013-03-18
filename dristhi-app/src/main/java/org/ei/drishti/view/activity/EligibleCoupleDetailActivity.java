package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.EligibleCoupleDetailController;

public class EligibleCoupleDetailActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new EligibleCoupleDetailController(this, caseId, context.allEligibleCouples(),
                context.allAlerts(), context.allTimelineEvents(), context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/ec_detail.html");
    }
}
