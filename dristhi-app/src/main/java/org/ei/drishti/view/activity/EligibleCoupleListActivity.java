package org.ei.drishti.view.activity;

import org.ei.drishti.event.CapturedPhotoInformation;
import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.EligibleCoupleListViewController;

import static org.ei.drishti.event.Event.ON_PHOTO_CAPTURED;

public class EligibleCoupleListActivity extends SecuredWebActivity {
    private Listener<CapturedPhotoInformation> photoCaptureListener;

    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new EligibleCoupleListViewController(context.allEligibleCouples(),
                context.allBeneficiaries(), context.allSettings(), context.listCache(), this, context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/ec_list.html");

        photoCaptureListener = new Listener<CapturedPhotoInformation>() {
            @Override
            public void onEvent(CapturedPhotoInformation data) {
                if (webView != null) {
                    webView.loadUrl("javascript:pageView.reloadPhoto('" + data.caseId() + "', '" + data.photoPath() + "')");
                }
            }
        };
        ON_PHOTO_CAPTURED.addListener(photoCaptureListener);
    }
}
