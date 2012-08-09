package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ANCDetailController;

public class ANCDetailActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new ANCDetailController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allTimelineEvents(), context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/anc_detail.html");
    }
}
