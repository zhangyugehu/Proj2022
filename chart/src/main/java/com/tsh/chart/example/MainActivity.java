package com.tsh.chart.example;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.tsh.chart.percent.CirclePercentChart;
import com.tsh.chart.IAnimatorChart;
import com.tsh.chart.percent.LabelCirclePercentChart;
import com.tsh.chart.ratio.LabelPieChart;
import com.tsh.chart.percent.LinePercentChart;
import com.tsh.chart.ratio.LinePieChart;
import com.tsh.chart.ratio.PieChart;
import com.tsh.chart.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LabelPieChart pieChart;
    private LabelCirclePercentChart percentChart;
    private LinePieChart linePieChart;
    private LinePercentChart linePercentChart0, linePercentChart1, linePercentChart2, linePercentChart3;

    long animateDuration = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = findViewById(R.id.pie_chart);
//        pieChart.setAnimateDuration(animateDuration);
//        pieChart.setTextSize(12);
//        pieChart.setExpansionFactor(2);
//        pieChart.setEmptyColor(Color.parseColor("#111C24"));
//        pieChart.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
        pieChart.setLabelFormatter((entry, position) -> String.format(Locale.US, "%.2f%%", entry.percent * 100));
        pieChart.addEntry(new PieChart.Entry(0.2801f, Color.parseColor("#9775E4"), true));
        pieChart.addEntry(new PieChart.Entry(0.3955f, Color.parseColor("#49A5FF"), false));
        pieChart.addEntry(new PieChart.Entry(0.2801f, Color.parseColor("#49D2C8"), false));
        pieChart.addEntry(new PieChart.Entry(0.3955f, Color.parseColor("#01B07D"), false));
//        pieChart.animateShow();

        percentChart = findViewById(R.id.percent_chart);
//        percentChart.setPercent(0.6f);
//        percentChart.setAnimateDuration(animateDuration);
//        percentChart.setColor(Color.parseColor("#111C24"));
//        percentChart.setActiveColor(Color.parseColor("#49A5FF"));
//        percentChart.setLabel("数字货币", Color.WHITE, 12);
//        percentChart.animateShow();

        linePieChart = findViewById(R.id.line_pie_chart);
//        linePieChart.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
//        linePieChart.setEmptyColor(Color.parseColor("#111C24"));
//        linePieChart.setAnimateDuration(animateDuration);
        linePieChart.addEntry(new LinePieChart.Entry(0.30f, Color.parseColor("#9253D6")));
        linePieChart.addEntry(new LinePieChart.Entry(0.02f, Color.parseColor("#49A5FF")));
        linePieChart.addEntry(new LinePieChart.Entry(0.68f, Color.parseColor("#00B07C")));
//        linePieChart.animateShow();

        linePercentChart0 = findViewById(R.id.line_percent_chart_0);
        linePercentChart1 = findViewById(R.id.line_percent_chart_1);
        linePercentChart2 = findViewById(R.id.line_percent_chart_2);
        linePercentChart3 = findViewById(R.id.line_percent_chart_3);
//        linePercentChart.setColor(Color.parseColor("#9775E4"));
//        linePercentChart.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
//        linePercentChart.setAnimateDuration(animateDuration);
//        linePercentChart.setLabelPadding(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
//        linePercentChart.setData(new PercentData.Impl(0.6f));
//        linePercentChart.setLabelFormatter(percent ->
//                String.format(Locale.US, "%.2f%%", percent * 100));
//        linePercentChart.animateShow();

        LinearLayout circlePercentLayout = findViewById(R.id.layout_circle_percent);
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < circlePercentLayout.getChildCount(); i++) {
            View childAt = circlePercentLayout.getChildAt(i);
            if (childAt instanceof CirclePercentChart) {
                CirclePercentChart chart = (CirclePercentChart) childAt;
                animators.add(
                        createAnimator(chart).setDuration((long) (animateDuration * chart.getPercent()))
                );
            }
        }
        animatorSet.playTogether(animators);
        circlePercentLayout.setOnClickListener(v -> animatorSet.start());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            linePercentChart0.post(() -> {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        createAnimator(percentChart),
                        createAnimator(pieChart),
                        createAnimator(linePieChart),
                        createAnimator(linePercentChart0).setDuration((long) (animateDuration * linePercentChart0.getPercent())),
                        createAnimator(linePercentChart1).setDuration((long) (animateDuration * linePercentChart1.getPercent())),
                        createAnimator(linePercentChart2).setDuration((long) (animateDuration * linePercentChart2.getPercent())),
                        createAnimator(linePercentChart3).setDuration((long) (animateDuration * linePercentChart3.getPercent()))
                );
//                animatorSet.setDuration(animateDuration);
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.start();
            });
        }
        return super.dispatchTouchEvent(ev);
    }

    ObjectAnimator createAnimator(IAnimatorChart animatorChart) {
        return ObjectAnimator.ofFloat(animatorChart,
                animatorChart.animateProperty(),
                animatorChart.animateStart(),
                animatorChart.animateEnd());
    }
}