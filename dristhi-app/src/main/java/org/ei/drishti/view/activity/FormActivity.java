package org.ei.drishti.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.apache.commons.io.IOUtils;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.util.LogContext;

import java.io.IOException;
import java.text.MessageFormat;

import static java.util.UUID.randomUUID;
import static org.ei.drishti.AllConstants.*;
import static org.ei.drishti.util.Log.logError;

public class FormActivity extends Activity {
    private ProgressDialog progressDialog;
    private String model;
    private String form;
    private String formName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_layout);

        try {
            getIntentData();
        } catch (IOException e) {
            logError(e.toString());
            finish();
        }
        progressDialogInitialization();
        webViewInitialization(this);
    }

    private void getIntentData() throws IOException {
        Intent intent = getIntent();
        formName = intent.getStringExtra(FORM_NAME_PARAMETER);
        model = IOUtils.toString(getAssets().open("www/form/" + formName + "/model.xml"));
        form = IOUtils.toString(getAssets().open("www/form/" + formName + "/form.xml"));
    }

    private void progressDialogInitialization() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
    }

    private void webViewInitialization(final Activity activity) {
        WebView webView = (WebView) findViewById(R.id.webview);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);

                if (progress == 100 && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new FormWebInterface(model, form), "androidContext");
        webView.addJavascriptInterface(Context.getInstance().formDataService(), "formDataRepositoryContext");
        webView.addJavascriptInterface(new LogContext(), "logContext");
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        webView.loadUrl(MessageFormat.format("file:///android_asset/www/form/template.html?{0}={1}&{2}={3}&{4}={5}", FORM_NAME_PARAMETER, formName, ENTITY_ID_PARAMETER, "df8e94dd-91bd-40d2-a82a-fb7402e97f30", ID_PARAM, randomUUID()));
    }
}
