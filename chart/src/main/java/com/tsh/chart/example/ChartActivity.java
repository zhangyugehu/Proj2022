package com.tsh.chart.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tsh.chart.R;
import com.tsh.chart.bar.LineBarChartBase;
import com.tsh.chart.data.ChartData;
import com.tsh.chart.data.SimpleChartData;
import com.tsh.chart.data.PercentChartEntry;
import com.tsh.chart.percent.CirclePercentChart;
import com.tsh.chart.percent.LinePercentGroupChart;
import com.tsh.chart.percent.LinePieChart;
import com.tsh.chart.pie.PieChart;
import com.tsh.chart.pie.PieView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChartActivity extends AppCompatActivity {
    PieChart pieChart, pieChart2;
    LinePercentGroupChart linePercentGroupChart;
    LinePieChart linePieChart;
    LineBarChartBase<XAxis> lineBarChart;
    ViewGroup circlePercentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        pieChart = findViewById(R.id.pie_chart);
        initPieChartData();
        circlePercentGroup = findViewById(R.id.circle_percent_chart_group);
        pieChart2 = findViewById(R.id.pie_chart_2);
        initPieChart2Data();
        linePercentGroupChart = findViewById(R.id.line_distribution_chart);
        initLineDistributionChartData();
        linePieChart = findViewById(R.id.line_pie_chart);
        initLinePieChartData();
        lineBarChart = findViewById(R.id.line_bar_chart);
        initLineBarChart();

    }

    public static class XAxis implements LineBarChartBase.XAxis {

        private final int month;

        public XAxis(int month) {
            this.month = month;
        }

        @Override
        public CharSequence toReadable() {
            return month + "月";
        }
    }

    Random random = new Random();

    float randomPercent() {
        return random.nextFloat() * (random.nextBoolean() ? 1 : -1);
    }

    private void initLineBarChart() {

        ChartData<XAxis, LineBarChartBase.EntrySet> data = new ChartData<>();
        List<XAxis> xVals = new ArrayList<>();
        List<LineBarChartBase.EntrySet> entrySets = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            xVals.add(new XAxis(i));
            entrySets.add(new LineBarChartBase.EntrySet(Arrays.asList(
                    new PercentChartEntry(Color.parseColor("#9775E4"), randomPercent()),
//                    new PercentChartEntry(Color.parseColor("#49D2C8"), randomPercent()),
                    new PercentChartEntry(Color.parseColor("#01B07D"), randomPercent())
//                    new PercentChartEntry(Color.parseColor("#01B07D"), randomPercent())
            )));
            entrySets.add(new LineBarChartBase.EntrySet(Arrays.asList(
//                    new PercentChartEntry(Color.parseColor("#9775E4"), 0.3f),
                    new PercentChartEntry(Color.parseColor("#49D2C8"), 0.7f),
//                    new PercentChartEntry(Color.parseColor("#01B07D"), -0.1f),
                    new PercentChartEntry(Color.parseColor("#01B07D"), 0.2f)
            )));
        }
        data.setxValues(xVals);
        data.setData(entrySets);
        lineBarChart.setData(data);
        lineBarChart.setOnSelectListener((index, subIndex) ->
                Toast.makeText(this, "Select: " + index + "-" + subIndex, Toast.LENGTH_LONG).show());
        lineBarChart.showAnimator();
    }

    private void initLinePieChartData() {
        SimpleChartData<PercentChartEntry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("盈利", "平衡", "亏损"));
        simpleChartData.setData(Arrays.asList(
                new PercentChartEntry(Color.parseColor("#9775E4"), 0.30f),
                new PercentChartEntry(Color.parseColor("#49D2C8"), 0.02f),
                new PercentChartEntry(Color.parseColor("#01B07D"), 0.68f)
        ));
        linePieChart.setData(simpleChartData);
    }

    private void initLineDistributionChartData() {
        SimpleChartData<PercentChartEntry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("制造业", "消费品", "汽车", "一句话说不清的行业"));
        simpleChartData.setData(Arrays.asList(
                new PercentChartEntry(Color.parseColor("#9775E4"), 0.70f),
                new PercentChartEntry(Color.parseColor("#49D2C8"), 0.0001f),
                new PercentChartEntry(Color.parseColor("#01B07D"), 0.9999f),
                new PercentChartEntry(Color.parseColor("#01B07D"), 0.04f)
        ));
        linePercentGroupChart.setData(simpleChartData);
    }

    private void initPieChart2Data() {
        SimpleChartData<PieView.Entry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("股票做多", "期权做多", "股票做空", "期权做空"));
        simpleChartData.setData(Arrays.asList(
                new PieView.Entry(0.2801f, Color.parseColor("#9775E4")),
                new PieView.Entry(0.2801f, Color.parseColor("#9775E4")),
                new PieView.Entry(0.1500f, Color.parseColor("#01B07D")),
                new PieView.Entry(0.01f, Color.parseColor("#01B07D"))
        ));
        pieChart2.setData(simpleChartData);
    }

    private void initPieChartData() {
        SimpleChartData<PieView.Entry> simpleChartData = new SimpleChartData<>();
        simpleChartData.setxValues(Arrays.asList("股票", "期权", "投顾", "数字货币"));
        simpleChartData.setData(Arrays.asList(
                new PieView.Entry(0.2801f, Color.parseColor("#9775E4"), true),
                new PieView.Entry(0.2801f, Color.parseColor("#49D2C8")),
                new PieView.Entry(0.3955f, Color.parseColor("#01B07D")),
                new PieView.Entry(0.3955f, Color.parseColor("#01B07D"))
        ));
        pieChart.setData(simpleChartData);
    }

    private boolean isHide = false;

    public void onHideClick() {
        isHide = !isHide;
        pieChart.hideDetail(isHide);
        pieChart2.hideDetail(isHide);

        for (int i = 0; i < circlePercentGroup.getChildCount(); i++) {
            View childAt = circlePercentGroup.getChildAt(i);
            if (childAt instanceof CirclePercentChart) {
                CirclePercentChart chart = (CirclePercentChart) childAt;
                chart.hideDetail(isHide);
            }
        }

        linePercentGroupChart.hideDetail(isHide);

        linePieChart.hideDetail(isHide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Hide");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            onHideClick();
        }
        return super.onOptionsItemSelected(item);
    }
}