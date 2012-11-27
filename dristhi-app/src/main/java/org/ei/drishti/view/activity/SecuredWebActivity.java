package org.ei.drishti.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
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
    private ProgressDialog progressDialog;

    @Override
    protected void onCreation() {
        setContentView(R.layout.html);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
        progressDialog.setTitle("Loading ...");
        progressDialog.show();
        webView = (WebView) findViewById(R.id.webview);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);

                if (progress == 100 && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

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

    public void updateFromServer() {
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
                if (fetched.equals(status)) {
                    updateController.reload();
                }
            }
        });
    }

    @Override
    protected void onResumption() {
    }

    //    Added to fix the memory leak caused due to bug in android which stops activities with webview to be GCed.
    //    Refer to this link for more details - http://code.google.com/p/android/issues/detail?id=9375
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.destroy();
            webView = null;
        }

        if(updateController != null)
            updateController.destroy();
    }
}
