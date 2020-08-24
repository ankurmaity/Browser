package com.example.browser;

import android.app.Application;
import android.content.Context;

import com.example.browser.fragment.BrowseFragment;

import java.util.ArrayList;

/**
 * @author ankur
 */
public class ApplicationClass extends Application {
    private static ApplicationClass instance;
    public boolean isNightMode = false;
    public BrowseFragment activeTab;
    public ArrayList<BrowseFragment> tabList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ApplicationClass getInstance() {
        return instance;
    }


}
