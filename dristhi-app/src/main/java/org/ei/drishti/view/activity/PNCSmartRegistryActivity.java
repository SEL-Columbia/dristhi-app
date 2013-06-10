package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.PNCSmartRegistryController;

public class PNCSmartRegistryActivity extends SmartRegisterActivity {
    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new PNCSmartRegistryController(context.serviceProvidedService(), context.alertService(),
                context.allEligibleCouples(), context.allBeneficiaries(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/pnc_register.html");
    }

    @Override
    protected void onResumption() {
        webView.loadUrl("javascript:if(window.pageView) {window.pageView.reload();}");
    }
}
