package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.EligibleCoupleViewController;

public class EligibleCoupleViewActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new EligibleCoupleViewController(this, caseId, context.allEligibleCouples(), context.allBeneficiaries(), context.allTimelineEvents()), "context");
        webView.loadUrl("file:///android_asset/www/ec_detail.html");
    }
}
