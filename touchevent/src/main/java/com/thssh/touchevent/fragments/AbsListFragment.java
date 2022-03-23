package com.thssh.touchevent.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.activity.BaseFragment;
import com.thssh.touchevent.RecyclerLayout;

import java.util.List;

public abstract class AbsListFragment extends BaseFragment {
    public AbsListFragment() {
        unregisterFragmentDelegate(lifeCycleDelegate);
    }

    RecyclerLayout recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerLayout(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(new InnerAdapter(requireContext(), createData(), primaryColor()));
        return recyclerView;
    }

    protected abstract int primaryColor();

    protected abstract List<String> createData();

    static class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView contentView;

        public InnerHolder(@NonNull TextView itemView) {
            super(itemView);
            contentView = itemView;
        }

        public void bindData(String data) {
            contentView.setText(data);
        }
    }

    static class InnerAdapter extends RecyclerView.Adapter<InnerHolder> {

        Context mContext;
        List<String> mData;
        int mTextColor;

        public InnerAdapter(Context context, List<String> data, int textColor) {
            this.mData = data;
            this.mContext = context;
            this.mTextColor = textColor;
        }

        @NonNull
        @Override
        public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            textView.setTextColor(mTextColor);
            return new InnerHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
            holder.bindData(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
