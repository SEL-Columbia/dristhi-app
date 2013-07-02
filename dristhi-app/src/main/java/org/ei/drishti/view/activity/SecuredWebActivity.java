package org.ei.drishti.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import org.ei.drishti.R;
import org.ei.drishti.sync.SyncAfterFetchListener;
import org.ei.drishti.sync.SyncProgressIndicator;
import org.ei.drishti.sync.UpdateActionsTask;
import org.ei.drishti.view.InternationalizationContext;
import org.ei.drishti.view.controller.NavigationController;
import org.ei.drishti.view.controller.UpdateController;
import org.ei.drishti.view.controller.VillageController;

import static android.webkit.ConsoleMessage.MessageLevel.ERROR;
import static java.text.MessageFormat.format;
import static org.ei.drishti.util.Log.logDebug;
import static org.ei.drishti.util.Log.logError;

public abstract class SecuredWebActivity extends SecuredActivity {
    protected WebView webView;
    protected UpdateController updateController;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreation() {
        setActivityLayout();

        progressDialogInitialization();
        webViewInitialization(this);

        updateController = new UpdateController(webView);

        onInitialization();
    }

    protected void setActivityLayout() {
        setContentView(R.layout.html);
    }

    protected abstract void onInitialization();

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
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(this, context.actionService(), context.formSubmissionSyncService(), new SyncProgressIndicator());
        updateActionsTask.updateFromServer(new SyncAfterFetchListener());
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

        if (updateController != null)
            updateController.destroy();
    }

    private void progressDialogInitialization() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
    }

    private void webViewInitialization(final Activity activity) {
        webView = (WebView) findViewById(R.id.webview);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);

                if (progress == 100 && progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String message = format("Javascript Log. Message: {0}, lineNumber: {1}, sourceId, {2}", consoleMessage.message(),
                        consoleMessage.lineNumber(), consoleMessage.sourceId());

                if (consoleMessage.messageLevel() == ERROR) {
                    logError(message);
                } else {
                    logDebug(message);
                }
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new NavigationController(this, context.anmService()), "navigationContext");
        webView.addJavascriptInterface(new VillageController(context.allEligibleCouples()), "villageContext");
        webView.addJavascriptInterface(new InternationalizationContext(getResources()), "internationalizationContext");
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }
}
