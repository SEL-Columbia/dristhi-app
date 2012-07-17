package org.ei.drishti.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.ei.drishti.Context;
import org.ei.drishti.R;
import org.ei.drishti.domain.Beneficiary;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.view.controller.EligibleCoupleViewContext;
import org.ei.drishti.view.domain.ECContext;
import org.ei.drishti.view.domain.ECTimeline;

import java.util.ArrayList;
import java.util.List;

public class EligibleCoupleViewActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String caseId = (String) getIntent().getExtras().get("caseId");
        Context context = Context.getInstance().updateApplicationContext(this.getApplicationContext());
        EligibleCouple eligibleCouple = context.allEligibleCouples().findByCaseID(caseId);
        List<Beneficiary> beneficiaries = context.allBeneficiaries().findByECCaseId(caseId);
        List<ECTimeline> ecTimeLines = new ArrayList<ECTimeline>();
        for (Beneficiary beneficiary : beneficiaries) {
            ecTimeLines.add(new ECTimeline(beneficiary.status().description(), new String[]{}, beneficiary.referenceDate()));
        }

        ECContext ecContext = new ECContext(eligibleCouple.wifeName(), eligibleCouple.village(), eligibleCouple.subCenter(), eligibleCouple.ecNumber(),
                false, null, eligibleCouple.currentMethod(), null, null, ecTimeLines);
        webView.addJavascriptInterface(new EligibleCoupleViewContext(ecContext, this), "context");
        webView.loadUrl("file:///android_asset/www/EC.html");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
