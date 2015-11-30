package org.ei.telemedicine.view.activity;

import org.ei.telemedicine.view.controller.ChildSmartRegisterController;

public class ChildSmartRegisterActivity extends SmartRegisterActivity {
    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new ChildSmartRegisterController(context.serviceProvidedService(), context.alertService(),
                context.allBeneficiaries(), context.listCache(), context.smartRegisterClientsCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/child_register.html");
    }
}
