package org.ei.drishti.view.activity;

import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.view.AfterFetchListener;
import org.ei.drishti.view.NoOpProgressIndicator;
import org.ei.drishti.view.UpdateActionsTask;

public class HomeActivity extends SecuredWebActivity {

    @Override
    protected void onInitialization() {
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
