package com.example.browser.utils;

import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.browser.interfaces.GenericCallback;

public class MyWebViewClient extends WebViewClient {
    private GenericCallback mGenericCallback;

    public MyWebViewClient(GenericCallback genericCallback) {
        this.mGenericCallback = genericCallback;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        mGenericCallback.callback(url);
        CookieManager.getInstance().setAcceptCookie(true);
        return true;
    }
}