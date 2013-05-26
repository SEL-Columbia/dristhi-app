package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ANCSmartRegistryController;

public class ANCSmartRegistryActivity extends SmartRegisterActivity {
    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new ANCSmartRegistryController(context.serviceProvidedService(), context.alertService(), context.allEligibleCouples(), context.allBeneficiaries(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/anc_register.html");
    }

    @Override
    protected void onResumption() {
        webView.loadUrl("javascript:pageView.reload()");
    }
}
