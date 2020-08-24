package com.example.browser.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.browser.ApplicationClass;
import com.example.browser.R;
import com.example.browser.fragment.BrowseFragment;
import com.example.browser.fragment.DropdownDialog;
import com.example.browser.interfaces.GenericCallback;
import com.example.browser.models.DDModel;
import com.example.browser.models.DataConversion;
import com.example.browser.utils.Constants;
import com.example.browser.utils.Utilities;


public class BrowserActivity extends BaseActivity implements TextView.OnEditorActionListener {
    public String TAG = "BrowserActivity";

    private Button clearSearchBtn;
    private EditText searchET;
    private ImageView searchEngineIV;
    private ApplicationClass app = ApplicationClass.getInstance();
    private String searchEngine = Constants.GOOGLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (app.isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_browser);

        init();
    }

    /**
     * Initialize views
     */
    private void init() {

        searchET = findViewById(R.id.search_et);
        searchET.setOnEditorActionListener(this);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clearSearchBtn.setVisibility(Utilities.has(charSequence + "") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clearSearchBtn = findViewById(R.id.clear_search_btn);
        clearSearchBtn.setOnClickListener(this);
        clearSearchBtn.setVisibility(Utilities.has(searchET.getText() + "") ? View.VISIBLE : View.GONE);

        searchEngineIV = findViewById(R.id.search_engine_iv);
        searchEngineIV.setOnClickListener(this);
        Utilities.fetchIconFromWebsite(BrowserActivity.this, searchEngineIV, getString(R.string.url_google));

        findViewById(R.id.tab).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.home).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.mode).setOnClickListener(this);
        if (Utilities.has(app.activeTab)) {
            ((TextView) findViewById(R.id.tab_count)).setText((app.tabList.indexOf(app.activeTab) + 1) + "");
            replaceFragment(app.activeTab, app.activeTab.getTag(), R.id.body);
        } else {
            newTab();
        }

        if (ApplicationClass.getInstance().isNightMode) {
            ((ImageView) findViewById(R.id.mode)).setImageDrawable(getDrawable(R.drawable.ic_night));
        } else {
            ((ImageView) findViewById(R.id.mode)).setImageDrawable(getDrawable(R.drawable.ic_day));
        }
    }

    /**
     * Open new Tab
     */
    private void newTab() {
        app.activeTab = new BrowseFragment();
        app.tabList.add(app.activeTab);
        addFragment(app.activeTab, Utilities.getRandomString(6), R.id.body);
        searchET.setText("");
        ((TextView) findViewById(R.id.tab_count)).setText(app.tabList.size() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_engine_iv:
                DropdownDialog fragment = new DropdownDialog(new GenericCallback() {
                    @Override
                    public void callback(Object o) {
                        Utilities.fetchIconFromWebsite(BrowserActivity.this, searchEngineIV, ((DDModel) o).getImageUrl());
                        searchEngine = ((DDModel) o).getData().toString();
                    }
                }, "Search Engine", new DataConversion().fetchSearchEngineList());
                addDialogFragment(fragment, "SEARCH_ENGINE", false);
                break;
            case R.id.clear_search_btn:
                searchET.setText("");
                break;
            case R.id.tab:
                DropdownDialog tabListDialog = new DropdownDialog(new GenericCallback() {
                    @Override
                    public void callback(Object o) {
                        BrowseFragment browseFragment = (BrowseFragment) ((DDModel) o).getData();
                        loadTab(browseFragment);
                    }
                }, "Open Tabs", new DataConversion().fetchTabDDList(app.tabList));
                tabListDialog.setmRemoveCallback(removeTabCallback);
                addDialogFragment(tabListDialog, "TAB_LIST", false);
                break;
            case R.id.add:
                newTab();
                break;
            case R.id.home:
                app.activeTab.clearWebView();
                searchET.setText("");
                break;
            case R.id.search:
                searchET.setText("");
                searchET.requestFocus();
                showKeyboard(searchET);
                break;
            case R.id.mode:
                ApplicationClass.getInstance().isNightMode = !ApplicationClass.getInstance().isNightMode;
                if (ApplicationClass.getInstance().isNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                startActivity(getIntent());
                overridePendingTransition(0, 0);

                break;
        }
    }

    /**
     * Load particular tab
     * @param browseFragment
     */
    private void loadTab(BrowseFragment browseFragment) {
        replaceFragment(browseFragment, browseFragment.getTag(), R.id.body);
        app.activeTab = browseFragment;
        if (browseFragment.getURL().equalsIgnoreCase("New Tab")) {
            searchET.setText("");
        } else {
            searchET.setText(browseFragment.getURL());
        }
        ((TextView) findViewById(R.id.tab_count)).setText((app.tabList.indexOf(browseFragment) + 1) + "");
    }

    /**
     * Callback will be triggered when a tab is removed
     */
    private GenericCallback removeTabCallback = new GenericCallback() {
        @Override
        public void callback(Object o) {
            int pos = (int) o;
            app.tabList.remove(pos);
            if (Utilities.has(app.tabList)) {
                loadTab(app.tabList.get(app.tabList.size() - 1));
            } else {
                newTab();
            }
        }
    };

    @Override
    public boolean onEditorAction(TextView view, int id, KeyEvent keyEvent) {
        switch (id) {
            case EditorInfo.IME_ACTION_NEXT:
                app.activeTab.processInput(view.getText() + "");
                hideKeyboard();
                view.clearFocus();
                return true;
        }
        return false;
    }

    public void setUrlToSearchBar(String url) {
        searchET.setText(url);
    }

    public String getSearchEngine() {
        return searchEngine;
    }

}
