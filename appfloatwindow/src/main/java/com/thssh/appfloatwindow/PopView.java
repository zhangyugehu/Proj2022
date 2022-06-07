package com.thssh.appfloatwindow;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thssh.commonlib.utils.UI;

public class PopView extends FrameLayout {

    private final TextView textView;

    public PopView(@NonNull Context context) {
        this(context, null);
    }

    public PopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView getTextView() {
        return textView;
    }

    public PopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textView = new TextView(context);
        textView.setText("PopView");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setTextColor(Color.BLUE);
        textView.setGravity(Gravity.CENTER);
        addView(textView, new LayoutParams(UI.dpi(200), UI.dpi(200)));
    }
}
