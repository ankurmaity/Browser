package com.example.browser.models;

import com.example.browser.ApplicationClass;
import com.example.browser.R;
import com.example.browser.fragment.BrowseFragment;
import com.example.browser.utils.Constants;
import com.example.browser.utils.Utilities;

import java.util.ArrayList;

/**
 * @author ankur
 */
public class DataConversion {

    public ArrayList<DDModel> fetchSearchEngineList() {
        ArrayList<DDModel> engineList = new ArrayList<>();
        ApplicationClass applicationClass = ApplicationClass.getInstance();

        engineList.add(new DDModel(applicationClass.getResources().getString(R.string.google),
                applicationClass.getResources().getString(R.string.url_google), Constants.GOOGLE));

        engineList.add(new DDModel(applicationClass.getResources().getString(R.string.yahoo),
                applicationClass.getResources().getString(R.string.url_yahoo), Constants.YAHOO));

        engineList.add(new DDModel(applicationClass.getResources().getString(R.string.wiki),
                applicationClass.getResources().getString(R.string.url_wiki), Constants.WIKI));

        return engineList;
    }

    public ArrayList<DDModel> fetchTabDDList(ArrayList<BrowseFragment> tabList) {
        ArrayList<DDModel> tabDDList = new ArrayList<>();
        ApplicationClass applicationClass = ApplicationClass.getInstance();

        for (BrowseFragment fragment : tabList) {
            tabDDList.add(new DDModel(fragment.getTitle(),
                    fragment.getURL(), fragment));
        }

        return tabDDList;
    }
}
