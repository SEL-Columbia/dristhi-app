package org.ei.drishti.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.ei.drishti.R;
import org.ei.drishti.sync.SyncAfterFetchListener;
import org.ei.drishti.sync.SyncProgressIndicator;
import org.ei.drishti.sync.UpdateActionsTask;
import org.ei.drishti.view.InternationalizationContext;
import org.ei.drishti.view.controller.CameraController;
import org.ei.drishti.view.controller.NavigationController;
import org.ei.drishti.view.controller.UpdateController;

import java.io.File;

public abstract class SecuredWebActivity extends SecuredActivity {
    public static final int TAKE_PHOTO_REQUEST_CODE = 111;
    protected WebView webView;
    protected UpdateController updateController;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreation() {
        setContentView(R.layout.html);

        progressDialogInitialization();
        webViewInitialization(this);

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
        UpdateActionsTask updateActionsTask = new UpdateActionsTask(this, context.actionService(), new SyncProgressIndicator());

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
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new NavigationController(this, context.anmService()), "navigationContext");
        webView.addJavascriptInterface(new InternationalizationContext(getResources()), "internationalizationContext");
        webView.addJavascriptInterface(new CameraController(this), "cameraContext");
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != TAKE_PHOTO_REQUEST_CODE)
            return;
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                "org.ei.dristhi.woman_photos"
        );

    }
}
