package org.ei.drishti.view.activity;

public class ANCSmartRegistryActivity extends SmartRegisterActivity {
    @Override
    protected void onSmartRegisterInitialization() {
        webView.loadUrl("file:///android_asset/www/smart_registry/anc_register.html");
    }

    @Override
    protected void onResumption() {
        webView.loadUrl("javascript:pageView.reload()");
    }
}
