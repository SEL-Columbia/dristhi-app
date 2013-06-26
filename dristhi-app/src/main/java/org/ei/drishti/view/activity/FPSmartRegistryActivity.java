package org.ei.drishti.view.activity;

import org.ei.drishti.event.CapturedPhotoInformation;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.FPSmartRegistryController;

import static org.ei.drishti.event.Event.ON_PHOTO_CAPTURED;

public class FPSmartRegistryActivity extends SmartRegisterActivity {

    @Override
    protected void onSmartRegisterInitialization() {
        webView.addJavascriptInterface(new FPSmartRegistryController(context.allEligibleCouples(), context.allBeneficiaries(), context.alertService(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/fp_register.html");
    }

    @Override
    protected void onResumption() {
        webView.loadUrl("javascript:if(window.pageView) {window.pageView.reload();}");
    }
}
