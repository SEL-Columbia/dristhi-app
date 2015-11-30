package org.ei.telemedicine.view.activity;

import org.ei.telemedicine.view.controller.FPSmartRegisterController;

public class FPSmartRegisterActivity extends SmartRegisterActivity {

    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new FPSmartRegisterController(context.allEligibleCouples(), context.allBeneficiaries(), context.alertService(), context.listCache(), context.fpClientsCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/fp_register.html");
    }
}
