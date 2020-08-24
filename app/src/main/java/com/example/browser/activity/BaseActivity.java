package com.example.browser.activity;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.browser.utils.Utilities;

/**
 * @author ankur
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Hide keyboard
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Show keyboard
     * @param view
     */
    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Add DialogFragment
     * @param fragment
     * @param tag
     * @param addToBackStack
     */
    public void addDialogFragment(DialogFragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, "tag");
    }

    /**
     * Add fragment
     * @param fragment
     * @param tag
     * @param containerId
     */
    public void addFragment(Fragment fragment, String tag, int containerId) {
        addFragment(fragment, tag, containerId, false);
    }

    /**
     * Add fragment from don't add to back stack
     * @param fragment
     * @param tag
     * @param containerId
     */
    public void addFragment(Fragment fragment, String tag, int containerId, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerId, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    /**
     * Replace Fragment
     * @param fragment
     * @param tag
     * @param containerId
     */
    public void replaceFragment(Fragment fragment, String tag, int containerId) {
        replaceFragment(fragment, tag, containerId, false);
    }

    /**
     * Replace fragment from don't add to back stack
     * @param fragment
     * @param tag
     * @param containerId
     * @param addToBackStack
     */
    public void replaceFragment(Fragment fragment, String tag, int containerId, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    /**
     * Remove fragment on basis of tag
     * @param tag
     */
    public void removeFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (Utilities.has(fragment)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
        }
    }
}
