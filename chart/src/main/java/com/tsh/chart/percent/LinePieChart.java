package com.tsh.chart.percent;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tsh.chart.PointTextView;
import com.tsh.chart.R;
import com.tsh.chart.data.SimpleChartData;
import com.tsh.chart.data.PercentChartEntry;
import com.tsh.chart.legend.LegendInflater;
import com.tsh.chart.pie.LinePieView;

import java.util.List;
import java.util.Locale;

public class LinePieChart extends LinearLayout {

    static final LegendInflater<PercentChartEntry> DEFAULT_LEGEND_INFLATER = (context, data, index, hide) -> {
        PointTextView pointTextView = new PointTextView(context);
        pointTextView.setTextSize(13);
        pointTextView.setTextColor(ContextCompat.getColor(context, R.color.chart_sub_legend_text_color));
        pointTextView.setColor(data.second.getColor());
        pointTextView.setText(data.first);
        pointTextView.append("  ");
        SpannableString spannablePercent = new SpannableString(
                String.format(Locale.US, "%.2f%%", hide?0:data.second.getPercent()));
        spannablePercent.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.chart_legend_text_color)), 0, spannablePercent.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        pointTextView.append(spannablePercent);
        return pointTextView;
    };

    SimpleChartData<PercentChartEntry> data;
    LinePieView vLinePieView;
    LinearLayout vLegendRoot;
    LegendInflater<PercentChartEntry> legendInflater = DEFAULT_LEGEND_INFLATER;

    int radiusBackgroundColor;
    float radius;

    public LinePieChart(Context context) {
        this(context, null);
    }

    public LinePieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinePieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);

        addView((vLinePieView = new LinePieView(context, attrs, defStyleAttr)));
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        addView((vLegendRoot = new LinearLayout(context, attrs, defStyleAttr)), lp);
        vLegendRoot.setOrientation(LinearLayout.HORIZONTAL);

        int paddingVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int paddingHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());
        setPadding(
                Math.max(paddingHorizontal, getPaddingStart()),
                Math.max(paddingVertical, getPaddingTop()),
                Math.max(paddingHorizontal, getPaddingEnd()),
                Math.max(paddingVertical, getPaddingBottom())
        );

        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        radiusBackgroundColor = ContextCompat.getColor(context, R.color.chart_background_color);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(radiusBackgroundColor);
        gradientDrawable.setCornerRadius(radius);
        setBackground(gradientDrawable);
    }

    public void setData(SimpleChartData<PercentChartEntry> data) {
        this.data = data;
        vLinePieView.setData(data.getData());
        inflateLegendView();
    }

    private void inflateLegendView() {
        vLegendRoot.removeAllViews();
        List<CharSequence> xValues;
        List<PercentChartEntry> entries;
        for (int i = 0; legendInflater != null && data != null &&
                (xValues = data.getxValues()) != null &&
                (entries = data.getData()) != null &&
                i < xValues.size(); i++) {
            CharSequence xValue = xValues.get(i);
            View legendView = legendInflater.inflate(getContext(), Pair.create(xValue, entries.get(i)), i, vLinePieView.isHideDetail());
            vLegendRoot.addView(legendView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
    }

    public void hideDetail(boolean hide) {
        vLinePieView.setHideDetail(hide);
        vLinePieView.invalidate();
        inflateLegendView();
        if (!hide) {
            vLinePieView.showAnimator();
        }
    }
}
