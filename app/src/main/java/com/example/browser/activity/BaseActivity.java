package com.example.browser.activity;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void addDialogFragment(DialogFragment fragment, String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, "tag");
    }

    public void addFragment(Fragment fragment, String tag, int containerId) {
        addFragment(fragment, tag, containerId, false);
    }

    public void replaceFragment(Fragment fragment, String tag, int containerId) {
        replaceFragment(fragment, tag, containerId, false);
    }

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

    public void removeFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (Utilities.has(fragment)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
        }
    }
}
