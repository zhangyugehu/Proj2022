package com.thssh.appfloatwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;

import com.thssh.commonlib.logger.L;

public class PopView extends FrameLayout implements FloatRelativeLayout.IDraggable {

    private static final int IDLE_TIME = 3000;
    private final TextView vLabel, vLabelNextHold;
    private final ViewGroup vContentBox;
    private final int halfWidowWidth;

    private final Group vLeftCircleGroup, vRightCircleGroup;

    private boolean inIdleState = false;
    private int savedState;

    public PopView(@NonNull Context context) {
        this(context, null);
    }

    public PopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        halfWidowWidth = context.getResources().getDisplayMetrics().widthPixels / 2;
        LayoutInflater.from(context).inflate(R.layout.popview, this, true);
        vLeftCircleGroup = findViewById(R.id.group_circle_left);
        vRightCircleGroup = findViewById(R.id.group_circle_right);
        vLabel = findViewById(R.id.txt_content);
        vLabelNextHold = findViewById(R.id.txt_content_hold);
        vContentBox = findViewById(R.id.box_content);
    }

    private final Runnable idleAction = this::toIdleState;

    void toIdleState() {
        if (!inIdleState) {
            inIdleState = true;
            setAlpha(0.7f);
            vContentBox.setVisibility(GONE);
        }
    }

    void wakeIdle() {
        if (inIdleState) {
            inIdleState = false;
            setAlpha(1.0f);
            vContentBox.setVisibility(VISIBLE);
        }
    }

    public void setLabel(CharSequence label) {
        onTap();
        // TODO: 2022/6/14 动画
        vLabel.setText(label);
    }

    @Override
    public void onDrag(MotionEvent event) {
        L.d("onDrag");
        savedState = vContentBox.getVisibility();
        vContentBox.setVisibility(View.GONE);
    }

    @Override
    public void onRelease(MotionEvent event) {
        L.d("onRelease");
        vContentBox.setVisibility(savedState);
    }

    @Override
    public void onMove(MotionEvent event) {
        boolean nextInLeft = event.getRawX() < halfWidowWidth;
        if (nextInLeft != isInLeft) {
            isInLeft = nextInLeft;
            switchViewSide();
        }
    }

    @Override
    public void onTap() {
        removeCallbacks(idleAction);
        wakeIdle();
        postDelayed(idleAction, IDLE_TIME);
    }

    private boolean isInLeft;

    private void switchViewSide() {
        L.d("switchViewSide", isInLeft ? "Left" : "Right");
        vLeftCircleGroup.setVisibility(isInLeft ? VISIBLE : GONE);
        vRightCircleGroup.setVisibility(isInLeft ? GONE : VISIBLE);
    }

    public void setTextColor(int color) {
        vLabel.setTextColor(color);
        vLabelNextHold.setTextColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        L.d("onSizeChanged", w, h, "old", oldw, oldh);
    }
}
