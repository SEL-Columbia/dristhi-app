package org.ei.drishti.view.activity;

import org.ei.drishti.view.controller.ChildSmartRegistryController;

public class ChildSmartRegistryActivity extends SmartRegisterActivity {
    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new ChildSmartRegistryController(context.serviceProvidedService(), context.alertService(),
                context.allBeneficiaries(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/child_register.html");
    }
}
