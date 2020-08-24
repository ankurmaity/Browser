package com.example.browser.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.browser.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class BaseFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    public View mView;

    protected void listen(int id) {
        mView.findViewById(id).setOnClickListener(this);
    }

    protected void focus(int id) {
        mView.findViewById(id).setOnFocusChangeListener(this);
    }

    protected View getView(int id) {
        return mView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView.setClickable(true);
        return mView;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    protected void showResponseMessage(String data) {
        if (!Utilities.has(data)) {
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(data);

            if (jsonObject.has("message") && Utilities.has(jsonObject.getString("message"))) {
                Utilities.makeToast(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
