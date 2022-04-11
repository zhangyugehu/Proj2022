package com.thssh.commonlib.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.thssh.commonlib.logger.L;

import java.util.ArrayList;
import java.util.List;

public class NamedFragment extends AbsListFragment {

    private static final String ARGS_NAME = "ARGS_NAME";
    private static final String ARGS_COLOR = "ARGS_COLOR";

    public static NamedFragment newInstance(String name, int color) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_NAME, name);
        bundle.putInt(ARGS_COLOR, color);
        NamedFragment fragment = new NamedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    String name;
    int color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalArgumentException("args null");
        }
        name = args.getString(ARGS_NAME);
        color = args.getInt(ARGS_COLOR);
    }

    @Override
    protected RecyclerView provideRecyclerView(Context context) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setViewCacheExtension(new RecyclerView.ViewCacheExtension() {
            @Nullable
            @Override
            public View getViewForPositionAndType(@NonNull RecyclerView.Recycler recycler, int position, int type) {
                L.d("ViewCacheExtension", recycler.hashCode(), position, type);
                return null;
            }
        });
        return recyclerView;
    }

    @Override
    protected int primaryColor() {
        return color;
    }

    @Override
    protected List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(name + "-" + (i + 1));
        }
        return data;
    }
}
