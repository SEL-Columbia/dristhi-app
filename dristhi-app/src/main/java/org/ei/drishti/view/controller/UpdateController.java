package org.ei.drishti.view.controller;

import android.webkit.WebView;

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
        loadUrl("javascript:startProgressIndicator()");
    }

    public void stopProgressIndicator() {
        loadUrl("javascript:stopProgressIndicator()");
    }

    public void reload() {
        loadUrl("javascript:reload()");
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
                webView.loadUrl(url);
            }
        }
    }
}
