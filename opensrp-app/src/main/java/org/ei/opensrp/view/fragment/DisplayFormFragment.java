package org.ei.opensrp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.ei.opensrp.R;
import org.ei.opensrp.view.activity.SecuredNativeSmartRegisterActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geoffrey Koros on 9/12/2015.
 */
public class DisplayFormFragment extends Fragment {

    WebView webView;
    ProgressBar progressBar;

    private String formInputErrorMessage = "Form contains errors please try again";// externalize this

    private static final String headerTemplate = "web/forms/header";
    private static final String footerTemplate = "web/forms/footer";
    private static final String scriptFile = "web/forms/js_include.js";

    private String formName;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_form_fragment, container, false);
        webView = (WebView)view.findViewById(R.id.webview);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        initWebViewSettings();
        loadHtml();
        return view;
    }

    private void initWebViewSettings(){
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebViewClient(new AppWebViewClient(progressBar));
        webView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        webView.getSettings().setGeolocationDatabasePath(getActivity().getFilesDir().getPath());
        webView.getSettings().setDefaultTextEncodingName("utf-8");

        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(getActivity());
        webView.addJavascriptInterface(myJavaScriptInterface, "Android");
    }

    public void loadHtml(){
        showProgressDialog();
        String header = readFileAssets(headerTemplate);

        String script = readFileAssets(scriptFile);
        String modelString = readFileAssets("www/form/" + formName + "/model.xml").replaceAll("\"", "\\\\\"").replaceAll("\n", "").replaceAll("\r", "").replaceAll("/","\\\\/");;
        String form = readFileAssets("www/form/" + formName + "/form.xml");
        String footer = readFileAssets(footerTemplate);

        // inject the model and form into html template
        script = script.replace("$model_string_placeholder", modelString);
        header = header.replace("<!-- $script_placeholder >", script);

        StringBuilder sb = new StringBuilder();
        sb.append(header).append(form).append(footer);

        webView.loadDataWithBaseURL("file:///android_asset/web/forms/", sb.toString(), "text/html", "utf-8", null);
    }

    public String readFileAssets(String fileName) {
        String fileContents = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            fileContents = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        //Log.d("File", fileContents);
        return fileContents;
    }

    private void showProgressDialog(){
        webView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    String formData;
    public void setFormData(String data){
        this.formData = data;
    }

    public void loadFormData(){
        formData = formData != null ?  formData.replaceAll("\"","\\\"") : "";
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:loadDraft('"+ formData + "')");
            }
        });
    }

    private void dismissProgressDialog(){
        //dialog.dismiss();
        webView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        loadFormData();
    }

    //override this on tha child classes to override specific fields
    public Map<String,String> getFormFieldsOverrides(){
        return new HashMap<String, String>();
    }

    public class AppWebViewClient extends WebViewClient {
        private View progressBar;

        public AppWebViewClient(ProgressBar progressBar) {
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            dismissProgressDialog();
        }
    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showFormErrorToast(){
            Toast.makeText(mContext, formInputErrorMessage, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void processFormSubmission(String formSubmission){
            ((SecuredNativeSmartRegisterActivity)getActivity()).saveFormSubmission(formSubmission, recordId, formName, getFormFieldsOverrides());
        }
    }

}
