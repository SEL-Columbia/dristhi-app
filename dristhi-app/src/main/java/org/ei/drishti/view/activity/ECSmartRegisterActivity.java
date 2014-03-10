package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ECSmartRegisterController;

public class ECSmartRegisterActivity extends SmartRegisterActivity {
    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new ECSmartRegisterController(context.allEligibleCouples(), context.allBeneficiaries(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/web/dist/ec_register.html");
    }
}
