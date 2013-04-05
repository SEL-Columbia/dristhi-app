package org.ei.drishti.view.activity;

public class FPSmartRegistryActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
//        webView.addJavascriptInterface(new EligibleCoupleListViewController(context.allEligibleCouples(),
//                context.allBeneficiaries(), context.allSettings(), context.listCache(), this, context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/fp_register.html");
    }
}
