package org.ei.drishti.view.activity;

import org.ei.drishti.event.Listener;
import org.ei.drishti.view.controller.HomeController;

import static org.ei.drishti.event.Event.*;

public class HomeActivity extends SecuredWebActivity {

    private Listener<Boolean> onSyncStartListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            updateController.startProgressIndicator();
        }
    };

    private Listener<Boolean> onSyncCompleteListener = new Listener<Boolean>() {
        @Override
        public void onEvent(Boolean data) {
            updateController.stopProgressIndicator();
        }
    };

    private Listener<String> onFormSubmittedListener = new Listener<String>() {
        @Override
        public void onEvent(String instanceId) {
            updateController.updateANMDetails();
        }
    };

    @Override
    protected void onInitialization() {
        SYNC_STARTED.addListener(onSyncStartListener);
        SYNC_COMPLETED.addListener(onSyncCompleteListener);
        FORM_SUBMITTED.addListener(onFormSubmittedListener);

        webView.loadUrl("file:///android_asset/www/home.html");
        webView.addJavascriptInterface(new HomeController(this, updateController), "context");
    }

    @Override
    protected void onResumption() {
        if (context.allSettings().fetchIsSyncInProgress()) {
            updateController.startProgressIndicator();
        } else
            updateController.stopProgressIndicator();
    }
}
