package com.example.browser.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.browser.R;
import com.example.browser.interfaces.GenericCallback;
import com.example.browser.interfaces.RecyclerViewCallback;
import com.example.browser.models.DDModel;
import com.example.browser.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class DDAdapter extends RecyclerView.Adapter<DDAdapter.ViewHolder> {
    private RecyclerViewCallback mRecyclerViewCallback;
    private GenericCallback mRemoveCallback;
    private List<DDModel> mDataList;
    private Activity mActivity;

    public DDAdapter(Activity activity, List<DDModel> dataList, RecyclerViewCallback recyclerViewCallback) {
        if (!Utilities.has(this.mDataList)) {
            this.mDataList = new ArrayList<>();
        }
        this.mDataList.addAll(dataList);
        this.mRecyclerViewCallback = recyclerViewCallback;
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dd_list_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DDModel model = mDataList.get(position);
        holder.dataTV.setText(model.getTitle());
        if (Utilities.has(model.getImageUrl())) {
            holder.logoIV.setVisibility(View.VISIBLE);
            Utilities.fetchIconFromWebsite(mActivity, holder.logoIV, model.getImageUrl());
        } else {
            holder.logoIV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * Set adapter list and notify
     * @param dataList
     */
    public void setmDataList(List<DDModel> dataList) {
        if (!Utilities.has(this.mDataList)) {
            this.mDataList = new ArrayList<>();
        }
        this.mDataList.clear();
        this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setRemoveCallback(GenericCallback mRemoveCallback) {
        this.mRemoveCallback = mRemoveCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dataTV;
        ImageView logoIV;
        ImageView crossIV;

        public ViewHolder(final View v) {
            super(v);

            dataTV = v.findViewById(R.id.data_tv);
            logoIV = v.findViewById(R.id.logo_iv);
            crossIV = v.findViewById(R.id.cross_iv);

            if (Utilities.has(mRemoveCallback)) {
                crossIV.setVisibility(View.VISIBLE);
                crossIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRemoveCallback.callback(getAdapterPosition());
                    }
                });
            } else {
                crossIV.setVisibility(View.INVISIBLE);
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerViewCallback.onClick(getAdapterPosition());
                }
            });
        }
    }
}
