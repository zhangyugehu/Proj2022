package com.tsh.chart.percent;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.R;

public class CirclePercentChart extends LinearLayout implements IAnimatorChart {

    final LabelCirclePercentView vPercentView;

    final TextView vTitle, vSubTitle;

    int radiusBackgroundColor;
    int circleViewSize;
    int paddingVertical;
    float radius;
    float titleTextSize;
    float subTitleTextSize;
    int titleTextColor;
    int subTitleTextColor;
    int titleTextHideColor;
    int subTitleTextHideColor;
    CharSequence title, subTitle;
    CharSequence titleHide = "0.00", subTitleHide = "0.00%";

    public CirclePercentChart(@NonNull Context context) {
        this(context, null);
    }

    public CirclePercentChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentChart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        try (TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentChart)) {
            circleViewSize = (int) typedArray.getDimension(R.styleable.CirclePercentChart_circlePercentSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55f, getResources().getDisplayMetrics()));
            paddingVertical = (int) typedArray.getDimension(R.styleable.CirclePercentChart_circlePercentPaddingVertical,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13f, getResources().getDisplayMetrics()));
            titleTextSize = (int) typedArray.getDimension(R.styleable.CirclePercentChart_circlePercentTitleSize, 12f);
            subTitleTextSize = (int) typedArray.getDimension(R.styleable.CirclePercentChart_circlePercentSubTitleSize,
                    titleTextSize);
            titleTextColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentTitleColor,
                    ContextCompat.getColor(context, R.color.chart_legend_text_color));
            subTitleTextColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentSubTitleColor,
                    titleTextColor);
            titleTextHideColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentTitleHideColor,
                    ContextCompat.getColor(context, R.color.chart_sub_legend_text_color));
            subTitleTextHideColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentSubTitleHideColor,
                    titleTextHideColor);
            radiusBackgroundColor = typedArray.getColor(R.styleable.CirclePercentChart_circlePercentBackgroundColor,
                    ContextCompat.getColor(context, R.color.chart_background_color));
            radius = (int) typedArray.getDimension(R.styleable.CirclePercentChart_circlePercentRadius,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
            title = typedArray.getString(R.styleable.CirclePercentChart_circlePercentTitle);
            if (typedArray.hasValue(R.styleable.CirclePercentChart_circlePercentTitleHide)) {
                titleHide = typedArray.getString(R.styleable.CirclePercentChart_circlePercentTitleHide);
            }
            subTitle = typedArray.getString(R.styleable.CirclePercentChart_circlePercentSubTitle);
            if (typedArray.hasValue(R.styleable.CirclePercentChart_circlePercentSubTitleHide)) {
                subTitleHide = typedArray.getString(R.styleable.CirclePercentChart_circlePercentSubTitleHide);
            }
        }

        LayoutParams circleLayoutParams = new LayoutParams(circleViewSize, circleViewSize);
        addView(vPercentView = new LabelCirclePercentView(context, attrs, defStyleAttr), circleLayoutParams);
        addView((vTitle = new TextView(context)),
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vTitle.setTextColor(titleTextColor);
        vTitle.setTextSize(titleTextSize);
        vTitle.setText(title);
        addView((vSubTitle = new TextView(context)),
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vSubTitle.setTextColor(subTitleTextColor);
        vSubTitle.setTextSize(subTitleTextSize);
        vSubTitle.setText(subTitle);

        setPadding(0, paddingVertical, 0, paddingVertical);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(radiusBackgroundColor);
        gradientDrawable.setCornerRadius(radius);
        setBackground(gradientDrawable);
    }

    private static final String TAG = "CirclePercentChart";
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getAnimator().start();
    }

    public void hideDetail(boolean hide) {
        vPercentView.setHideDetail(hide);
        vPercentView.invalidate();
        if (hide) {
            vTitle.setTextColor(titleTextHideColor);
            vTitle.setText(titleHide);
            vSubTitle.setTextColor(subTitleTextHideColor);
            vSubTitle.setText(subTitleHide);
        } else {
            vPercentView.showAnimator();
            vTitle.setTextColor(titleTextColor);
            vTitle.setText(title);
            vSubTitle.setTextColor(subTitleTextColor);
            vSubTitle.setText(subTitle);
        }
    }

    public void setValueColor(int color) {
        vSubTitle.setTextColor(color);
        vTitle.setTextColor(color);
    }

    public void setValueTextSize(float textSize) {
        vSubTitle.setTextSize(textSize);
    }

    public void setPercentTextSize(float textSize) {
        vSubTitle.setTextSize(textSize);
    }

    public void setTitle(CharSequence value) {
        this.title = value;
        vTitle.setText(value);
    }

    public void setTitleHide(CharSequence titleHide) {
        this.titleHide = titleHide;
    }

    public void setTitleColor(int color) {
        vTitle.setTextColor(color);
    }

    public void setSubTitle(CharSequence subTitle) {
        this.subTitle = subTitle;
        vSubTitle.setText(subTitle);
    }

    public void setSubTitleHide(CharSequence subTitleHide) {
        this.subTitleHide = subTitleHide;
    }

    public TextView getAmountLabel() {
        return vTitle;
    }

    public TextView getPercentLabel() {
        return vSubTitle;
    }

    public LabelCirclePercentView getPercentView() {
        return vPercentView;
    }

    @Override
    public Animator getAnimator() {
        return vPercentView.getAnimator();
    }
}
