package com.thssh.touchevent3;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thssh.commonlib.activity.BaseFragment;

import java.util.Random;

public class PlaceholderFragment extends BaseFragment {

    public static Fragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        placeholderFragment.setArguments(args);
        return placeholderFragment;
    }
    TextView view;

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        view = new TextView(getContext());
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        view.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Random random = new Random();
            container.setBackgroundColor(Color.parseColor(String.format("#%d%d%d",
                    random.nextInt(89) + 10,
                    random.nextInt(89) + 10,
                    random.nextInt(89) + 10
            )));
        }
        view.setGravity(Gravity.CENTER);
        view.setText("position: " + position);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        return view;
    }
    int position = -2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position", -1);
        }
    }
}
