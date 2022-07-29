package com.tsh.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.AnimationSet;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LabelPieChart pieChart;
    private LabelCirclePercentChart percentChart;
    private LinePieChart linePieChart;
    private LinePercentChart<PercentData.Impl> linePercentChart;

    long animateDuration = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = findViewById(R.id.pie_chart);
        pieChart.setAnimateDuration(animateDuration);
        pieChart.setTextSize(12);
        pieChart.setExpansionFactor(2);
        pieChart.setEmptyColor(Color.parseColor("#111C24"));
        pieChart.setLabelFormatter((entry, position) -> String.format(Locale.US, "%.2f%%", entry.percent * 100));
        pieChart.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
        pieChart.addEntry(new PieChart.Entry(0.2801f, Color.parseColor("#9775E4"), true));
        pieChart.addEntry(new PieChart.Entry(0.3955f, Color.parseColor("#49A5FF"), false));
        pieChart.addEntry(new PieChart.Entry(0.2801f, Color.parseColor("#49D2C8"), false));
        pieChart.addEntry(new PieChart.Entry(0.3955f, Color.parseColor("#01B07D"), false));
//        pieChart.animateShow();

        percentChart = findViewById(R.id.percent_chart);
        percentChart.setPercent(0.6f);
        percentChart.setAnimateDuration(animateDuration);
        percentChart.setColor(Color.parseColor("#111C24"));
        percentChart.setActiveColor(Color.parseColor("#49A5FF"));
        percentChart.setLabel("数字货币", Color.WHITE, 12);
//        percentChart.animateShow();

        linePieChart = findViewById(R.id.line_pie_chart);
        linePieChart.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
        linePieChart.setEmptyColor(Color.parseColor("#111C24"));
        linePieChart.setAnimateDuration(animateDuration);
        linePieChart.addEntry(new LinePieChart.Entry(0.30f, Color.parseColor("#9253D6")));
        linePieChart.addEntry(new LinePieChart.Entry(0.02f, Color.parseColor("#49A5FF")));
        linePieChart.addEntry(new LinePieChart.Entry(0.68f, Color.parseColor("#00B07C")));
//        linePieChart.animateShow();

        linePercentChart = findViewById(R.id.line_percent_chart);
        linePercentChart.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
        linePercentChart.setData(new PercentData.Impl(0.6f));
        linePercentChart.setAnimateDuration(animateDuration);
        linePercentChart.setLabelPadding(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        linePercentChart.setLabelFormatter(data ->
                String.format(Locale.US, "%.2f%%", data.getPercent() * 100));
        linePercentChart.setColor(Color.parseColor("#9775E4"));
//        linePercentChart.animateShow();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            linePercentChart.post(() -> {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(percentChart,
                                percentChart.animateProperty(),
                                percentChart.animateStart(),
                                percentChart.animateEnd()),
                        ObjectAnimator.ofFloat(pieChart,
                                pieChart.animateProperty(),
                                pieChart.animateStart(),
                                pieChart.animateEnd()),
                        ObjectAnimator.ofFloat(linePieChart,
                                linePieChart.animateProperty(),
                                linePieChart.animateStart(),
                                linePieChart.animateEnd()),
                        ObjectAnimator.ofFloat(linePercentChart,
                                linePercentChart.animateProperty(),
                                linePercentChart.animateStart(),
                                linePercentChart.animateEnd())
                );
                animatorSet.setDuration(animateDuration);
                animatorSet.start();
            });
        }
        return super.dispatchTouchEvent(ev);
    }
}