package org.ei.drishti.view.activity;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.ei.drishti.R;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.view.AfterFetchListener;
import org.ei.drishti.view.NoOpProgressIndicator;
import org.ei.drishti.view.UpdateActionsTask;

public abstract class SecuredWebActivity extends SecuredActivity {
    protected WebView webView;

    @Override
    protected void onCreation() {
        setContentView(R.layout.html);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

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
                UpdateActionsTask updateActionsTask = new UpdateActionsTask(this, context.actionService(), new NoOpProgressIndicator());
                updateActionsTask.updateFromServer(new AfterFetchListener() {
                    public void afterFetch(FetchStatus status) {
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResumption() {
    }
}
