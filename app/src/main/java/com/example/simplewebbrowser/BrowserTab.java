package com.example.simplewebbrowser;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserTab {

    private WebView webView;
    private String currentUrl;

    public BrowserTab(Context context) {
        webView = new WebView(context);
        setupWebView();
    }

    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

    public void loadUrl(String url) {
        currentUrl = url;
        webView.loadUrl(url);
    }

    public WebView getWebView() {
        return webView;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void destroy() {
        webView.removeAllViews();
        webView.destroy();
    }
}
