package org.ei.drishti.view.activity;

import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.view.AfterFetchListener;
import org.ei.drishti.view.NoOpProgressIndicator;
import org.ei.drishti.view.UpdateActionsTask;
import org.ei.drishti.view.controller.HomeActivityController;

public class HomeActivity extends SecuredWebActivity {

    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new HomeActivityController(this, context.anmService()), "context");
        webView.loadUrl("file:///android_asset/www/home.html");
    }

    @Override
    protected void onResumption() {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(this, context.actionService(), new NoOpProgressIndicator());
        updateActionsTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
            }
        });
    }
}
