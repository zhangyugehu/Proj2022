package com.thssh.commonlib.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.logger.L;

import java.util.UUID;

public class ListItem extends LinearLayout {
    int mTextColor;
    private TextView textView;

    public ListItem(@NonNull Context context, int color) {
        this(context, null);
        this.mTextColor = color;
        init();
    }

    public ListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public ListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textView = new TextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView.setTextColor(mTextColor);
        addView(textView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setText(String data) {
        textView.setText(data);
    }


    String specialKey;
    public void setSpecialKey(String key) {
        this.specialKey = key;
    }

    public String getSpecialKey() {
        return specialKey;
    }

    public void offsetTextColor(int position) {
        textView.setTextColor(mTextColor + (int) (position*1.5));
    }
}
