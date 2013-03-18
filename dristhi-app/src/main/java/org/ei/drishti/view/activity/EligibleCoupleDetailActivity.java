package org.ei.drishti.view.activity;

import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.EligibleCoupleDetailController;

import static org.ei.drishti.event.Event.ON_DATA_CHANGE;

public class EligibleCoupleDetailActivity extends SecuredWebActivity {
    private Listener<Boolean> dataChangeListener;

    @Override
    protected void onInitialization() {
        String caseId = (String) getIntent().getExtras().get("caseId");

        webView.addJavascriptInterface(new EligibleCoupleDetailController(this, caseId, context.allEligibleCouples(),
                context.allAlerts(), context.allTimelineEvents(), context.commCareClientService()), "context");
        webView.loadUrl("file:///android_asset/www/ec_detail.html");

        dataChangeListener = new Listener<Boolean>() {
            @Override
            public void onEvent(Boolean data) {
                if (data) {
                    if (webView != null) {
                        webView.reload();
                    }
                }
            }
        };
        ON_DATA_CHANGE.addListener(dataChangeListener);
    }
}
