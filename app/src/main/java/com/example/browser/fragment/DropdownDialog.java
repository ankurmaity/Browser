package com.example.browser.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.browser.R;
import com.example.browser.adapter.DDAdapter;
import com.example.browser.interfaces.GenericCallback;
import com.example.browser.interfaces.RecyclerViewCallback;
import com.example.browser.models.DDModel;
import com.example.browser.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ankur
 */
public class DropdownDialog extends DialogFragment implements View.OnClickListener {

    private GenericCallback mGenericCallback;
    private GenericCallback mRemoveCallback;
    private String mTitle;
    private List<DDModel> mDataList;
    private View mView;

    public DropdownDialog(GenericCallback genericCallback, String title, List dataList) {
        this.mGenericCallback = genericCallback;
        this.mTitle = title;
        this.mDataList = dataList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dd, container, false);

        if (Utilities.has(mTitle)) {
            ((TextView) mView.findViewById(R.id.title)).setText(mTitle);
        }

        mView.findViewById(R.id.close_iv).setOnClickListener(this);
        setListAdapter();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        int width = displayMetrics.widthPixels;
        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    /**
     * Set lit to adapter
     */
    private void setListAdapter() {
        RecyclerView recyclerView = mView.findViewById(R.id.dd_recycler);

        if (!Utilities.has(mDataList)) {
            mDataList = new ArrayList();
        }

        final DDAdapter ddAdapter = new DDAdapter(getActivity(), mDataList, new RecyclerViewCallback() {
            @Override
            public void onClick(int position) {
                mGenericCallback.callback(mDataList.get(position));
                DropdownDialog.this.dismiss();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        ddAdapter.setRemoveCallback(new GenericCallback() {
            @Override
            public void callback(Object o) {
                mDataList.remove((int) o);
                if (Utilities.has(mDataList)) {
                    ddAdapter.setmDataList(mDataList);
                } else {
                    DropdownDialog.this.dismiss();
                }
                mRemoveCallback.callback(o);
            }
        });
        recyclerView.setAdapter(ddAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                DropdownDialog.this.dismiss();
                break;
        }

    }

    public void setmRemoveCallback(GenericCallback mRemoveCallback) {
        this.mRemoveCallback = mRemoveCallback;
    }
}
