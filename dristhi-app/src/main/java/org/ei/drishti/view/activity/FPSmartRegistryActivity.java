package org.ei.drishti.view.activity;

import org.ei.drishti.event.CapturedPhotoInformation;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.FPSmartRegistryController;

import static org.ei.drishti.event.Event.ON_PHOTO_CAPTURED;

public class FPSmartRegistryActivity extends SecuredWebActivity {
    private Listener<CapturedPhotoInformation> photoCaptureListener;

    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new FPSmartRegistryController(context.allEligibleCouples(), context.allBeneficiaries(), context.listCache()), "context");
        webView.loadUrl("file:///android_asset/www/smart_registry/fp_register.html");

        photoCaptureListener = new Listener<CapturedPhotoInformation>() {
            @Override
            public void onEvent(CapturedPhotoInformation data) {
                if (webView != null) {
                    webView.loadUrl("javascript:pageView.reloadPhoto('" + data.entityId() + "', '" + data.photoPath() + "')");
                }
            }
        };
        ON_PHOTO_CAPTURED.addListener(photoCaptureListener);
    }

    @Override
    protected void onResumption() {
        webView.loadUrl("javascript:pageView.reload()");
    }
}
