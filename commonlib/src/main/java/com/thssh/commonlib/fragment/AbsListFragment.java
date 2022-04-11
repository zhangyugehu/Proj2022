package com.thssh.commonlib.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.activity.BaseFragment;
import com.thssh.commonlib.views.ListItem;

import java.util.List;

/**
 * @author hutianhang
 */
public abstract class AbsListFragment extends BaseFragment {
    public AbsListFragment() {
        unregisterFragmentDelegate(lifeCycleDelegate);
    }

    static RecyclerView.RecycledViewPool mCachePool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = provideRecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        if (mCachePool == null) {
            mCachePool = recyclerView.getRecycledViewPool();
        } else {
            recyclerView.setRecycledViewPool(mCachePool);
        }

        recyclerView.setAdapter(new InnerAdapter(requireContext(), createData(), primaryColor()));
        return recyclerView;
    }

    protected abstract RecyclerView provideRecyclerView(Context context);

    protected abstract int primaryColor();

    protected abstract List<String> createData();

    static class InnerHolder extends RecyclerView.ViewHolder {

        private final ListItem contentView;
        int color;

        public InnerHolder(@NonNull ListItem itemView, int color) {
            super(itemView);
            contentView = itemView;
            this.color = color;
        }

        public void bindData(String data, int position) {
            contentView.setText(data);
            contentView.offsetTextColor(position);
            contentView.setBackgroundColor(color);
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
            ListItem item = new ListItem(parent.getContext(), mTextColor);
            return new InnerHolder(item, mTextColor + 0xF0F0F0);
        }

        @Override
        public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
            holder.bindData(mData.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
