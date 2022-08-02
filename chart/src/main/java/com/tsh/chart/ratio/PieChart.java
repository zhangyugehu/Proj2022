package com.tsh.chart.ratio;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.tsh.chart.PointTextView;
import com.tsh.chart.data.ChartData;
import com.tsh.chart.legend.LegendInflater;

import java.util.List;
import java.util.Locale;

public class PieChart extends LinearLayout {

    private static final LegendInflater<PieView.Entry> DEFAULT_LEGEND_INFLATER = (context, data, index, hide) -> {
        LinearLayout linearLayout = new LinearLayout(context);
        PointTextView textView = new PointTextView(context);
        textView.setTextSize(13);
        textView.setTextColor(Color.parseColor("#A0A9BB"));
        CharSequence legends = data.first;
        textView.setText(legends);
        textView.setColor(data.second.getColor());
        linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textViewPercent = new TextView(context);
        textViewPercent.setTextSize(13);
        textViewPercent.setTextColor(Color.WHITE);
        textViewPercent.setText(String.format(Locale.US, " %.2f%%",  hide? 0 :data.second.getPercent() * 100));
        linearLayout.addView(textViewPercent, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return linearLayout;

    };

    LabelPieView pieView;
    FlexboxLayout flexbox;

    LegendInflater<PieView.Entry> legendInflater = DEFAULT_LEGEND_INFLATER;

    int radiusBackgroundColor = Color.parseColor("#18222B");
    float radius;
    ChartData<PieView.Entry> data;

    public PieChart(@NonNull Context context) {
        this(context, null);
    }

    public PieChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        int pieSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                55, getResources().getDisplayMetrics());

        addView(pieView = new LabelPieView(context, attrs, defStyleAttr),
                new LinearLayout.LayoutParams(pieSize, pieSize));
        flexbox = new FlexboxLayout(context, attrs, defStyleAttr);
        flexbox.setFlexWrap(FlexWrap.WRAP);
        flexbox.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        flexbox.setAlignContent(AlignContent.SPACE_AROUND);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        lp.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                19, getResources().getDisplayMetrics());
        addView(flexbox, lp);

        int paddingVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                14, getResources().getDisplayMetrics());
        int paddingHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                20, getResources().getDisplayMetrics());
        setPadding(
                Math.max(paddingHorizontal, getPaddingStart()),
                Math.max(paddingVertical, getPaddingTop()),
                Math.max(paddingHorizontal, getPaddingEnd()),
                Math.max(paddingVertical, getPaddingBottom())
        );

        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(radiusBackgroundColor);
        gradientDrawable.setCornerRadius(radius);
        setBackground(gradientDrawable);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        pieView.showAnimator();
    }

    public void setLegendInflater(LegendInflater<PieView.Entry>  legendInflater) {
        this.legendInflater = legendInflater;
    }

    public void hideDetail(boolean hide) {
        pieView.setHideDetail(hide);
        pieView.invalidate();
        setupLegendView();
        if (!hide) {
            pieView.showAnimator();
        }
    }

    public void setData(ChartData<PieView.Entry> data) {
        this.data = data;
        pieView.setData(data.getData());
        setupLegendView();
    }

    private void setupLegendView() {
        flexbox.removeAllViews();
        List<CharSequence> xValues;
        List<PieView.Entry> entries;
        for (int i = 0; legendInflater != null &&
                data != null &&
                (xValues = data.getxValues()) != null &&
                (entries = data.getData()) != null &&
                i < xValues.size(); i++) {
            PieView.Entry entry = null;
            if (i < entries.size()) {
                entry = entries.get(i);
            }
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setFlexBasisPercent(0.45f);
            float marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    5, getResources().getDisplayMetrics());
            lp.topMargin = (int) marginVertical;
            lp.bottomMargin = (int) marginVertical;
            flexbox.addView(legendInflater.inflate(getContext(),
                    Pair.create(xValues.get(i), entry), i, pieView.isHideDetail()), lp);
        }
    }

}
