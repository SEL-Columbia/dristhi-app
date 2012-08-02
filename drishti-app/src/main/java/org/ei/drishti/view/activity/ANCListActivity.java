package org.ei.drishti.view.activity;

public class ANCListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.loadUrl("file:///android_asset/www/anc_list.html");
    }
}
