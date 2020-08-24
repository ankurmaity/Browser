package com.example.browser.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.browser.R;
import com.example.browser.activity.BrowserActivity;
import com.example.browser.interfaces.GenericCallback;
import com.example.browser.utils.Constants;
import com.example.browser.utils.Log;
import com.example.browser.utils.MyWebViewClient;
import com.example.browser.utils.Utilities;

public class BrowseFragment extends BaseFragment {
    private String TAG = "BrowseFragment";

    private WebView webView;
    private ProgressBar progressBar;
    private boolean isFirstLoad = true;

    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (isFirstLoad) {
            mView = inflater.inflate(R.layout.fragment_browse, container, false);

            init(savedInstanceState);
            isFirstLoad = false;
        }
        return mView;
    }

    /**
     * Initialize
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        webView = mView.findViewById(R.id.web_view);
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }

    }

    /**
     * Load url to web view
     * @param input
     */
    private void callURL(String input) {
        webView.loadUrl(input);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setBackgroundColor(Color.WHITE);

        progressBar=mView.findViewById(R.id.progress_bar);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //Handle progress bar
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                if (newProgress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                } else {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
            }


        });

        webView.setWebViewClient(new MyWebViewClient(urlChangeCallback));

        try {
            if (!Utilities.isNetworkAvailable(getActivity())) {
                Toast.makeText(getActivity(), R.string.internet_not_available, Toast.LENGTH_SHORT).show();
            } else {
                if (!input.startsWith("http")) {
                    input = "https://" + input;
                }
                Log.d(TAG, input);
                webView.loadUrl(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Get search text or url and identify for processing
     * @param input
     */
    public void processInput(String input) {
        boolean isValidURL = Patterns.WEB_URL.matcher(input).matches();
        Log.d(TAG, input + "--Valid URL-" + isValidURL);

        if (isValidURL) {
            callURL(input);
        } else {
            seachText(input);
        }
    }

    /**
     * Process search text based on the search engine selected
     * @param input
     */
    private void seachText(String input) {
        String url = getActivity().getString(R.string.url_google_search);
        switch (((BrowserActivity) getActivity()).getSearchEngine()) {
            case Constants.GOOGLE:
                url = getString(R.string.url_google_search);
                break;
            case Constants.YAHOO:
                url = getString(R.string.url_yahoo_search);
                break;
            case Constants.WIKI:
                url = getString(R.string.url_wiki_search);
                break;
        }
        callURL(url + input);
    }


    /**
     * Call back when url is changed internally in webview
     */
    private GenericCallback urlChangeCallback = new GenericCallback() {
        @Override
        public void callback(Object url) {
            if (Utilities.has(url) && url instanceof String) {
                ((BrowserActivity) getActivity()).setUrlToSearchBar(url.toString());
            }
        }
    };

    @Override
    public void onClick(View view) {

    }

    /**
     * Get url from web view
     * @return
     */
    public String getURL() {
        if (Utilities.has(webView.getUrl())) {
            if (webView.getTitle().equalsIgnoreCase("about:blank")) {
                return "New Tab";
            }
            return webView.getUrl();
        } else {
            return "New Tab";
        }
    }

    /**
     * Get title of the website opened
     * @return
     */
    public String getTitle() {
        if (Utilities.has(webView.getTitle())) {
            if (webView.getTitle().equalsIgnoreCase("about:blank")) {
                return "New Tab";
            } else {
                return webView.getTitle();
            }
        } else {
            return "New Tab";
        }
    }

    /**
     * Clear wensite from Web view
     */
    public void clearWebView() {
        if (Utilities.has(webView.getUrl())) {
            webView.loadUrl("about:blank");
        }
    }
}
