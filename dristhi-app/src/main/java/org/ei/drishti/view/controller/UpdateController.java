package org.ei.drishti.view.controller;

import android.webkit.WebView;
import com.google.gson.Gson;
import org.ei.drishti.Context;
import org.ei.drishti.domain.ANM;
import org.ei.drishti.view.contract.HomeContext;

import java.util.ArrayList;
import java.util.List;

public class UpdateController {
    private WebView webView;
    private boolean pageHasFinishedLoading = false;
    private List<String> urlsToLoad;

    public UpdateController(WebView webView) {
        this.webView = webView;
        urlsToLoad = new ArrayList<String>();
    }

    public void pageHasFinishedLoading() {
        pageHasFinishedLoading = true;
        flushUrlLoads();
    }

    public void startProgressIndicator() {
        loadUrl("javascript:pageView.startProgressIndicator()");
    }

    public void stopProgressIndicator() {
        loadUrl("javascript:pageView.stopProgressIndicator()");
    }

    private void loadUrl(String url) {
        saveUrlLoad(url);
        flushUrlLoads();
    }

    private void saveUrlLoad(String urlToLoad) {
        urlsToLoad.add(urlToLoad);
    }

    private void flushUrlLoads() {
        if (pageHasFinishedLoading) {
            for (String url : urlsToLoad) {
                if (webView != null)
                    webView.loadUrl(url);
            }
        }
    }

    public void destroy() {
        webView = null;
    }

    public void updateANMDetails() {
        UpdateANMDetailsTask task = new UpdateANMDetailsTask(Context.getInstance().anmService());
        task.fetch(new AfterANMDetailsFetchListener() {
            @Override
            public void afterFetch(ANM anm) {
                String anmDetailsJSON = new Gson().toJson(new HomeContext(anm));
                if (webView != null)
                    webView.loadUrl("javascript:pageView.updateANMDetails('" + anmDetailsJSON + "')");
            }
        });
    }
}

