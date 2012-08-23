package org.ei.drishti.view.activity;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.ei.drishti.R;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.view.AfterFetchListener;
import org.ei.drishti.view.ProgressIndicator;
import org.ei.drishti.view.UpdateActionsTask;
import org.ei.drishti.view.controller.NavigationController;
import org.ei.drishti.view.controller.UpdateController;

import static org.ei.drishti.domain.FetchStatus.fetched;

public abstract class SecuredWebActivity extends SecuredActivity {
    protected WebView webView;
    protected UpdateController updateController;

    @Override
    protected void onCreation() {
        setContentView(R.layout.html);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new NavigationController(this, context.anmService()), "navigationContext");

        updateController = new UpdateController(webView);

        onInitialization();
    }

    protected abstract void onInitialization();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateMenuItem:
                updateFromServer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void updateFromServer() {
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(this, context.actionService(), new ProgressIndicator() {
            @Override
            public void setVisibile() {
                updateController.startProgressIndicator();
            }

            @Override
            public void setInvisible() {
                updateController.stopProgressIndicator();
            }
        });

        updateActionsTask.updateFromServer(new AfterFetchListener() {
            public void afterFetch(FetchStatus status) {
                if (status.equals(fetched)) {
                    updateController.reload();
                }
            }
        });
    }

    @Override
    protected void onResumption() {
    }
}
