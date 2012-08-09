package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.PNCDetailController;

public class PNCDetailActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new PNCDetailController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allTimelineEvents(), context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/pnc_detail.html");
    }
}
