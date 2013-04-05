package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.FPSmartRegistryController;

public class FPSmartRegistryActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new FPSmartRegistryController(context.allEligibleCouples(), context.allBeneficiaries(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/fp_register.html");
    }
}
