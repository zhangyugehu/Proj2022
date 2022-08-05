package com.tsh.chart.percent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.tsh.chart.R;
import com.tsh.chart.data.SimpleChartData;
import com.tsh.chart.data.PercentChartEntry;
import com.tsh.chart.legend.LegendInflater;

import java.util.ArrayList;
import java.util.List;

public class LinePercentGroupChart extends LinearLayout {

    final LegendInflater<PercentChartEntry> DEFAULT_LABEL_INFLATER = (context, data, index, hide) -> {
        TextView textView = new TextView(context);
        textView.setMaxLines(1);
        textView.setTextSize(13);
        textView.setTextColor(ContextCompat.getColor(context, R.color.chart_sub_legend_text_color));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(textView,
                6, 16, 1,
                TypedValue.COMPLEX_UNIT_SP);
        textView.setText(data.first);
        return textView;
    };

    LegendInflater<PercentChartEntry> labelInflater = DEFAULT_LABEL_INFLATER;

    SimpleChartData<PercentChartEntry> data;

    int radiusBackgroundColor;
    float radius;

    final List<Pair<View, LinePercentView>> childViews;

    public LinePercentGroupChart(Context context) {
        this(context, null);
    }

    public LinePercentGroupChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePercentGroupChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        childViews = new ArrayList<>();
        setOrientation(LinearLayout.VERTICAL);

        int paddingVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                14, getResources().getDisplayMetrics());
        int paddingHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                14, getResources().getDisplayMetrics());
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        setPadding(
                Math.max(paddingHorizontal, getPaddingStart()),
                Math.max(paddingVertical, getPaddingTop()),
                Math.max(paddingHorizontal, getPaddingEnd()),
                Math.max(paddingVertical, getPaddingBottom())
        );
        radiusBackgroundColor = ContextCompat.getColor(context, R.color.chart_background_color);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(radiusBackgroundColor);
        gradientDrawable.setCornerRadius(radius);
        setBackground(gradientDrawable);
    }

    public void setData(SimpleChartData<PercentChartEntry> data) {
        this.data = data;
        List<PercentChartEntry> entries;
        List<CharSequence> xValues;
        removeAllViews();
        childViews.clear();
        for (int i = 0; data != null &&
                (entries = data.getData()) != null &&
                (xValues = data.getxValues()) != null && i < entries.size(); i++) {
            PercentChartEntry chartEntry = entries.get(i);
            CharSequence xValue = null;
            if (i < xValues.size()) {
                xValue = xValues.get(i);
            }
            addItemView(chartEntry, xValue, i);
        }
    }

    private void addItemView(PercentChartEntry chartEntry, CharSequence xValue, int i) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinePercentView percentView = new LinePercentView(getContext());
        float labelWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        percentView.setPercent(chartEntry.getPercent());
        percentView.setColor(chartEntry.getColor());
        View labelView = null;
        if (labelInflater != null) {
            labelView = labelInflater.inflate(getContext(), Pair.create(xValue, chartEntry), i, percentView.isHideDetail());
            linearLayout.addView(labelView, new LayoutParams((int) labelWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        lp.topMargin = lp.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        lp.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        linearLayout.addView(percentView, lp);
        addView(linearLayout);
        childViews.add(Pair.create(labelView, percentView));
    }

    public void hideDetail(boolean hide) {
        List<Animator> animators = new ArrayList<>();
        for (Pair<View, LinePercentView> childView : childViews) {
            if (!hide) {
                animators.add(childView.second.getAnimator());
            }
            childView.second.setHideDetail(hide);
            childView.second.invalidate();
        }
        if (!hide) {
            AnimatorSet as = new AnimatorSet();
            as.playTogether(animators);
            as.start();
        }
    }
}
