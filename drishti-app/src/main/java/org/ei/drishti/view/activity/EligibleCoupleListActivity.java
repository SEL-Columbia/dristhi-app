package org.ei.drishti.view.activity;

import android.webkit.WebView;
import org.ei.drishti.view.controller.EligibleCoupleListViewController;

public class EligibleCoupleListActivity extends SecuredWebActivity {
    @Override
    protected void onInitialization() {
        webView.addJavascriptInterface(new EligibleCoupleListViewController(context.allEligibleCouples(), this), "context");
        webView.loadUrl("file:///android_asset/www/ec_list.html");
    }
}
